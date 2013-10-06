import java.util.*;

/**
 * Collects and stores tokens so that they can easily be sorted by term or frequency.
 * Not very space efficient, requires 2n space, where n is the number of term-frequency pairs.
 */
public class TokenCollector {
    private Map<String, Integer> frequencies;
    private Integer uniqueTokens;
    private Integer nonuniqueTokens;

    public TokenCollector(){
        frequencies = new HashMap<String, Integer>();
        uniqueTokens = 0;
        nonuniqueTokens = 0;
    }

    public TokenCollector(Map<String, Integer> map){
        frequencies = map;
        uniqueTokens = 0;
        nonuniqueTokens = 0;
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

    public Integer getUniqueTokens() {
        return uniqueTokens;
    }

    public void setUniqueTokens(Integer uniqueTokens) {
        this.uniqueTokens = uniqueTokens;
    }

    public Integer getNonuniqueTokens() {
        return nonuniqueTokens;
    }

    public void setNonuniqueTokens(Integer nonuniqueTokens) {
        this.nonuniqueTokens = nonuniqueTokens;
    }
}
