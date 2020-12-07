package Interface.Ranking;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class RecordListCell extends ListCell<Record> {

    HBox hbox = new HBox();
    Label lr = new Label(); // label avec le rang du joueur au classement
    Label lp = new Label(); // label avec le pseudo du joueur
    Label ls = new Label(); // label avec le score du joueur

    public RecordListCell() {

        super();

        // instanciation de la Hbox
        hbox.setPrefSize(460, 20);

        // couleur du texte
        lr.setStyle("-fx-text-fill: white;");
        lp.setStyle("-fx-text-fill: white;");
        ls.setStyle("-fx-text-fill: white;");

        // parametrage des tailles des labels
        lr.setPrefSize(40, 10);
        lp.setPrefSize(100, 10);
        ls.setPrefSize(70, 10);

        // parametrage du placement hortizontal
        hbox.setMargin(lr, new Insets(0, 0, 0, 0));
        hbox.setMargin(lp, new Insets(0, 270, 0, 0));
        hbox.setMargin(ls, new Insets(0, 0, 0, 0));

        // ajout de tous les éléments à afficher dans le classement des scores
        hbox.getChildren().addAll(lr, lp, ls);

    }

    public void updateItem (Record record, boolean empty) {

        super.updateItem(record, empty);

        setText(null);
        setGraphic(null);

        if (record != null && !empty) {
            lr.setText(record.rank + " - ");
            lp.setText(record.pseudo);
            ls.setText(String.valueOf(record.score));
            setGraphic(hbox);
        }

    }

}
