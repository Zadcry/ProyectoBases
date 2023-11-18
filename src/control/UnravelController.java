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
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class UnravelController implements Initializable {

    private String User;
    private String Password;
    private Connection conn;
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
            
            try {
                conn.close();
                System.out.println("Disconnected from MySQL.");
            } catch (SQLException ex) {
                Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
            TareaController tareaController = loader.getController();
            tareaController.receiveConection(this.User, this.Password);
            
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
            
            try {
                conn.close();
                System.out.println("Disconnected from MySQL.");
            } catch (SQLException ex) {
                Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
            ConsultaController consultaController = loader.getController();
            consultaController.receiveConection(this.User, this.Password);
            
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

    void receiveConection(String Usuario, String Contraseña) {
        try {
            this.User = Usuario;
            this.Password = Contraseña;            
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/",Usuario,Contraseña);
            if (conn != null){
                System.out.println("Success");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Ha ocurrido el error: "+e);
            alert.setContentText("Verifique sus credenciales");
            alert.showAndWait();
        }
    }
    
}
