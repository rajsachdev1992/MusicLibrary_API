package rsachde1;
/**
 *
 * @author Raj.Sachdev
 * 
 * Stores all the information about a Song received from the MFA API to be sent 
 * to Android.
 */
public class Song {
    
    private String songName;    //title
    private String songURL;     //streaming links
    private String imageURL;    //image link
    private String artist;      //artist of song
    private String album;       //album of song

    public Song(String songName, String songURL, String imageURL, String artist, String album) {
        this.songName = songName;
        this.songURL = songURL;
        this.imageURL = imageURL;
        this.artist = artist;
        this.album = album;
    }
    
    public Song() {
        //default constuctor
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
    
}
