package eg.edu.alexu.csd.filestructure.btree;
import eg.edu.alexu.csd.filestructure.btree.ISearchResult;
import org.w3c.dom.Element;


public class SearchResult implements ISearchResult {
    private int rank=0;
    private String id;

    public SearchResult(String id, int rank){
        this.id = id;
        this.rank=rank;
    }
    public SearchResult(Integer id, int rank){
        this.id = id.toString();
        this.rank=rank;
    }

    @Override
    public String getId() {
        return id;
    }
    @Override
    public void setId(String id) {
        this.id =id;
    }
    @Override
    public int getRank() {
        return rank;
    }
    @Override
    public void setRank(int rank) {
        this.rank=rank;
    }
    @Override
    public String toString(){
        return id+"  "+rank;
    }
}
