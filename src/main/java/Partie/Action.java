package Partie;

import Interface.Settings.Engine;
import Interface.ViewController.gameController;

import java.util.ArrayList;

public class Action {
    int id; // numéro d'identification de l'action
    public String text; // description textuelle de l'action
    boolean doable; // action accessible pour le joueur ou non, à analyser pour savoir si l'action doit être affichée ou non
    ArrayList<int[]> consequence; // liste de couples définissant les conséquence de l'action, forme (type de conséquence, argument nécessaire à la réalisation de cette conséquence)

    public String getText() {
        return text;
    }

    public Action(int id,String text, ArrayList<int[]> consequence, int room, boolean doable) { //Action "générale", doable à false pour les déplacement vers salle vérouillées au départ par exemple
        this.id=id;
        this.text = text;
        this.consequence=consequence;
        this.doable = doable;
        Game.search_room(room).add_action(this); // ajoute l'action à la liste des actions réalisables dans la salle à laquelle elle est liée
        Game.actions.add(this); // ajoute l'action à la liste des actions du jeu
    }

    public Action(int id,String text, ArrayList<int[]> consequence, int room, int id_gear) { //Action liée à un objet
        this.id=id;
        this.text = text;
        this.consequence = consequence;
        this.doable = false;
        Game.search_room(room).add_action(this); // ajoute l'action à la liste des actions réalisables dans la salle à laquelle elle est liée
        Game.actions.add(this); // ajoute l'action à la liste des actions du jeu
        Game.search_gear(id_gear).actions.add(this); // ajoute l'action à la liste des actions nécessitant l'objet renseigné
    }

    public void setDoable(boolean doable) { // rend une action réalisable par le joueur
        this.doable = doable;
    }

    public boolean getDoable() {
        return doable;
    }

    public void Consequence(){
        for(int i =0;i<consequence.size();i++){
            switch (this.consequence.get(i)[0]) {
                case 1: // mouvement vers la salle de numéro d'identification arg_conséquence
                    Game.player.move(this.consequence.get(i)[1]);
                    break;
                case 2: // dévérouillage d'une action de numéro d'identification arg_conséquence
                    Game.search_action(this.consequence.get(i)[1]).setDoable(true);
                    Engine.engine.refreshAction();
                    break;
                case 3: // vérouillage d'une action de numéro d'identification arg_conséquence
                    Game.search_action(this.consequence.get(i)[1]).setDoable(false);
                    Engine.engine.refreshAction();
                    break;
                case 4: // ajout de l'objet de numéro d'identification arg_consequence à l'inventaire
                    if(Game.player.add_inventory(Game.search_gear(this.consequence.get(i)[1]))){ // Si l'ajout à l'inventaire se passe bien (il reste de la place dans l'inventaire)
                        Game.search_action(this.id).setDoable(false); // rend l'obtention de cette objet impossible
                     }
                     break;
                case 5: // suppression de l'objet de numéro d'identification arg_consequence de l'inventaire
                     Game.player.remove_inventory(Game.search_gear(this.consequence.get(i)[1]));
                     break;
                case 6: // utilisation de l'objet de numéro d'identification arg_consequence
                    Game.search_gear(this.consequence.get(i)[1]).use_gear();
                    break;
                case 7: // affichage d'un nouveau texte (fonctionne aussi pour la demande d'indice)
                    Game.search_room(Game.player.position).txt_evolve(Game.search_txt(this.consequence.get(i)[1])); // fait évoluer le texte de la salle dans laquelle le joueur se trouve en lui ajoutant le texte ayant pour id le code renseigné
                    break;
                case 8: // rend une salle inaccessible
                    Game.search_room(this.consequence.get(i)[1]).setAccess(false);
                    break;
                case 9: // rend une salle accessible
                    Game.search_room(this.consequence.get(i)[1]).setAccess(true);
                break;

             }
        }
    }
}
