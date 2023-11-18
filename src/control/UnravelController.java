package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import control.LoginController;
import java.sql.Connection;
import java.sql.DriverManager;

public class UnravelController implements Initializable {

    @FXML
    private MenuItem menItemIr;
    @FXML
    private MenuItem menItemVolver;
    @FXML
    private MenuItem menItemCerrar;
    @FXML
    private VBox VBox;
    @FXML
    private MenuItem menItemVisualizar;
    @FXML
    private MenuItem menItemHacer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void doIr(ActionEvent event) {
    }

    @FXML
    private void doVolver(ActionEvent event) {
    try
        {
            Stage stage=new Stage();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/login.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene (root);
            
            stage.setTitle("Unravel-a-data");
            stage.setScene(scene);
            stage.show();
            Stage myStage=(Stage)this.VBox.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)
        {
            Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    

    @FXML
    private void doCerrar(ActionEvent event) {
        Stage stage=(Stage)this.VBox.getScene().getWindow();
        stage.close();
        System.exit(0);

    }

    @FXML
    private void doVisualizar(ActionEvent event) {
    try
        {
            Stage stage=new Stage();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/Tarea.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene (root);
            
            stage.setTitle("Unravel-a-data");
            stage.setScene(scene);
            stage.show();
            Stage myStage=(Stage)this.VBox.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)
        {
            Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void doHacer(ActionEvent event) {
        try {
            
            Stage stage=new Stage();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/Consulta.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene (root);
            
            stage.setTitle("Unravel-a-data");
            stage.setScene(scene);
            stage.show();
            Stage myStage=(Stage)this.VBox.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex) {
            Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
}
