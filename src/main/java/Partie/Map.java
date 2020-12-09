package Partie;

import java.util.ArrayList;

public class Map { //Inutile pour le moment
    ArrayList<Room> map;
    //image?

    public Map(){
        this.map= new ArrayList<Room>();
    }
    public void add_room(Room room){
        this.map.add(room);
    }

    public Room search_room(int nb){
        int i=0;
        while(this.map.get(i).nb!=nb){
            i++;
        }
        return this.map.get(i);
    }
}

/* création gale à mettre dans le Main :

Player player1 = new Player("joueur_test");
Game game_test = new Game(player1,0);

ArrayList<String> sounds_test = new ArrayList<>;

Room room1 = new Room(1011,"url1",101,102,-1,-1,-1,sounds_test); //première salle
Room room2 = new Room(1021,"url2",102,-1,-1,101,-1,sounds_test); //deuxième salle
Room room1_1 = new Room(2011,"url11",201,101,sounds_test); //table dans salle 1
Room room1_2 = new Room(2021,"url12",202,101,sounds_test); //porte dans salle 1
Room room1_1_1 = new Room(2031,"url13",203,201,sounds_test); //vase sur table dans salle 1


//Enigme de la salle 102

ArrayList<int[]> consequences_enigme1 = new ArrayList<>;
consequences_enigme1 = {{4,0}};
Enigma enigme1 = new Enigma(3011,"url13",301,102,2022,consequences_enigme1,sounds_test);


//Création des objets

Gear objet1 = new Gear(1,"clef","Clef ancienne, sert probablement pour déverouiller une serure",1)


//Actions de la salle 101

ArrayList<int[]> consequences_action1011 = new ArrayList<>;
consequences_action1011 = {{1,102}};
Action action1011 = new Action(1011,"On s'en fout du texte ici car flèche", consequences_action1011, 101, false); //déplacement salle 101 à 102

ArrayList<int[]> consequences_action1012 = new ArrayList<>;
consequences_action1012 = {{1,201}};
Action action1012 = new Action(1012,"Examiner la table", consequences_action1012, 101, true); //déplacement vers table, 101 à 201

ArrayList<int[]> consequences_action1013 = new ArrayList<>;
consequences_action1013 = {{1,202}};
Action action1013 = new Action(1013,"Examiner la porte", consequences_action1013, 101, true); //déplacement vers la porte, 101 à 202


//Actions de la salle 201 (table)

ArrayList<int[]> consequences_action2011 = new ArrayList<>;
consequences_action2011 = {{1,101}};
Action action2011 = new Action(2011,"On s'en fout du texte ici car flèche", consequences_action2011, 201, true); //retour à la salle 101

ArrayList<int[]> consequences_action2012 = new ArrayList<>;
consequences_action2012 = {{1,203}};
Action action2012 = new Action(2012,"Examiner le vase", consequences_action2012, 201, true); //déplacement vers le vase, 201 à 203


//Actions de la salle 203 (vase)

ArrayList<int[]> consequences_action2031 = new ArrayList<>;
consequences_action2031 = {{4,1},{3,2012},{1,201},{8,2012}};
Action action2031 = new Action(2031,"Récupérer la clef", consequences_action2031, 203, true); // ajout de la clef à l'inventaire, déplacement vers salle précédente (201) puis mise à jour du texte de cette salle  et verrouillage de l'action permettant d'examiner le vase


//Actions de la salle 202 (porte)

ArrayList<int[]> consequences_action2021 = new ArrayList<>;
consequences_action2021 = {{1,101}};
Action action2021 = new Action(2021,"On s'en fout du texte ici car flèche", consequences_action2021, 202, true); //retour à la salle 101

ArrayList<int[]> consequences_action2022 = new ArrayList<>;
consequences_action2022 = {{8,2022}};
Action action2022 = new Action(2022,"Essayer d'enfoncer la porte", consequences_action2022, 202, true); //mise à jour du texte

ArrayList<int[]> consequences_action2023 = new ArrayList<>;
consequences_action2023 = {{6,1},{2,1011},{3,1013},{8,1012},{1,101}};
Action action2023 = new Action(2023,"Utiliser la clef", consequences_action2023, 202, 1); //ajoute une utilisation à la clef, dévérouille l'action permetant d'accéder à 102 depuis 101, bloque action d'examiner la porte depuis 101, ajoute texte à salle 101, retour dans 101


//Actions de la salle 102

ArrayList<int[]> consequences_action1021 = new ArrayList<>;
consequences_action1021 = {{1,101}};
Action action1021 = new Action(1021,"On s'en fout du texte ici car flèche", consequences_action1021, 102, true); //déplacement salle 102 à 101

ArrayList<int[]> consequences_action1022 = new ArrayList<>;
consequences_action1022 = {{1,301}};
Action action1022 = new Action(1022,"Examiner le tableau", consequences_action1022, 102, true); //déplacement vers table, 102 à 301

//Actions de l'énigme 301

ArrayList<int[]> consequences_action3011 = new ArrayList<>;
consequences_action3011 = {{8,3012}};
Action action3011 = new Action(3011,"Demander un indice", consequences_action3011, 301, true); //mise à jour du texte

// /!\ Action de proposition à gérer /!\

 */
