package rsachde1;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Raj.Sachdev
 * 
 * Provides methods to create Dashboard components.
 */
public class DashboardUtility {
    
    /**
     * Creates an HTML entry for top 10 searched terms.
     * @param queryCountList
     * @return String
     */
    public static String createTop10Entry(List<QueryCountPair> queryCountList) {
        StringBuilder top10String = new StringBuilder();
        top10String.append("<table style=\"width:100%\">\n");
        top10String.append("<tr>\n");
        top10String.append("<th>").append("Search Term").append("</th>");
        top10String.append("<th>").append("Times Searched").append("</th>");
        top10String.append("</tr>\n");
        Iterator<QueryCountPair> iterator = queryCountList.iterator();
        QueryCountPair queryCountPair;
        while (iterator.hasNext()) {
            queryCountPair = iterator.next();
            top10String.append("<tr>\n");
            top10String.append("<td>").append(queryCountPair.getQuery()).append("</td>");
            top10String.append("<td>").append(queryCountPair.getCount()).append("</td>");
            top10String.append("</tr>\n");
        }
        top10String.append("</table>");

        return top10String.toString();
    }
    
    /**
     * Creates an entry for displaying the average number of daily requests
     * on the Dashboard.
     * @param averageDailyRequests
     * @return String
     */
    public static String createAverageDailyRequestsEntry(double averageDailyRequests) {
        StringBuilder finalString = new StringBuilder();
        finalString.append("<table style=\"width:100%\">\n");
        finalString.append("<tr>\n");
        finalString.append("<th>").append("Average Requests per Day").append("</th>");
        finalString.append("<td>").append(averageDailyRequests).append("</td>");
        finalString.append("</tr>\n");
        finalString.append("</table>");
        return finalString.toString();
    }
    
    /**
     * Creates a Dashboard entry to show percentage of requests received in 
     * different parts of the day.
     * @param quarterInfo
     * @return String
     */
    public static String createQuarterPercentagesEntry(QuarterInfo quarterInfo) {
        StringBuilder finalString = new StringBuilder();
        finalString.append("<table style=\"width:100%\">\n");
        finalString.append("<tr>\n");
        finalString.append("<th>").append("12am - 5.59am").append("</th>");
        finalString.append("<td>").append(quarterInfo.getQ1Percent()).append("%").append("</td>");
        finalString.append("</tr>\n");

        finalString.append("<tr>\n");
        finalString.append("<th>").append("6am - 11.59am").append("</th>");
        finalString.append("<td>").append(quarterInfo.getQ2Percent()).append("%").append("</td>");
        finalString.append("</tr>\n");

        finalString.append("<tr>\n");
        finalString.append("<th>").append("12pm - 5.59pm").append("</th>");
        finalString.append("<td>").append(quarterInfo.getQ3Percent()).append("%").append("</td>");
        finalString.append("</tr>\n");

        finalString.append("<tr>\n");
        finalString.append("<th>").append("6pm - 11.59pm").append("</th>");
        finalString.append("<td>").append(quarterInfo.getQ4Percent()).append("%").append("</td>");
        finalString.append("</tr>\n");
        finalString.append("</table>");
        return finalString.toString();
    }
    
    /**
     * Creates a Dashboard entry to display the log entries for all requests, starting 
     * from the most recent.
     * @param logList
     * @return String
     */
    public static String createLogEntry(List<RequestLog> logList) {
        StringBuilder finalString = new StringBuilder();
        finalString.append("<table style=\"width:100%\">\n");
        RequestLog logEntry;
        Iterator<RequestLog> iterator = logList.iterator();
        while (iterator.hasNext()) {
            logEntry = iterator.next();
            finalString.append("<tr>\n");
            finalString.append("<td>\n");
            finalString.append("<b>Request URL: </b>").append(logEntry.getRequestURL()).append("<br>");
            finalString.append("<b>Query Text: </b>").append(logEntry.getQueryText()).append("<br>");
            finalString.append("<b>Request to MFA API: </b>").append(logEntry.getAPIRequestURL()).append("<br>");
            finalString.append("<b>Number of songs recieved: </b>").append(logEntry.getNumSongs()).append("<br>");
            finalString.append("<b>List of songs received: </b>").append(logEntry.getSongList()).append("<br>");
            finalString.append("<b>Time of completion: </b>").append(logEntry.getTimestampRecieved()).append("<br>");
            finalString.append("</td>\n");
            finalString.append("</tr>\n");
        }
        finalString.append("</table>");
        return finalString.toString();
    }

}
