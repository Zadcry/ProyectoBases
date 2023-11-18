package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TareaController implements Initializable {

    @FXML
    private ComboBox<String> cboBase;
    @FXML
    private ComboBox<?> cboTabla;
    @FXML
    private AnchorPane ancTarea;
    @FXML
    private Menu menItemSalir;
    @FXML
    private TableView<?> tblTarea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
   /* private void populateDatabases() {
        try {
            
            //No hay conexion de mysql en este punto
            Connection connection = DriverManager.
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");

            List<String> databases = new ArrayList<>();
            while (resultSet.next()) {
                databases.add(resultSet.getString(1));
            }

            cboBase.getItems().addAll(databases);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */
    
    @FXML
    private void doBaseMostrar(ActionEvent event) {
    }

    @FXML
    private void doTablaMostrar(ActionEvent event) {
    }
    @FXML
    private void doSalir(ActionEvent event) {
    try
        {
            Stage stage=new Stage();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/Unravel.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene (root);
            
            stage.setTitle("Unravel-a-data");
            stage.setScene(scene);
            stage.show();
            var myStage=(Stage)this.ancTarea.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)
        {
            Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
}
