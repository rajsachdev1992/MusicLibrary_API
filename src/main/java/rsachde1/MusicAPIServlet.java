package rsachde1;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Raj.Sachdev
 * 
 * This is the main servlet which acts as an entry point for the web service
 * and the view dashboard service.
 */
@WebServlet(urlPatterns = {"/MusicAPIServlet","/Dashboard"})
public class MusicAPIServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MongoDBUtil mongoDBObj = MongoDBUtil.getInstance();
        //getParameter search text from request
        String queryText = request.getParameter("queryText");
        if (!request.getServletPath().contains("Dashboard")) {
            //create Log entry
            RequestLog logEntry = new RequestLog();
            //find request URL
            String requestURL = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + request.getRequestURI() + "?" + request.getQueryString();

            //store requestURL and queryText in Log entry
            logEntry.setRequestURL(requestURL);
            logEntry.setQueryText(queryText);

            //HTTP get Response from MFA API and parse JSON
            String MFAResponse = ConnectionUtility.getSearchResultsFromAPI(queryText, logEntry);
            AndroidResponse responseUtil = new AndroidResponse();

            //create new JSON for Android 
            List<Song> songList = responseUtil.parseMFAResponseCreateSongList(MFAResponse, logEntry);
            String apiResponse = responseUtil.createAndroidResponse(songList);
            
            //store log entry
            mongoDBObj.insertRecord(logEntry);
            
            //send response to Android
            PrintWriter writer = response.getWriter();
            response.setStatus(200);
            response.setContentType("text/plain;charset=UTF-8");
            writer.write(apiResponse);
        } else {

            //get list of top 10 searched items
            List<QueryCountPair> top10List = mongoDBObj.getTop10SearchedTerms();
            request.setAttribute("top10List", DashboardUtility.createTop10Entry(top10List));

            //get average number of requests per day
            double averageDailyRequests = mongoDBObj.getAverageDailyRequests();
            request.setAttribute("averageDailyRequests", DashboardUtility.createAverageDailyRequestsEntry(averageDailyRequests));

            // get percentage of requests during time of day
            QuarterInfo quarterPercentages = mongoDBObj.getQuarterPercentages();
            request.setAttribute("quarterPercentages", DashboardUtility.createQuarterPercentagesEntry(quarterPercentages));

            //get list of all log entries
            List<RequestLog> logList = mongoDBObj.getAllLogEntries();
            request.setAttribute("logList", DashboardUtility.createLogEntry(logList));

            //redirect to JSP
            RequestDispatcher view = request.getRequestDispatcher("logs.jsp");
            view.forward(request, response);

        }

    }
}
