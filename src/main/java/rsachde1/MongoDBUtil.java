package rsachde1;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.Document;

/**
 *
 * @author Raj.Sachdev
 * Provides methods to connect to the Mongo Database, insert records, retrieve
 * records, and perform analysis on data retrieved.
 * 
 * Singleton Design.
 * 
 * MongoDB Driver code referred from:
 * http://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/
 */
public class MongoDBUtil {

    private static MongoDBUtil mongoObject;
    private MongoClient mongo;
    private MongoDatabase db;

    private MongoDBUtil() {
        this.mongo = null;
        this.db = null;
        this.createConnection();
    }
    
    /**
     * Returns the object of the class.
     * @return MongoDBUtil
     */
    public static MongoDBUtil getInstance() {
        if (mongoObject == null) {
            return new MongoDBUtil();
        } else {
            return mongoObject;
        }
    }
    
    /**
     * inserts a logEntry record in the Database.
     * @param logEntry 
     */
    public void insertRecord(RequestLog logEntry) {
        Document doc = new Document();
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(date);
        String dateString = new SimpleDateFormat("yyyy.MM.dd").format(date);
        String hour = new SimpleDateFormat("HH").format(date);

        //crate DB entry
        doc.put("requestURL", logEntry.getRequestURL());
        doc.put("queryText", logEntry.getQueryText());
        doc.put("APIRequestURL", logEntry.getAPIRequestURL());
        doc.put("numSongs", logEntry.getNumSongs());
        doc.put("songList", logEntry.getSongList());
        doc.put("timeStamp", timeStamp);
        doc.put("date", dateString);
        doc.put("quarter", getQuarterFromHour(hour));

        //insert into DB`
        MongoCollection<Document> collection = db.getCollection("log");
        collection.insertOne(doc);
    }
    
    /**
     * Extracts the top 10 searched terms from the log entries in the
     * database.
     * @return List<>
     */
    public List<QueryCountPair> getTop10SearchedTerms() {
        MongoCollection<Document> collection = db.getCollection("log");
        MongoCursor<Document> cursor = collection.find().iterator();
        Document doc;
        HashMap<String, Integer> songCountMap = new HashMap<>();
        String queryText;
        Integer queryCount;
        while (cursor.hasNext()) {
            doc = cursor.next();
            queryText = doc.getString("queryText");
            queryCount = songCountMap.get(queryText);
            if (queryCount == null) {
                songCountMap.put(queryText, 1);
            } else {
                songCountMap.put(queryText, queryCount + 1);
            }
        }
        return getTop10List(songCountMap);
    }
    
    /**
     * Computes the average number of daily requests by analyzing
     * log entries in the database.
     * @return double
     */
    public double getAverageDailyRequests() {
        MongoCollection<Document> collection = db.getCollection("log");
        MongoCursor<Document> cursor = collection.find().iterator();
        HashMap<String, Integer> DateCountMap = new HashMap<>();

        Integer numDays;
        Document doc;
        String date;
        while (cursor.hasNext()) {
            doc = cursor.next();
            date = doc.getString("date");
            numDays = DateCountMap.get(date);
            if (numDays == null) {
                DateCountMap.put(date, 1);
            } else {
                DateCountMap.put(date, numDays + 1);
            }
        }
        List<Integer> dateCountList = new LinkedList<>(DateCountMap.values());
        int listSize = dateCountList.size();
        int totalCount = 0;
        Iterator<Integer> iter = dateCountList.iterator();
        while (iter.hasNext()) {
            totalCount += iter.next();
        }
        double average = (double) totalCount / (double) listSize;
        return average;
    }
    
    /**
     * Computes the percentage of requests received during 4 different segments
     * of the day.
     * @return QuarterInfo
     */
    public QuarterInfo getQuarterPercentages() {
        MongoCollection<Document> collection = db.getCollection("log");
        MongoCursor<Document> cursor = collection.find().iterator();
        HashMap<String, Integer> QuarterCountMap = new HashMap<>();
        Integer numRequests;
        Document doc;
        String quarter;
        while (cursor.hasNext()) {
            doc = cursor.next();
            quarter = doc.getString("quarter");
            numRequests = QuarterCountMap.get(quarter);
            if (numRequests == null) {
                QuarterCountMap.put(quarter, 1);
            } else {
                QuarterCountMap.put(quarter, numRequests + 1);
            }
        }
        return getQuarterInfo(QuarterCountMap);
    }
    
