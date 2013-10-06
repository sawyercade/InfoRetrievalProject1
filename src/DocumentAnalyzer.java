import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;

public class DocumentAnalyzer {

    protected Tokenizer tokenizer;
    
    public DocumentAnalyzer(){
        tokenizer = new BasicTokenizer();
    }

    public Map<String, Integer> tokenize(String path) throws IOException{
        
        //Parse the document
        Document doc = Jsoup.parse(readFile(path));

        //Get stripped text
        String text = doc.text();

        //TESTING
        //System.out.println("DA:\n" + text+ "\n");

        //Get all links in the document
        Elements links = doc.select("a");

        //Count frequency of each token
        tokenizer.tokenize(text);
        tokenizer.tokenize(links);

        return tokenizer.getFrequencies();
    }

    public static String readFile(String path) throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
    }

    public String testJsoup(String path) throws IOException{
        return Jsoup.parse(readFile(path)).text();
    }
}
