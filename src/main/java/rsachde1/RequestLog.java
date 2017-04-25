package rsachde1;

/**
 *
 * @author Raj.Sachdev
 * 
 * This class models a log entry to be saved into MongoDB. Models all the important
 * information about a request to the Web Service.
 */
public class RequestLog {

    private String requestURL; //URL recieved from Android.
    private String queryText;  //search term queried
    private String APIRequestURL; //URL of the MFA API which is hit
    private int numSongs;         //number of songs returned from MFA API
    private String songList;      //comma separated list of songs returned from MFA
    private String timestampRecieved; //time stamp of the request
    private String date;              //date of request
    private String quarter;           //quarter of the day the request was recieved.
    
    public RequestLog() {
        //default contructor
    }
    
    public RequestLog(String requestURL, String queryText, String APIRequestURL, int numSongs, String songList, String timestampRecieved, String date, String quarter) {
        this.requestURL = requestURL;
        this.queryText = queryText;
        this.APIRequestURL = APIRequestURL;
        this.numSongs = numSongs;
        this.songList = songList;
        this.timestampRecieved = timestampRecieved;
        this.date = date;
        this.quarter = quarter;
    }
    
    
    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getTimestampRecieved() {
        return timestampRecieved;
    }

    public void setTimestampRecieved(String timestampRecieved) {
        this.timestampRecieved = timestampRecieved;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getAPIRequestURL() {
        return APIRequestURL;
    }

    public void setAPIRequestURL(String APIRequestURL) {
        this.APIRequestURL = APIRequestURL;
    }

    public int getNumSongs() {
        return numSongs;
    }

    public void setNumSongs(int numSongs) {
        this.numSongs = numSongs;
    }

    public String getSongList() {
        return songList;
    }

    public void setSongList(String songList) {
        this.songList = songList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }
    

}
