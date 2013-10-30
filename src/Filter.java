import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Filter {
    public static final int MINIMUM_TOKEN_LENGTH = 2;
    public static final String STOPWORDS_FILE_PATH = "C:\\Users\\Sawyer\\Projects\\InfoRetrieval\\stoplist.txt";

    private Map<String, Integer> StopWords;

    public Filter() throws IOException {
        this.StopWords = new HashMap<String, Integer>();
        buildStopWords(new File(STOPWORDS_FILE_PATH));
    }

    /**
     * Removes stopwords and other non-useful tokens from a map of token localHashTable
     * @param localHashTable
     * @return
     */
    public void filter(LocalHashTable localHashTable){
        removeStopwords(localHashTable);
        removeMinLengthTokens(localHashTable);
    }

    /**
     * Remove tokens of length < MINIMUM_TOKEN_LENGTH
     * @param tokens
     */
    private void removeMinLengthTokens(LocalHashTable tokens) {
        for(Map.Entry<String, Integer> token : tokens){
            if (token.getKey().length()<MINIMUM_TOKEN_LENGTH){
                tokens.remove(token); //make sure to remove using the iterator, not the actual key string value
            }
        }
    }

    /**
     * Remove all stopwords from the set of tokens
     * @param tokens
     */
    private void removeStopwords(LocalHashTable tokens) {
        for(Map.Entry<String, Integer> stopword : StopWords.entrySet()){
            tokens.remove(stopword.getKey());
        }
    }

    /**
     * Read in and parse stopwords from a file (one stopword per line)
     * @param stopwordsFile
     * @throws IOException
     */
    private void buildStopWords(File stopwordsFile) throws IOException {
        //read in the the stopwords file
        FileReader fileReader = new FileReader(stopwordsFile);
        BufferedReader br = new BufferedReader(fileReader);
        String s;
        while ((s = br.readLine())!=null){
            this.StopWords.put(s, null);
        }
    }

    public Map<String, Integer> getStopWords() {
        return StopWords;
    }

    public void setStopWords(Map<String, Integer> stopWords) {
        StopWords = stopWords;
    }
}
