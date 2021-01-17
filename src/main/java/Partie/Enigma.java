package Partie;

import Interface.ScreenLoader.LoadMap;
import Interface.Settings.Engine;
import Music.Systems.Son;
import Music.Systems.WorldBoxDisc;

import java.io.IOException;
import java.util.ArrayList;

public class Enigma extends Room{
    String solution; // solution "encode" le résultat de l'énigme
    ArrayList<int[]> consequences; // liste de couples définissant les conséquences de la résolution de l'énigme, forme : (type de conséquence, argument nécessaire à la réalisation de cette conséquence)
    int nb_error; // le nombre d'erreur à avoir été commises

    /**
     * Constructeur d'énigme
     * @param id identifiant de l'énigme
     * @param origin_room identifiant de la salle qui comprote l'énigme
     * @param id_text texte décrivant l'énigme
     * @param path_image URL de l'image de l'énigme
     * @param solution entier encodant la solution de l'énigme
     * @param consequences tableau des couples de conséquences de l'énigme
     */
    public Enigma(int id, int origin_room, int id_text, String path_image, String solution, ArrayList<int[]> consequences){
        super(id,origin_room,id_text,path_image);
        this.solution=solution;
        this.consequences=consequences;
        this.nb_error = 0;
        Game.enigmas.add(this); // ajoute l'énigme à la liste de toutes les énigmes du jeu
    }


    public String getSolution() {
        return solution;
    }

    public ArrayList<int[]> getConsequences() {
        return consequences;
    }


    public void setConsequences(ArrayList<int[]> consequences) {
        this.consequences = consequences;
    }

    /**
     * méthode appliquant les conséquences d'uné énigme après une proposition du joueur
     * @param suggestion proposition du joueur comme solution de l'énigme
     * @throws IOException
     */
    public void check_solution(String suggestion) throws IOException { // vérifie si la suggestion donnée correspond ou non au résultat attendu de l'énigme
        if (getSolution().equals(suggestion)){
            Game.search_room(this.neighbours[2]).search_action_with_enigma(this.getId()).setAvailable(false); // rend l'accès à cette énigme impossible
            Game.player.move(this.neighbours[2]); // renvoie le joueur à l'écran précédent l'énigme
            Engine.engine.answer_box_visible(false);
            this.do_consequences(); // met en place les conséquences de la résolution de l'énigme
        }else{
            nb_error++;
            if(nb_error == 1){
                this.text_evolve(Game.search_text(4004)); // fait évoluer le texte de l'énigme pour que le joueur sâche que sa solution n'est pas la bonne
            }else if(nb_error == 3){
                this.text_evolve(Game.search_text(4005)); // propose au joueur de prendre un indice
            }
        }
    }

    /**
     * méthode appliquant les conséquences d'une énigme
     * @throws IOException
     */
    public void do_consequences() throws IOException { // conséquences liées à la résolution de l'énigme
        for(int i = 0;i<getConsequences().size();i++) {
            switch (getConsequences().get(i)[0]) {
                case 1: // mouvement vers la salle de numéro d'identification consequence[1]
                    Game.player.move(getConsequences().get(i)[1]);
                    break;
                case 2: // dévérrouillage d'une action de numéro d'identification consequence[1]
                    Game.search_action(getConsequences().get(i)[1]).setAvailable(true);
                    break;
                case 3: // vérrouillage d'une action de numéro d'identification consequence[1]
                    Game.search_action(getConsequences().get(i)[1]).setAvailable(false);
                    break;
                case 5: // suppression de l'objet de numéro d'identification consequence[1] de l'inventaire
                    Game.player.remove_from_inventory(Game.search_item(getConsequences().get(i)[1]).id);
                    break;
                case 6: // utilisation de l'objet de numéro d'identification consequence[1]
                    Game.search_item(getConsequences().get(i)[1]).use_item();
                    break;
                case 7: // affichage d'un nouveau texte consequence[i][1] dans la salle consequence[i][2]
                    Game.search_room(getConsequences().get(i)[2]).text_evolve(Game.search_text(getConsequences().get(i)[1]));
                    break;
                case 8: // rend une salle inaccessible
                    Game.search_room(getConsequences().get(i)[1]).setAccess(false);
                    break;
                case 9: // rend une salle accessible
                    Game.search_room(getConsequences().get(i)[1]).setAccess(true);
                    break;
                case 10: // affiche écran fin de partie
                    LoadMap gl = new LoadMap();
                    gl.display_screen_from_id(LoadMap.END_GAME);
                    WorldBoxDisc.play(Son.hibou);
                    WorldBoxDisc.play(Son.valid);
                    break;
            }
        }
    }


}
