import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Wraps a Map<String, Integer> of frequencies, keeping track of the number of tokens as it goes.
 */
public class LocalHashTable implements Iterable<Map.Entry<String, Integer>>{
    private Map<String, Integer> frequencies;
    private long numNonUniqueTokens;

    public LocalHashTable() {
        frequencies = new HashMap<String, Integer>();
        numNonUniqueTokens = 0;
    }

    /**
     * Increments the value for key by 1 if it exists, puts a new key with a value of 1 if not.
     * @param key
     */
    public void incrementFrequency(String key){
        Integer value = 1;
        if(frequencies.containsKey(key)){
            value = frequencies.get(key) + 1;
        }
        frequencies.put(key, value);
        numNonUniqueTokens+=value;
    }

    /**
     * Remove an entry from the hashtable. Updates counters.
     */
    public void remove(String key) {
        if (frequencies.get(key)!= null){
            numNonUniqueTokens -= frequencies.remove(key); //remove key, update numNonUniqueTokens
        }
    }

    /**
     * Remove an entry from the hashtable. Updates counters.
     */
    public void remove(Map.Entry<String, Integer> entry) {
        if (frequencies.get(entry)!=null){
            numNonUniqueTokens -= frequencies.remove(entry); //remove entry, update numNonUniqueTokens
        }
    }

    /**
     * Calculates the rtf for the entry indicated by key.
     * @param key
     * @return
     */
    public float getRtf(String key) throws IRException{
        if(frequencies.get(key)==null){
            throw new IRException("Attempting to get rtf of a key that does not exist in the hashtable");
        }
        return ((float)frequencies.get(key))/numNonUniqueTokens; //get the frequency of key, divide by total number of tokens
    }

    public Integer getNumUniqueTokens(){
        return frequencies.size();
    }

    public Long getNumNonUniqueTokens(){
        return numNonUniqueTokens;
    }

    public void setNumNonUniqueTokens(long numNonUniqueTokens) {
        this.numNonUniqueTokens = numNonUniqueTokens;
    }

    public Map<String, Integer> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(Map<String, Integer> frequencies) {
        this.frequencies = frequencies;
    }

    @Override
    public Iterator<Map.Entry<String, Integer>> iterator() {
        return frequencies.entrySet().iterator();
    }
}
