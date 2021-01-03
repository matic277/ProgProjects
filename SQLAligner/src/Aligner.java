import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Aligner {
    
    static Pattern p = Pattern.compile("((?<=(INSERT\\sINTO\\s))[\\w\\d_]+(?=\\s+))|((?<=\\()(\\s*[\\w\\d_,]+\\s*)+)", Pattern.CASE_INSENSITIVE);
    
    public static void main(String[] args) throws IOException {
        Path file = Paths.get("sqls.txt");
        List<String> sqls = new ArrayList<>(10);

        try (Stream<String> lines = Files.lines(file, Charset.defaultCharset())) {
            lines.forEachOrdered(sqls::add);
        }

        System.out.println(Arrays.toString(sqls.toArray()));

        
        Matcher tmpMatcher = p.matcher(sqls.get(0));
    
//        while (tmpMatcher.find()) {
//            System.out.println(tmpMatcher.group(0));
//        }
        
        // get all col names
        tmpMatcher.find();
        String[] cols = getColumnNames(tmpMatcher.group(0));
        System.out.println("Cols: " + Arrays.toString(cols));
        
        sqls.forEach(Aligner::processSql);
    }
    
    public static void processSql(String sql) {
        Matcher m = p.matcher(sql);
        m.find(); m.find(); // call find() to get second group (values)
        
        String vals = m.group(0);
        System.out.println(vals);
    }
    
    public static String[] getColumnNames(String input) {
        return input
                .replaceAll(" ", "")
                .split(",");
    }
}
