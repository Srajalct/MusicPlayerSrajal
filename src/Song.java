import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Song {
    private String title;
    private double duration;
    private String artist;
    private String lastPlayedDate;

    public Song(String title, double duration, String artist)
    {
        if (title == null || title.isEmpty())
        {
            throw new IllegalArgumentException("Song title cannot be null or empty.");
        }
        if (duration <= 0)
        {
            throw new IllegalArgumentException("Song duration must be greater than zero.");
        }
        if (artist == null || artist.isEmpty())
        {
            throw new IllegalArgumentException("Artist name cannot be null or empty.");
        }

        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.lastPlayedDate = null;
    }

    public String getTitle() {
        return title;
    }

    public double getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getLastPlayedDate() {
        return lastPlayedDate;
    }

    public void setLastPlayedDate(String lastPlayedDate) {
        this.lastPlayedDate = lastPlayedDate;
    }

    public void updateLastPlayedDate() {
        LocalDate today = LocalDate.now();
        this.lastPlayedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Song{")
                .append("title='").append(title).append('\'')
                .append(", duration=").append(duration)
                .append(", artist='").append(artist).append('\'');
        if (lastPlayedDate != null) {
            sb.append(", lastPlayedDate='").append(lastPlayedDate).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}

