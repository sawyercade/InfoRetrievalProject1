import java.io.IOException;
import java.util.*;

/**
 * Global HashTable:
 * Collects and stores tokens and localHashTable.
 */
public class TokenCollector implements Iterable<Map.Entry<String, Integer>>{
    private Map<String, Integer> frequencies;
    private Long uniqueTokens;
    private Long nonuniqueTokens;

    public TokenCollector() throws IOException {
        frequencies = new HashMap<String, Integer>();
        uniqueTokens = (long)0;
        nonuniqueTokens = (long)0;
    }

    public TokenCollector(Map<String, Integer> map){
        frequencies = map;
        uniqueTokens = (long)0;
        nonuniqueTokens = (long)0;
    }

    public void addTokens(Map<String, Integer> newTokens){
        for (Map.Entry<String, Integer> entry : newTokens.entrySet()){
            Integer value = entry.getValue();
            String key = entry.getKey();
            if (frequencies.containsKey(key)){
                value = frequencies.get(key) + entry.getValue();
            }
            else{
                uniqueTokens++;
            }
            frequencies.put(key, value);
            nonuniqueTokens += value;
        }
    }

    public void removeToken(String token) throws IRException{
        if(!frequencies.containsKey(token)){
            throw new IRException("Attempting to remove a token that doesn't exist in frequencies");
        }
        nonuniqueTokens -= frequencies.remove(token);
        uniqueTokens--;
    }

    public List<TermFreq> sortFrequenciesByTerm(){
        List<TermFreq> termFreqs = new ArrayList<TermFreq>();
        for(Map.Entry<String, Integer> entry : frequencies.entrySet()){
            termFreqs.add(new TermFreq(entry.getKey(), entry.getValue()));
        }
        Collections.sort(termFreqs, new Comparator<TermFreq>() {
            public int compare(TermFreq t1, TermFreq t2){
                return t1.getTerm().compareTo(t2.getTerm());
            }
        });
        return termFreqs;
    }

    public List<TermFreq> sortFrequenciesByFreq(){
        List<TermFreq> termFreqs = new ArrayList<TermFreq>();
        for(Map.Entry<String, Integer> entry : frequencies.entrySet()){
            termFreqs.add(new TermFreq(entry.getKey(), entry.getValue()));
        }
        Collections.sort(termFreqs, new Comparator<TermFreq>() {
            public int compare(TermFreq t1, TermFreq t2){
                return t1.getFreq().compareTo(t2.getFreq());
            }
        });
        return termFreqs;
    }

    //GETTERS AND SETTERS
    public Map<String, Integer> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(Map<String, Integer> frequencies) {
        this.frequencies = frequencies;
    }

    public Long getUniqueTokens() {
        return uniqueTokens;
    }

    public void setUniqueTokens(Long uniqueTokens) {
        this.uniqueTokens = uniqueTokens;
    }

    public Long getNonuniqueTokens() {
        return nonuniqueTokens;
    }

    public void setNonuniqueTokens(Long nonuniqueTokens) {
        this.nonuniqueTokens = nonuniqueTokens;
    }

    @Override
    public Iterator<Map.Entry<String, Integer>> iterator() {
        Iterator<Map.Entry<String, Integer>> iterator = new Iterator<Map.Entry<String, Integer>>(){
            Iterator<Map.Entry<String, Integer>> mapIterator = frequencies.entrySet().iterator();
            Map.Entry<String, Integer> currentEntry;

            @Override
            public boolean hasNext() {
                return mapIterator.hasNext();
            }

            @Override
            public Map.Entry<String, Integer> next() {
                this.currentEntry = mapIterator.next();
                return currentEntry;
            }

            @Override
            public void remove(){
                nonuniqueTokens -= frequencies.get(currentEntry.getKey());
                uniqueTokens--;
                mapIterator.remove();
            }
        };

        return iterator;
    }
}