    /**
     * Returns a list of all log entries stored in the DB.
     * @return List
     */
    public List<RequestLog> getAllLogEntries() {
        MongoCollection<Document> collection = db.getCollection("log");
        MongoCursor<Document> cursor = collection.find().iterator();
        Document doc;
        RequestLog logEntry;
        List<RequestLog> logList = new LinkedList<>();
        while(cursor.hasNext()) {
            doc = cursor.next();
            logEntry = new RequestLog((String)doc.get("requestURL"), (String)doc.get("queryText"), 
                    (String) doc.get("APIRequestURL"), (Integer) doc.get("numSongs"), 
                    (String) doc.get("songList"), (String) doc.get("timeStamp"), 
                    (String) doc.get("date"), (String) doc.get("quarter"));
            logList.add(logEntry);
        }
        Collections.reverse(logList);
        return logList;
    }
    
    /**
     * Helper method for getQuarterPercentages() method. Returns the result in a
     * QuarterInfo object.
     * @param QuarterCountMap
     * @return QuarterInfo
     */
    private QuarterInfo getQuarterInfo(HashMap<String, Integer> QuarterCountMap) {
        List<Map.Entry<String, Integer>> QuarterCountList = new LinkedList<>(QuarterCountMap.entrySet());
        int q1Count = 0, q2Count = 0, q3Count = 0, q4Count = 0;
        Iterator<Map.Entry<String, Integer>> iter = QuarterCountList.iterator();
        Entry<String, Integer> mapEntry;
        String quarter;
        while (iter.hasNext()) {
            mapEntry = iter.next();
            quarter = mapEntry.getKey();
            switch (quarter) {
                case "1":
                    q1Count = mapEntry.getValue();
                    break;
                case "2":
                    q2Count = mapEntry.getValue();
                    break;
                case "3":
                    q3Count = mapEntry.getValue();
                    break;
                case "4":
                    q4Count = mapEntry.getValue();
            }
        }
        return new QuarterInfo(q1Count, q2Count, q3Count, q4Count);
    }
    
    /**
     * Helper method for getTop10SearchedTerms(). It sorts the HashMap and returns
     * a sorted list of query count pairs.
     * @param songCountMap
     * @return List
     */
    private List<QueryCountPair> getTop10List(HashMap<String, Integer> songCountMap) {
        List<Map.Entry<String, Integer>> queryCountList = new LinkedList<>(songCountMap.entrySet());
        Collections.sort(queryCountList, new MapComparator());
        List<QueryCountPair> finalList = new LinkedList<>();
        Iterator<Map.Entry<String, Integer>> iter = queryCountList.iterator();
        int count = 0;
        Entry<String, Integer> mapEntry;
        while (iter.hasNext() && count < 10) {
            mapEntry = iter.next();
            finalList.add(new QueryCountPair(mapEntry.getKey(), mapEntry.getValue()));
            count++;
        }
        return finalList;
    }
    
    /**
     * creates a connection to the Mongo DB.
     */
    private void createConnection() {
        //MongoClientURI mongoURI = new MongoClientURI("mongodb://raj:raj@ds058739.mlab.com:58739/mfa_log_db");
        MongoClientURI mongoURI = new MongoClientURI("mongodb://raj:raj@ds058739.mlab.com:58739/mfa_log_db");
        //mongo = new MongoClient("ds058739.mlab.com", 58739);
        mongo = new MongoClient(mongoURI);
        db = mongo.getDatabase(mongoURI.getDatabase());

    }
    
    /**
     * Helper method for the insert method. Computed the quarter of the
     * day from hours.
     * @param hourString
     * @return 
     */
    private String getQuarterFromHour(String hourString) {
        int hour = Integer.parseInt(hourString);
        String quarter;
        if (hour >= 0 && hour < 6) {
            quarter = "1";
        } else if (hour < 12) {
            quarter = "2";
        } else if (hour < 18) {
            quarter = "3";
        } else {
            quarter = "4";
        }
        return quarter;
    }
    
    /**
     * Comparator to help sort the List in getTop10List() method in descending order. 
     */
    private class MapComparator implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
            Integer val1 = (int) o1.getValue();
            Integer val2 = (int) o2.getValue();
            return val2.compareTo(val1);
        }

    }
}
