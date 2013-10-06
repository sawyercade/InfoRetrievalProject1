public class TermFreq {
    public String term;
    public Integer freq;

    public TermFreq(String term, Integer freq){
        this.term = term;
        this.freq = freq;
    }

    public String getTerm(){
        return term;
    }

    public Integer getFreq(){
        return freq;
    }
}
