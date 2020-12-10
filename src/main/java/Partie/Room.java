package Partie;

import java.util.ArrayList;

public class Room {
    String txt; // url du texte lié à la salle
    String path_image; // url de l'image liée à la salle
    int nb; // le chiffre des centaines vaut 1 si c'est une salle, 2 si c'est un lieu d'interaction
    int[] neighbours = new int [4]; // salles voisines à la salle
    ArrayList<Action> actions; // actions rélisables dans cette salle
    ArrayList<String> sounds; // nom des sons joués dans la salle

    public Room(int id_txt, String path_image, int nb, int neighbour_north, int neighbour_east, int neighbour_south, int neighbour_west, ArrayList<String> sounds){
        this.txt = Game.search_txt(id_txt);
        this.path_image = path_image;
        this.nb = nb;
        this.neighbours[0] = neighbour_north;
        this.neighbours[1] = neighbour_east;
        this.neighbours[2] = neighbour_south;
        this.neighbours[3] = neighbour_west;
        this.actions = new ArrayList<>();
        this.sounds = sounds;
        Game.map.add(this); // ajoute la salle à la liste des salles/sous-salles/énigmes disponibles dans le jeu
    }

    public Room(int id_txt, String path_image, int nb, int origin_room, ArrayList<String> sounds){ //"Sous-salle", càd entité dans une (sous-)salle, ex : table inspectable ou énigme
        this.txt = Game.search_txt(id_txt);
        this.path_image = path_image;
        this.nb=nb;
        this.neighbours[0]=-1;
        this.neighbours[1]=-1;
        this.neighbours[2]=origin_room; // un seul voisin qui est la salle depuis laquelle cette sous-salle est accessible
        this.neighbours[3]=-1;
        this.actions = new ArrayList<>();
        this.sounds = sounds;
        Game.map.add(this); // ajoute la salle à la liste des salles/sous-salles/énigmes disponibles dans le jeu
    }

    public void add_action(Action action){ // ajoute une action à la liste des actions possibles de la room
        this.actions.add(action);
    }

    public Action search_access_enigma(int nb){ // recherche l'action qui donne accès à l'énigme numéro nb
        Action action = null;
        for(int i = 0; i< this.actions.size();i++){
            for(int j = 0; j< this.actions.get(i).consequence.size();i++){
              if(this.actions.get(i).consequence.get(j)[0] == 1 && this.actions.get(i).consequence.get(j)[1] == nb){
                  action =  this.actions.get(i);
              }
            }
        }
        return action;
    }

    public void txt_evolve(String more_txt){ // rajoute du texte au texte à afficher avec la salle
        this.txt = this.txt + more_txt;
        // refresh texte
    }
}
