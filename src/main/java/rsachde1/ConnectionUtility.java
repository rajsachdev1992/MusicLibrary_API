package rsachde1;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raj.Sachdev
 * Provides methods to connect to the Music Finder API and get responses based
 * on user inputs.
 */
public class ConnectionUtility {

    /**
     * Creates URL to hit the MFA API for song search.
     *
     * @param searchText
     * @return String: URL
     */
    private static String createSongSearchURL(String searchText) {
        String url = "http://freemusicarchive.org/api/get/tracks.json?api_key=60BLHNQCAOUFPIBZ&limit=10&q={1}";
        try {
            searchText = URLEncoder.encode(searchText, "UTF-8");
            url = url.replace("{1}", searchText);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConnectionUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("MFA URL: " + url);
        return url;
    }

    /**
     *
     * @param searchText
     * @param logEntry
     * @return String: JSON response from MFA API
     */
    public static String getSearchResultsFromAPI(String searchText, RequestLog logEntry) {
        // Make an HTTP GET passing the name on the URL line
        String response = "";
        HttpURLConnection conn;
        int status = 0;

        try {

            // pass the name on the URL line
            URL url = new URL(createSongSearchURL(searchText));
            logEntry.setAPIRequestURL(url.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/plain");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            // wait for response
            status = conn.getResponseCode();

            // If things went poorly, don't try to read any response, just return.
            if (status != 200) {
                System.out.println("ERROR: " + status);
                response = "{}";
            } else {
                String output;
                // things went well so let's read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                while ((output = br.readLine()) != null) {
                    response += output;

                }
            }

            conn.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(ConnectionUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionUtility.class.getName()).log(Level.SEVERE, null, ex);
        }

        // return HTTP status to caller
        System.out.println("response: " + response);
        return response;
    }

}
