package fxKirjaloki2;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;

/**
 * @author heta
 * @version 19.2.2026
 *
 */
public class TulostusController implements ModalControllerInterface<String> {
    @FXML TextArea tulostusAlue;
    
    @FXML private void handleOK() {
        ModalController.closeStage(tulostusAlue);
    }
    
    @FXML private void handleTulosta() {
     // Dialogs.showMessageDialog("Ei osata vielä tulostaa");
        PrinterJob job = PrinterJob.createPrinterJob();
        if ( job != null && job.showPrintDialog(null) ) {
            WebEngine webEngine = new WebEngine();
            webEngine.loadContent("<pre>" + tulostusAlue.getText() + "</pre>");
            webEngine.print(job);
            job.endJob();
        }
    }
    
    /**
     * @return null
     */
    @Override
    public String getResult() {
        return null;
    } 

    
    /**
     * @param oletus oletusteksti
     */
    @Override
    public void setDefault(String oletus) {
        // if ( oletus == null ) return;
        tulostusAlue.setText(oletus);
    }
    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        //
    }

    /**
     * Näyttää tulostusalueessa tekstin
     * @param tulostus tulostettava teksti
     * @return kontrolleri, jolta voidaan pyytää lisää tietoa
     */
    public static TulostusController tulosta(String tulostus) {
        TulostusController tulostusCtrl = 
          ModalController.showModeless(TulostusController.class.getResource("TulostusView.fxml"),
                                       "Tulostus", tulostus);
        return tulostusCtrl;
    }

    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return tulostusAlue;
    }
}
