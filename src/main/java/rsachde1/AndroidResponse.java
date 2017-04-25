package rsachde1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Raj.Sachdev
 * 
 * Utility to create a response for Android application.
 */
public class AndroidResponse {
    
    /**
     * Parses the JSON response from MFA and creates a list of songs received.
     * @param MFAResponse
     * @param logEntry
     * @return List<Song> 
     */
    public List<Song> parseMFAResponseCreateSongList(String MFAResponse, RequestLog logEntry) {
        List<Song> songList = new LinkedList<>();
        try {
            JSONObject mfaObj = new JSONObject(MFAResponse);
            JSONArray dataset = mfaObj.getJSONArray("dataset");
            StringBuilder songListLogEntry = new StringBuilder("");
            if (dataset != null) {
                JSONObject songJSONObj;
                Song song;
                for (int i = 0; i < dataset.length(); i++) {
                    song = new Song();
                    songJSONObj = (JSONObject) dataset.get(i);
                    song.setSongName(songJSONObj.getString("track_title"));
                    song.setSongURL(songJSONObj.getString("track_url"));
                    song.setImageURL(songJSONObj.getString("track_image_file"));
                    song.setArtist(songJSONObj.getString("artist_name"));
                    song.setAlbum(songJSONObj.getString("album_title"));
                    songList.add(song);
                    songListLogEntry.append(song.getSongName()).append(",");
                }
                songListLogEntry.deleteCharAt(songListLogEntry.length() - 1);
                logEntry.setNumSongs(dataset.length());
                logEntry.setSongList(songListLogEntry.toString());
            }
        } catch (JSONException ex) {
            Logger.getLogger(AndroidResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songList;
    }
    
    /**
     * creates a JSON response for the Android application from the songList.
     * @param songList
     * @return String: JSONResponse to be sent to Android.
     */
    public String createAndroidResponse(List<Song> songList) {
        Iterator<Song> songIterator = songList.iterator();
        JSONObject mainObj = new JSONObject();
        JSONArray songArray = new JSONArray();
        JSONObject songJSONObj;
        Song song;
        try {
            while (songIterator.hasNext()) {
                song = songIterator.next();
                songJSONObj = new JSONObject();
                songJSONObj.put("songName", song.getSongName());
                songJSONObj.put("songURL", song.getSongURL());
                songJSONObj.put("imageURL", song.getImageURL());
                songJSONObj.put("artist", song.getArtist());
                songJSONObj.put("album", song.getAlbum());
                songArray.put(songJSONObj);
            }
            mainObj.put("numSongs", songArray.length());
            mainObj.put("songList", songArray);
        } catch (JSONException ex) {
            Logger.getLogger(AndroidResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mainObj.toString();
    }
}
