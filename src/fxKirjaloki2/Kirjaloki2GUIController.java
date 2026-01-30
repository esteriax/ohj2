package fxKirjaloki2;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author heta
 * @version 30.1.2026
 *
 */
public class Kirjaloki2GUIController {
    public void eiToimi() {
        Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Huomautus");
          alert.setHeaderText(null);
          alert.setContentText("Ei toimi vielä");
          alert.getDialogPane().setStyle("-fx-font-family: monospace;");
          alert.showAndWait();
    }
}