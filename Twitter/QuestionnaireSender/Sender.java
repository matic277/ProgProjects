import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Sender {
    
    static String relativeFilePath_apiKeys = "apikeys/keys.txt";
    static String[] apiKeys = new String[4];
    
    public static void main(String[] args) {
        readKeysFromFile();
        // ID   = 1399416626773204995
        // name = @hellotester8
        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer(apiKeys[0], apiKeys[1]);
        AccessToken accessToken = new AccessToken(apiKeys[2], apiKeys[3]);
        twitter.setOAuthAccessToken(accessToken);
        DirectMessage directMessage = null;
        String surveyUrl = "https://forms.gle/gh88532ySBK79WUJA";
        String message = "Hello, you have been randomly chosen to participate in a survey about Covid-19 vaccines." +
                "We'd greatly appreciate if you take 2 minutes and complete it. The data is completely anonymous" +
                " and you will be able to look at the results after you complete it. Link to survey: " +
                surveyUrl + "\n Thank you for your time.";
        
        try {
            System.out.println(twitter.getScreenName());
            directMessage = twitter.sendDirectMessage("@hellotester8", message);
        }
        catch (Exception e) {
            System.out.println("Error sending dm.");
            e.printStackTrace();
        }
    }
    
    private static void readKeysFromFile() {
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
}
