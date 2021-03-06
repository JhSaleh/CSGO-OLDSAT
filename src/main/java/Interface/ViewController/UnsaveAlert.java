package Interface.ViewController;

import Interface.Save.SaveSlot;
import Interface.Save.Saves;
import Interface.ScreenLoader.LoadMap;
import Music.Systems.Son;
import Music.Systems.WorldBoxDisc;
import Partie.Game;
import Serialization.Memoire;
import Serialization.Serial_game;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

// =================================== //
// Pop up de gestion des sortie de jeu //
// =================================== //

public class UnsaveAlert {

    Alert alert = new Alert(Alert.AlertType.WARNING);
    ButtonType yes = new ButtonType("Oui");
    ButtonType no = new ButtonType("Non");
    ButtonType undo = new ButtonType("Annuler");

    /**
     * Pop up demandant si le joueur veut sauvegarder sa partie avant de quitter l'application
     */
    public void exitGame() throws IOException {
        alert.setTitle("OLD'SAT");
        alert.setHeaderText("Vous allez quitter l'application.\nSouhaitez vous sauvegarder ?");

        // Remove default ButtonTypes
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yes, no, undo);

        // traitement de la réponse
        if (Game.getBas() == 0) {
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() != undo) {

                // Option de sauvegarde quand appui sur OUI
                if (option.get() == yes) {

                    // VVV sauvegarde VVV
                    File file = new File("resources/json/saves.json");
                    Memoire m = new Memoire();
                    Saves saves = (Saves) m.read_data(file); // récupération des saves

                    // recherche du bon slot par pseudo /!\ -> ca peut generer des problemes
                    boolean saved = false;
                    for (int i = 0; i < 10; i++) {
                        if ((saves.getSave(i).srgame != null ) && (!saved && Game.player.getPseudo().equals(saves.getSave(i).srgame.player.getPseudo()))) {
                            saves.setSave(i, new SaveSlot(i, new Serial_game()));
                            saved = true;
                        }
                    }

                    m.write_data(saves, file);

                }
                LoadMap.stage.close(); // fermeture de l'application
            }
        } else {
            LoadMap.stage.close(); // fermeture de l'application
        }
    }

    /**
     * Pop up demandant si le joueur veut sauvegarder sa partie avant de revenir à l'accueil
     * @throws InterruptedException
     */
    public void homeScreen() throws IOException {

        alert.setTitle("OLD'SAT");
        alert.setHeaderText("Vous allez quitter l'application.\nSouhaitez vous sauvegarder ?");

        // Remove default ButtonTypes
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yes, no, undo);

        // traitement de la réponse
        if (Game.getBas() == 0) {
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() != undo) {

                // Option de sauvegarde quand appui sur OUI
                if (option.get() == yes) {

                    // VVV sauvegarde VVV
                    File file = new File("resources/json/saves.json");
                    Memoire m = new Memoire();
                    Saves saves = (Saves) m.read_data(file); // récupération des saves

                    // recherche du bon slot par pseudo /!\ les pseudos sont uniques
                    boolean saved = false;
                    for (int i = 0; i < 10; i++) {
                        if (!saved && Game.player.getPseudo().equals(saves.getSave(i).srgame.player.getPseudo())) {
                            saves.setSave(i, new SaveSlot(i, new Serial_game()));

                            saved = true;
                        }
                    }

                    m.write_data(saves, file);

                }
                // retour écran accueil
                Game.reset_game();
                LoadMap gl = new LoadMap();
                gl.display_screen_from_id(LoadMap.HOME);
                WorldBoxDisc.pauseAllBackgroundSound();
                WorldBoxDisc.play(Son.menuTheme);
            }
        } else {
            // retour écran accueil
            Game.reset_game();
            LoadMap gl = new LoadMap();
            gl.display_screen_from_id(LoadMap.HOME);
            WorldBoxDisc.pauseAllBackgroundSound();
            WorldBoxDisc.play(Son.menuTheme);
        }
    }
}
