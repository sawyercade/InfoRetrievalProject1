import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.StringTokenizer;

public class BasicTokenizer implements Tokenizer{

    final String wordDelimiters = " \t\n\r\f\"\1\u000B,*#$^&+=!?-_()[]{}|\\:<>;./%'";

    protected LocalHashTable localHashTable;
    private Integer numUniqueTokens;
    private Integer numNonUniqueTokens;

    public BasicTokenizer(){
        this.numUniqueTokens = 0;
        this.numNonUniqueTokens = 0;
        this.localHashTable = new LocalHashTable();
    }

    @Override
    //tokenizes text
    public void tokenize(String text) {
        String preprocessedText = preprocess(text);
        StringTokenizer tokenizer = new StringTokenizer(preprocessedText, wordDelimiters);
        while (tokenizer.hasMoreTokens()){
            localHashTable.incrementFrequency(tokenizer.nextToken().toLowerCase());
        }
    }

    @Override
    //tokenizes links differently than other words
    public void tokenize(Elements elements) {
        for (Element element : elements){
            String url = element.attr("href");
            //If the url is relative, throw it away. We have no way of resolving the baseUri of test documents.
            if(url.startsWith("www.") || url.startsWith("http://") || url.startsWith("https://")){
                //Store the url as a token if it's absolute
                localHashTable.incrementFrequency(url);
            }
        }
    }

    @Override
    public LocalHashTable getLocalHashTable() {
        return localHashTable;
    }


    private String preprocess(String text){
        return text.replace("\\", "\\\\");
    }

    public static String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }
        //System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }

    //GETTERS AND SETTERS

    public Integer getNumUniqueTokens() {
        return numUniqueTokens;
    }

    public void setNumUniqueTokens(Integer numUniqueTokens) {
        this.numUniqueTokens = numUniqueTokens;
    }

    public Integer getNumNonUniqueTokens() {
        return numNonUniqueTokens;
    }

    public void setNumNonUniqueTokens(Integer numNonUniqueTokens) {
        this.numNonUniqueTokens = numNonUniqueTokens;
    }
}
