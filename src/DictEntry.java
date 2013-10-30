import java.util.List;

public class DictEntry implements Comparable<DictEntry>{
    private String term;
    private Integer numDocs;
    private Float idf;
    private List<Posting> postingList;
    private Long postingLocation;

    public DictEntry(String term, Integer numDocs, List<Posting> postingList){
        this.term = term;
        this.numDocs = numDocs;
        this.postingList = postingList;
    }

    //GETTERS AND SETTERS
    public List<Posting> getPostingList() {
        return postingList;
    }

    public void setPostingList(List<Posting> postingList) {
        this.postingList = postingList;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getNumDocs() {
        return numDocs;
    }

    public void setNumDocs(Integer numDocs) {
        this.numDocs = numDocs;
    }

    public Long getPostingLocation() {
        return postingLocation;
    }

    public void setPostingLocation(Long postingLocation) {
        this.postingLocation = postingLocation;
    }

    public Float getIdf() {
        return idf;
    }

    public void setIdf(Float idf) {
        this.idf = idf;
    }

    @Override
    public int compareTo(DictEntry o) {
        return this.term.compareTo(o.getTerm());
    }
}
