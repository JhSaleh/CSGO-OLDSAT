package Music.Systems;

import Timer.Timer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Cette classe permet de jouer plusieurs musiques différentes à la suite les unes des autres
 */

public class PlayList extends Thread {
    /**
     * Tableau des String/Nom des musiques de la playlist. UTILISE SEULEMENT LES STRINGS DE LA CLASSE Son
     */
    private ArrayList<String> playlist;

    /**
     * Valable en mode régulier/mode = false. Durée entre chaque piste de musique correspond aux éléments du tableau.
     */
    private ArrayList<Integer> timeInBetweenA;
    /**
     * Valable en mode irrégulier/mode = true. Durée entre chaque piste est de timeInBetween secondes.
     */
    private int timeInBetween;

    /**
     * Mode de lecture de la playlist
     * mode = false : mode régulier
     * mode = true : mode irégulier
     */
    private boolean mode;
    /**
     * Initialise une playlist vide en mode régulier.
     */
    public PlayList(){
        this.playlist = new ArrayList<>();
        this.timeInBetween = 0;
        this.mode = false;
        this.timeInBetweenA = new ArrayList<>();
    }

    /**
     * Insère dans une playlist toutes les pistes désirés de façon régulière
     * @param inPlaylist
     */
    public PlayList(ArrayList<String> inPlaylist){
        this.playlist = inPlaylist;
        this.timeInBetween = 0;
        this.mode = false;
    }

    /**
     * Initialise une playlist irrégulière
     * @param inPlayList
     * @param inTimeInBetweenA
     */
    public PlayList(ArrayList<String> inPlayList, ArrayList<Integer> inTimeInBetweenA){
        this.playlist = inPlayList;
        setTimeInBetweenA(inTimeInBetweenA);
        this.timeInBetween = 0;
        this.mode = true;
    }

    /**
     * Ajoute une piste à la playlist
     * @param aSound nom de la piste
     */
    public void addSound(String aSound){
        playlist.add(aSound);
    }

    /**
     * Affecte le temps entre element de la playlist
     * @param timeInBetween
     */
    public void setTimeInBetween(int timeInBetween) {
        this.timeInBetween = timeInBetween;
    }

    public void setTimeInBetweenA(ArrayList<Integer> inTimeInBetweenA){
        this.timeInBetweenA = inTimeInBetweenA;
        if(timeInBetweenA.size() != playlist.size() - 1){
            throw new IllegalArgumentException("timeInBetweenA devrait avoir : " + Integer.toString(playlist.size()-1) + "de cases.\n Pour rappel chaques cases du tableau correspond à un temps mort en secondes entre 2 pistes.\n\n");
        }
    }

    @Override
    public void run() {
        for(int i = 0; i < playlist.size(); i++){
            WorldBoxDisc.play(playlist.get(i));

            //Mode de lecture
            if(!mode) { //mode régulier où les pistes sont espacés par timeInBetween secondes
                Timer.sleep(timeInBetween * 1000 + WorldBoxDisc.worldBoxMusic.get(WorldBoxDisc.idWorldBoxMusic.get(playlist.get(i))).duration * 1000);
            } else {//mode où les musiques sont espacé par des temps irréguliers
                Timer.sleep(timeInBetweenA.get(i) * 1000 + WorldBoxDisc.worldBoxMusic.get(WorldBoxDisc.idWorldBoxMusic.get(playlist.get(i))).duration * 1000);
            }

            //Coupe le son de certaines musiques qui loop ou de fonds sonores
            if (WorldBoxDisc.worldBoxMusic.get(WorldBoxDisc.idWorldBoxMusic.get(playlist.get(i))).type == MusicType.Background) { //Garantit que les musiques de fonds s'arrètent
                WorldBoxDisc.stop(playlist.get(i));
            } else if (WorldBoxDisc.worldBoxMusic.get(WorldBoxDisc.idWorldBoxMusic.get(playlist.get(i))).loop) {
                WorldBoxDisc.stop(playlist.get(i));
            }

        }
    }

    public static void main(String[] args) {
        WorldBoxDisc.init();
        Random generator = new Random();
        ArrayList<String> testo = new ArrayList<>();
        ArrayList<Integer> timeTest = new ArrayList<>();
        ArrayList<Integer> oracle = new ArrayList<>();

        for(int i = 0; i < WorldBoxDisc.total; i++){
            if(WorldBoxDisc.worldBoxMusic.get(i).type == MusicType.SoundFx && WorldBoxDisc.worldBoxMusic.get(i).name != "coeur"){
                testo.add(WorldBoxDisc.worldBoxMusic.get(i).name);
                oracle.add(generator.nextInt(10)%10);
            }
        }
        oracle.remove(oracle.size()-1);

        PlayList playList = new PlayList(testo, oracle);

        playList.start();
    }
}
