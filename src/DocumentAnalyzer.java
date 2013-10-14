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
    protected Filter filter;

    /**
     * Creates a new DocumentAnalyzer. A new DocumentAnalyzer should be used for each document in the collection,
     * unless you want to treat two separate documents as actually being the same document.
     */
    public DocumentAnalyzer() throws IOException {
        tokenizer = new BasicTokenizer();
        filter = new Filter();
    }

    /**
     * Tokenizes and calculates localHashTable for the file indicated by path. Note that
     * @param path
     * @return
     * @throws IOException
     */
    public LocalHashTable tokenize(String path) throws IOException{
        //Parse the document
        Document doc = Jsoup.parse(readFile(path));

        //Get stripped text
        String text = doc.text();

        //Get all links in the document
        Elements links = doc.select("a");

        //Count frequency of each token
        tokenizer.tokenize(text);
        tokenizer.tokenize(links);

        //Filter tokens
        filter.filter(tokenizer.getLocalHashTable());

        return tokenizer.getLocalHashTable();
    }

    public static String readFile(String path) throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
    }

    public String testJsoup(String path) throws IOException{
        return Jsoup.parse(readFile(path)).text();
    }

    /**
     * Gets the number of unique tokens for this document post-filtering.
     * To get pre-filtering, use tokenizer.getNumUniqueTokens().
     * @return
     */
    public Integer getNumUniqueTokens(){
        return tokenizer.getLocalHashTable().getNumUniqueTokens();
    }

    /**
     * Gets the number of non-unique tokens for this document post-filtering.
     * To get pre-filtering, use tokenizer.getNumNonUniqueTokens().
     * @return
     */
    public Integer getNumNonUniqueTokens(){
        return tokenizer.getNumNonUniqueTokens();
    }
}
