package control;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContraseña;
    @FXML
    private Button btnEntrar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void doEntrar(ActionEvent event) throws IOException, ClassNotFoundException {
        String Usuario = txtUsuario.getText();
        String Contraseña = txtContraseña.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/",Usuario,Contraseña);
        
            if (conn != null){
                System.out.println("Success");
                try {
                    Stage stage=new Stage();
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/Unravel.fxml"));
                    Parent root=loader.load();
                    Scene scene=new Scene (root);
                    stage.setTitle("Unravel-a-data");
                    stage.setScene(scene);
                    stage.show();
                    Stage myStage=(Stage)this.btnEntrar.getScene().getWindow();
                    myStage.close();
                }
                catch(IOException ex){
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Ha ocurrido el error: "+e);
            alert.setContentText("Verifique sus credenciales");
            alert.showAndWait();
        }
        
    }
    }
    
