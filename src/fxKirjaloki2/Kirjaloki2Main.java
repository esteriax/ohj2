package fxKirjaloki2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import kirjaloki.Kirjaloki;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author heta
 * @version 30.1.2026
 *
 */
public class Kirjaloki2Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("Kirjaloki2GUIView.fxml"));
            final Pane root = ldr.load();
            final Kirjaloki2GUIController kirjaloki2Ctrl = (Kirjaloki2GUIController) ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kirjaloki2.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kirjaloki");
            primaryStage.show();
            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !kirjaloki2Ctrl.voikoSulkea() ) event.consume();
            });
            
            Kirjaloki kirjaloki = new Kirjaloki();  
            kirjaloki2Ctrl.setKirjaloki(kirjaloki); 
            
            if (!kirjaloki2Ctrl.avaa()) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}