import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
public class Main {

    private static ArrayList<Album> albums = new ArrayList<>();
    public static void main(String[] args)
    {

        Album album = new Album("Album1", "THE EPIC SERIES");

        album.addSong("Wonderful Places", 4.5);
        album.addSong("Highway to Hell", 3.5);
        album.addSong("Loving the way you are", 5.0);
        albums.add(album);

        album = new Album("Album2", "Eminem");

        album.addSong("Rap God", 4.5);
        album.addSong("Not Afraid", 3.5);
        album.addSong("Lose Yourself", 4.5);
        albums.add(album);

        album = new Album("Bollywood Classics", "Lata Mangeshkar");

        album.addSong("Lag Ja Gale", 4.0);
        album.addSong("Ajeeb Dastan Hai Yeh", 4.2);
        album.addSong("Pyar Kiya To Darna Kya", 3.8);
        albums.add(album);

        album = new Album("Modern Bollywood", "Arijit Singh");

        album.addSong("Tum Hi Ho", 4.5);
        album.addSong("Channa Mereya", 4.2);
        album.addSong("Raabta", 4.0);
        albums.add(album);

        album = new Album("Bollywood Hits", "Shreya Ghoshal");

        album.addSong("Teri Ore", 3.8);
        album.addSong("Sun Raha Hai", 4.1);
        album.addSong("Deewani Mastani", 4.3);
        albums.add(album);

        album = new Album("Bollywood Party Songs", "Honey Singh");

        album.addSong("Lungi Dance", 3.5);
        album.addSong("Angrezi Beat", 3.8);
        album.addSong("Blue Eyes", 4.0);
        albums.add(album);

        LinkedList<Song> playList_1 = new LinkedList<>();

        albums.get(0).addToPlayList("Wonderful Places", playList_1);
        albums.get(0).addToPlayList("Highway to Hell", playList_1);
        albums.get(1).addToPlayList("Rap God", playList_1);
        albums.get(1).addToPlayList("Lose Yourself", playList_1);
        albums.get(2).addToPlayList("Lag Ja Gale", playList_1);
        albums.get(3).addToPlayList("Tum Hi Ho", playList_1);
        albums.get(4).addToPlayList("Teri Ore", playList_1);
        albums.get(5).addToPlayList("Lungi Dance", playList_1);

        play(playList_1);
    }


    private static void play(LinkedList<Song> playList)
    {
        Scanner sc = new Scanner(System.in);
        boolean quit = false;
        boolean forward = true;
        ListIterator<Song> listIterator = playList.listIterator();

        if (playList.isEmpty())
        {
            System.out.println("This playlist has no songs.");
            return;
        }
        else {
            System.out.println("Now playing: " + listIterator.next().toString());
            printMenu();
        }

        while (!quit)
        {
            int action;
            try
            {
                System.out.print("Enter your choice: ");
                action = sc.nextInt();
                sc.nextLine();
            }
            catch (InputMismatchException e)
            {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (action)
            {
                case 0:
                    System.out.println("Exiting playlist...");
                    quit = true;
                    break;

                case 1: // Play next song
                    if (!forward)
                    {
                        if (listIterator.hasNext()) listIterator.next();
                        forward = true;
                    }
                    if (listIterator.hasNext())
                    {
                        Song nextSong = listIterator.next();
                        nextSong.setLastPlayedDate(getCurrentDate());
                        System.out.println("Now playing: " + nextSong.toString());
                    }
                    else {
                        System.out.println("End of playlist.");
                        forward = false;
                    }
                    break;

                case 2:
                    if (forward)
                    {
                        if (listIterator.hasPrevious()) listIterator.previous();
                        forward = false;
                    }
                    if (listIterator.hasPrevious())
                    {
                        Song prevSong = listIterator.previous();
                        prevSong.setLastPlayedDate(getCurrentDate());
                        System.out.println("Now playing: " + prevSong.toString());
                    }
                    else
                    {
                        System.out.println("Start of playlist.");
                        forward = false;
                    }
                    break;

                case 3:
                    if (forward)
                    {
                        if (listIterator.hasPrevious())
                        {
                            Song currentSong = listIterator.previous();
                            System.out.println("Replaying: " + currentSong.toString());
                            listIterator.next();
                        }
                        else
                        {
                            System.out.println("No song to replay.");
                        }
                    }
                    else
                    {
                        if (listIterator.hasNext())
                        {
                            Song currentSong = listIterator.next();
                            System.out.println("Replaying: " + currentSong.toString());
                            listIterator.previous();
                        }
                        else
                        {
                            System.out.println("No song to replay.");
                        }
                    }
                    break;

                case 4:
                    printList(playList);
                    break;

                case 5:
                    printMenu();
                    break;

                case 6:
                    if (!playList.isEmpty())
                    {
                        listIterator.remove();
                        System.out.println("Current song removed.");
                        if (listIterator.hasNext())
                        {
                            System.out.println("Now playing: " + listIterator.next().toString());
                        }
                        else if (listIterator.hasPrevious())
                        {
                            System.out.println("Now playing: " + listIterator.previous().toString());
                        }
                    }
                    else
                    {
                        System.out.println("Playlist is empty.");
                    }
                    break;

                case 7:
                    System.out.println("Top 10 songs globally:");
                    albums.forEach(album -> album.getTop10Songs().forEach(System.out::println));
                    break;

                case 8:
                    System.out.print("Enter artist name: ");
                    String artist = sc.nextLine();
                    albums.stream()
                            .filter(a -> a.getArtist().equalsIgnoreCase(artist))
                            .forEach(a -> a.getTop10SongsByArtist().forEach(System.out::println));
                    break;



                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("Available options:");
        System.out.println("0 - Quit");
        System.out.println("1 - Play next song");
        System.out.println("2 - Play previous song");
        System.out.println("3 - Replay current song");
        System.out.println("4 - Show playlist");
        System.out.println("5 - Show menu");
        System.out.println("6 - Remove current song");
        System.out.println("7 - Show top 10 songs globally");
        System.out.println("8 - Show top 10 songs by artist");
    }

    private static void printList(LinkedList<Song> playList)
    {
        System.out.println("Playlist:");
        playList.forEach(System.out::println);
    }

    private static String getCurrentDate()
    {
        return java.time.LocalDate.now().toString();
    }
}
