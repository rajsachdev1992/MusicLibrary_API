package rsachde1;

/**
 *
 * @author Raj.Sachdev
 * 
 * This class stores the searched terms and the number of times the term has
 * been queried.
 */
public class QueryCountPair {
    
    private String query;
    private int count;

    public QueryCountPair(String query, int count) {
        this.query = query;
        this.count = count;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
