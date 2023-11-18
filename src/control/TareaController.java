/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class TareaController implements Initializable {

    @FXML
    private ComboBox<?> cboBase;
    @FXML
    private ComboBox<?> cboTabla;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void doBaseMostrar(ActionEvent event) {
    }

    @FXML
    private void doTablaMostrar(ActionEvent event) {
    }
    
}
