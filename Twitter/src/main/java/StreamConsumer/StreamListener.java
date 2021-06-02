package StreamConsumer;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import Tokenizer.Logger;
import Tokenizer.Tweet;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class StreamListener implements StatusListener {
    
    StreamHandler ref;
    String[] apiKeys = new String[4];
    
    Logger log = new Logger();
    
    
    TwitterFactory factory;
    public static Twitter twitter;
    DirectMessage directMessage = null;
    static final String surveyUrl = "https://forms.gle/gh88532ySBK79WUJA";
    public static final String message = "Hi!\n You have been randomly chosen to participate in a survey about Covid-19 vaccines." +
            "We'd greatly appreciate if you take 2 minutes and complete it. The data is completely anonymous" +
            " and you will be able to look at the results after you complete it. Link to survey: " +
            surveyUrl + "\n Thank you for your time.";
    
    public static AtomicInteger sentDMs = new AtomicInteger(0);
    public static AtomicInteger errorDMs = new AtomicInteger(0);
    public static AtomicReference<String> lastErrorMsg = new AtomicReference<>("EMPTY");
    public static AtomicReference<Exception> lastErrorExc = new AtomicReference<>(new Exception("empty"));
    
    
    public StreamListener(StreamHandler ref) {
        this.ref = ref;
        
        readKeysFromFile();
        
//        factory = new TwitterFactory();
//        twitter = factory.getInstance();
//        twitter.setOAuthConsumer(apiKeys[0], apiKeys[1]);
//        AccessToken accessToken = new AccessToken(apiKeys[2], apiKeys[3]);
//        twitter.setOAuthAccessToken(accessToken);
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder
                .setOAuthConsumerKey(apiKeys[0])
                .setOAuthConsumerSecret(apiKeys[1])
                .setOAuthAccessToken(apiKeys[2])
                .setOAuthAccessTokenSecret(apiKeys[3]);
        Configuration configuration = configurationBuilder.build();
        TwitterFactory factory = new TwitterFactory(configuration);
        twitter = factory.getInstance();
        try {
            System.out.println("NAME=" + twitter.getScreenName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    HashSet<byte[]> seen = new HashSet<>(10000);
    MessageDigest digest; {
        try { digest = MessageDigest.getInstance("SHA-256"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
    }
    
    static final SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
//    static final DateTimeFormatter form2 = new SimpleDateFormat("dd-MM-yyyy");
    
    public static final Set<String> seenUsers = new HashSet<>(5000);
    
    
    private static int x = 0;
    @Override
    public void onStatus(Status status) {
        // throw away non-english tweets
        if (!status.getLang().equals("en")) return;
        
        String tweet = "";
        if (status.isRetweet()) {
            tweet = status.getRetweetedStatus().getText();
        } else {
            tweet = status.getText();
        }
        
        if (seen.contains(digest.digest(tweet.getBytes(StandardCharsets.UTF_8)))) return;
        // For some reason, some tweets don't even contain
        // any of the query words. This doesn't happen often.
        // Check manually.
        if (ref.queries != null) {
            boolean contains = false;
            for (String s : ref.queries) {
                if (tweet.contains(s)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) return;
        }
        
        String userName = status.getUser().getScreenName();
        
        if (!seenUsers.contains(userName)) {
            try {
                twitter.sendDirectMessage(userName, message);
                sentDMs.incrementAndGet();
                log.saveSeenUsers(userName);
//                System.out.println("Sent to : " + userName);
            } catch (Exception e) {
                errorDMs.incrementAndGet();
//                if (x<1) {
//                    x++;
//                    e.printStackTrace();
//                }
                lastErrorMsg.set(e.getLocalizedMessage());
                lastErrorExc.set(e);
//                System.out.println("Error to: " + userName);
            }
        }
        seenUsers.add(userName);
        
        
//		System.out.println(form.format(status.getCreatedAt()));
        // getting RAW json string, still, emoticons are questionmarks
        //String raw = DataObjectFactory.getRawJSON(status);
        //System.out.println(raw);
        // add tweet to list of tweets
//        LocalDate d = LocalDate.of(2021, 5, 11);
        ref.tweets.add(new Tweet(tweet, status.getUser().getScreenName(), form.format(status.getCreatedAt())));
        seen.add(digest.digest(tweet.getBytes(StandardCharsets.UTF_8)));
        
        
        // take the tweet that has been in the list the longest and process it
        // there are two queues, one of tweets and one of executors, make sure
        // the executors one is as close to empty as possible, so that there
        // is only one queue (stupid but whatever)
        // calling .pollFirst() method means the tweet gets removed from the 
        // list as well
        //if (!ref.tweets.isEmpty() && ref.executor.getQueue().size() == 0)
            ref.executor.submit(new Consumer(ref.tweets.pollFirst(), ref));
        
        //ref.queue_size.incrementAndGet();
    }
    
    boolean containsEmoji(String s) {
        return s.codePoints().anyMatch(cp -> UnicodeBlock.of(cp).equals(UnicodeBlock.EMOTICONS));
    }
    
    String relativeFilePath_apiKeys = "apikeys/keys.txt";
    private void readKeysFromFile() {
        final int[] index = {0};
        try (Stream<String> lines = Files.lines(Paths.get(relativeFilePath_apiKeys), Charset.defaultCharset())) {
            //System.out.println(lines.count());
            lines.forEachOrdered(line -> {
                System.out.println(line);
                apiKeys[index[0]] = line;
                index[0]++;
            });
        } catch (IOException e) {
            System.out.println("Error reading api keys!");
            e.printStackTrace();
        }
        index[0] = 0;
    }
    
    @Override
    public void onTrackLimitationNotice(int arg0) {}
    @Override
    public void onDeletionNotice(StatusDeletionNotice arg0) {}
    @Override
    public void onScrubGeo(long arg0, long arg1) {}
    @Override
    public void onStallWarning(StallWarning arg0) {}
    @Override
    public void onException(Exception arg0) { System.out.println("Exception!" + Arrays.toString(arg0.getStackTrace())); }
}
