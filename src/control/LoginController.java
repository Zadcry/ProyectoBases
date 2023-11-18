/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import Start.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class LoginController implements Initializable {
    public Connection conn;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContraseña;
    @FXML
    private Button btnEntrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void doEntrar(ActionEvent event) {
        // BaseDeDatos = 
        String Usuario = txtUsuario.getText();
        String Contraseña = txtContraseña.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world"/*+BaseDeDatos*/,Usuario,Contraseña);
        
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
                catch(IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Se ha producido un error: "+e);
            alert.setContentText("Por favor verifique sus credenciales");
            alert.showAndWait();
        }
        }
    }
    
