import java.util.Iterator;
import java.util.Map;

public class Pruner {
    private TokenCollector tokenCollector;
    private InvertedFileBuilder invertedFileBuilder;

    public Pruner(TokenCollector tokenCollector, InvertedFileBuilder invertedFileBuilder){
        this.tokenCollector = tokenCollector;
        this.invertedFileBuilder = invertedFileBuilder;
    }

    /**
     * Prunes terms with a frequency less than minFreq or length less than minLength.
     * @param minFreq
     * @param minLength
     */
    public void prune(int minFreq, int minLength) throws IRException{
        //loop over all terms in tokenCollector (is there a better way to do this?)
        Iterator<Map.Entry<String, Integer>> iterator = tokenCollector.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            if(entry.getValue() < minFreq || entry.getKey().length() < minLength){ //if frequency or length is too low
                //remove from tokenCollector and invertedFileBuilder
                iterator.remove(); //prevents ConcurrentModificationException
                invertedFileBuilder.removeEntry(entry.getKey());
            }
        }
    }
}
