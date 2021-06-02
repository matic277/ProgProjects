import Tokenizer.Tweet;
import opennlp.tools.doccat.*;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class Analizer {
    
    enum Vaccine { PFIZER, ASTRAZENECA, MODERNA, MIXED, NONE }
    enum Model { MINE, OPEN_NLP }
    
    static Model model = Model.OPEN_NLP;
    static DoccatModel openNlpModel;
    
    static List<Tweet> tweets = new ArrayList<>(250_000);
    static Map<LocalDate, List<Tweet>> dateMap = new HashMap<>();
    static Map<LocalDate, Map<Vaccine, List<Tweet>>> vaccineMap = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            while (true) {
                if (donePercent > 98d) break;
                System.out.println("Progress on classifying: " + donePercent);
                try {Thread.sleep(3000); }
                catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
        
        // read file
        Files.lines(Paths.get("./ouputs/COMBINED_3way.txt"), Charset.defaultCharset()).forEach(Analizer::processLine);
        
        buildMaps();
        
        if (model == Model.MINE) {
            System.out.println("My model.");
            
            // print map and sentiments ordered by date
            dateMap.keySet().stream().sorted((d1, d2) -> d1.isBefore(d2) ? 1 : -1).forEachOrdered(date -> {
                System.out.println(date + " -> " + dateMap.get(date).size() + Arrays.deepToString(getSentimentsOnDate(date)));
            });
            
            // print by date and vaccine sentiment
            vaccineMap.keySet().stream().sorted((d1, d2) -> d1.isBefore(d2) ? 1 : -1).forEachOrdered(date -> {
                System.out.println();
                System.out.println(date + " -> " + dateMap.get(date).size());
                vaccineMap.get(date).forEach((vaccine, tweets) -> {
                    System.out.println("  " + vaccine + " (" + tweets.size() + ") -> " + Arrays.deepToString(getSentimentsOfTweets(tweets)));
                });
            });
        }
        else if (model == Model.OPEN_NLP) {
            System.out.println("OpenNLP model.");
            train();
            classify();
            
            // print by date and vaccine sentiment
            vaccineMap.keySet().stream().sorted((d1, d2) -> d1.isBefore(d2) ? 1 : -1).forEachOrdered(date -> {
                System.out.println();
                System.out.println(date + " -> " + dateMap.get(date).size());
                vaccineMap.get(date).forEach((vaccine, tweets) -> {
                    System.out.println("  " + vaccine + " (" + tweets.size() + ") -> " + Arrays.deepToString(getSentimentsOfTweets(tweets)));
                });
            });
        }
    }
    
    public static void buildMaps() {
        // create map based on date
        tweets.forEach(tweet -> {
            dateMap.computeIfAbsent(tweet.dateCollected, dateKey -> new ArrayList<>(4_000));
            dateMap.get(tweet.dateCollected).add(tweet);
        });
        System.out.println("datemap size=" + dateMap.size());
    
        // create a new dateMap based on vaccine
        // date -> map{Vaccines} -> [tweets]
        dateMap.forEach((date, tweets) -> {
            // generate map on a given date if it doesn't exist yet
            vaccineMap.computeIfAbsent(date, dateKey -> {
                Map<Vaccine, List<Tweet>> map = new HashMap<>();
                map.put(Vaccine.PFIZER, new ArrayList<>(2_000));
                map.put(Vaccine.ASTRAZENECA, new ArrayList<>(2_000));
                map.put(Vaccine.MODERNA, new ArrayList<>(2_000));
                map.put(Vaccine.MIXED, new ArrayList<>(2_000));
                map.put(Vaccine.NONE, new ArrayList<>(2_000));
                return map;
            });
            // populate vaccineMap
            tweets.forEach(tweet -> {
                if (tweet.isMixed()) vaccineMap.get(date).get(Vaccine.MIXED).add(tweet);
                else if (tweet.isPfizer()) vaccineMap.get(date).get(Vaccine.PFIZER).add(tweet);
                else if (tweet.isModerna()) vaccineMap.get(date).get(Vaccine.MODERNA).add(tweet);
                else if (tweet.isAstrazeneca()) vaccineMap.get(date).get(Vaccine.ASTRAZENECA).add(tweet);
                else vaccineMap.get(date).get(Vaccine.NONE).add(tweet);
            });
        });
    }
    
    static double donePercent = 0;
    static double classifiedTweets = 0;
    public static void classify() throws IOException {
        System.out.println("\n-> Model classifying...");
        
        final double tweetsToClassify = tweets.size();
        
        tweets.forEach(tweet -> {
            tweet.processTweet();
            DocumentCategorizerME myCategorizer = new DocumentCategorizerME(openNlpModel);
            double[] outcomes = myCategorizer.categorize(tweet.getCleanSource().split(" "));
            String category = myCategorizer.getBestCategory(outcomes);
            
            if (category.equalsIgnoreCase("1")) {
                tweet.sentimentClass = 1;
            } else if (category.equalsIgnoreCase("0")) {
                tweet.sentimentClass = 0;
            } else  {
                tweet.sentimentClass = -1;
            }
            
            donePercent = classifiedTweets++ / tweetsToClassify * 100;
        });
        System.out.print(" -> Model done classifying.");
    }
    
    public static void train() {
        System.out.print(" -> Model training...");
        final InputStream dataIn;
        try {
            dataIn = new FileInputStream("./datasets/trainingSet_GO_et_al.txt");
            
            ObjectStream<String> lineStream = new PlainTextByLineStream(() -> dataIn, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            
            TrainingParameters trainParams = new TrainingParameters();
            trainParams.put(TrainingParameters.CUTOFF_PARAM, 2);
            trainParams.put(TrainingParameters.ITERATIONS_PARAM, 30);
            trainParams.put(TrainingParameters.THREADS_PARAM, 2);
    
            openNlpModel = DocumentCategorizerME.train(
                    "en",
                    sampleStream,
                    trainParams,
                    new DoccatFactory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" done.");
    }
    
    // [negative, neutral, positive, negative%, neutral%, positive%]
    public static Double[] getSentimentsOfTweets(List<Tweet> tweets) {
        double positive = 0, neutral = 0, negative = 0;
        for (Tweet tweet : tweets) {
            if (tweet.sentimentClass == 1) positive++;
            else if (tweet.sentimentClass == 0) neutral++;
            else negative++;
        }
        double total = positive + neutral + negative;
        double posPerc = (double)positive / total;
        double neuPerc = (double)neutral / total;
        double negPerc = (double)negative / total;
        return new Double[] {negative, neutral, positive, negPerc, neuPerc, posPerc};
    }
    
    // [negative, neutral, positive, negative%, neutral%, positive%]
    public static Double[] getSentimentsOnDate(LocalDate date) {
        double positive = 0, neutral = 0, negative = 0;
        for (Tweet tweet : dateMap.get(date)) {
            if (tweet.sentimentClass == 1) positive++;
            else if (tweet.sentimentClass == 0) neutral++;
            else negative++;
        }
        double total = positive + neutral + negative;
        double posPerc = (double)positive / total;
        double neuPerc = (double)neutral / total;
        double negPerc = (double)negative / total;
        return new Double[] {negative, neutral, positive, negPerc, neuPerc, posPerc};
    }
    
    private static void processLine(String line) {
        line = line.trim();
        if (line.isEmpty() || line.isBlank()) return;
        
        String[] tokens = line.split(",");
        String classStr = tokens[0];
        String dateStr = tokens[1];
        String text = tokens[2];
        
        for (int i=3; i<tokens.length; i++) {
            text += "," + tokens[i];
        }
        
        String[] date = dateStr.split("-");
        Tweet t = new Tweet(text, null, LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])));
        t.sentimentClass = Integer.parseInt(classStr);
        tweets.add(t);
    }
}
