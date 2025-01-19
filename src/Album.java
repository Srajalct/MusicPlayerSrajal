import java.util.*;
import java.util.stream.Collectors;

public class Album
{
    private String name;
    private String artist;
    private ArrayList<Song> songs;
    private Map<String, Integer> playCount;
    private Map<String, List<Song>> playHistory; // Date -> Songs played
    private Random random;

    public Album(String name, String artist)
    {
        this.name = name;
        this.artist = artist;
        this.songs = new ArrayList<>();
        this.playCount = new HashMap<>();
        this.playHistory = new HashMap<>();
        this.random = new Random();
    }

    public Album()
    {
        this.songs = new ArrayList<>();
        this.playCount = new HashMap<>();
        this.playHistory = new HashMap<>();
        this.random = new Random();
    }

    public Song findSong(String title)
    {
        for (Song checkedSong : songs)
        {
            if (checkedSong.getTitle().equals(title))
            {
                return checkedSong;
            }
        }
        return null;
    }



    public boolean addSong(String title, double duration)
    {
        if (title == null || title.isEmpty())
        {
            System.out.println("Invalid song title. Cannot add song.");
            return false;
        }
        if (findSong(title) == null)
        {
            songs.add(new Song(title, duration, artist));
            return true;
        }
        else
        {
            System.out.println("Song with name " + title + " already exists in the album.");
            return false;
        }
    }

    public void playSong(String title, String artist, String date)
    {
        try
        {
            Song song = findSong(title);
            if (song != null && this.artist.equals(artist))
            {
                playCount.put(title, playCount.get(title) + 1);

                playHistory.putIfAbsent(date, new ArrayList<>());
                playHistory.get(date).add(song);

                System.out.println("Playing: " + title + " by " + artist);
            }
            else
            {
                throw new SongNotFoundException("Song not found: " + title + " by " + artist);
            }
        }
        catch (SongNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public boolean addToPlayList(int trackNumber, LinkedList<Song> playList)
    {
        int index = trackNumber - 1;
        if (index >= 0 && index < this.songs.size())
        {
            playList.add(this.songs.get(index));
            return true;
        }
        return false;
    }

    public boolean addToPlayList(String title, LinkedList<Song> playList)
    {
        Song song = findSong(title);
        if (song != null)
        {
            playList.add(song);
            return true;
        }
        return false;
    }

    public List<String> getTop10Songs()
    {
        return playCount.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> getTop10SongsByArtist()
    {
        return songs.stream()
                .sorted((a, b) -> playCount.getOrDefault(b.getTitle(), 0) - playCount.getOrDefault(a.getTitle(), 0))
                .limit(10)
                .map(Song::getTitle)
                .collect(Collectors.toList());
    }

    public List<String> getTop10SongsByDate(String date)
    {
        return playHistory.getOrDefault(date, new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(Song::getTitle, Collectors.counting()))
                .entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> getSongsPlayedLessThan(int count, String date)
    {
        return playHistory.getOrDefault(date, new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(Song::getTitle, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() < count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> getTopSingersGlobally(Map<String, Album> allAlbums)
    {
        return allAlbums.values().stream()
                .flatMap(album -> album.songs.stream())
                .collect(Collectors.groupingBy(Song::getArtist, Collectors.counting()))
                .entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(20)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void discardArtistsPlayedLessThan(int count, String date)
    {
        Map<String, Long> artistPlayCount = playHistory.getOrDefault(date, new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(Song::getArtist, Collectors.counting()));

        artistPlayCount.entrySet().removeIf(e -> e.getValue() < count);
    }

    public void playRandomSongByArtist()
    {
        if (!songs.isEmpty())
        {
            Song randomSong = songs.get(random.nextInt(songs.size()));
            playSong(randomSong.getTitle(), randomSong.getArtist(), "2025-01-16");
        }
        else
        {
            System.out.println("No songs available for this artist.");
        }
    }

    public void playSpecificSong(String title, String artist)
    {
        if (this.artist.equals(artist))
        {
            playSong(title, artist, "2025-01-16");
        }
        else
        {
            System.out.println("Artist mismatch or song not found.");
        }
    }

    public String getArtist()
    {
        return artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
