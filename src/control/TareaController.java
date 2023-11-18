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
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TareaController implements Initializable {

    private String User;
    private String Password;
    private Connection conn;
    
    @FXML
    private ComboBox<String> cboBase;
    @FXML
    private ComboBox<String> cboTabla;
    @FXML
    private AnchorPane ancTarea;
    @FXML
    private Menu menItemSalir;
    @FXML
    private TableView<?> tblTarea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    private void doBaseMostrar(ActionEvent event) {
        String selectedDatabase = cboBase.getValue();
            if (selectedDatabase != null) {
                populateTables(selectedDatabase);
            }
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
            
            try {
                conn.close();
                System.out.println("Disconnected from MySQL.");
            } catch (SQLException ex) {
                Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            UnravelController unravelController = loader.getController();
            unravelController.receiveConection(this.User, this.Password);
            
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

    void receiveConection(String Usuario, String Contraseña) {
       try {
            this.User = Usuario;
            this.Password = Contraseña;            
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/",Usuario,Contraseña);
            if (conn != null){
                System.out.println("Success");
            }
            populateDatabases();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Ha ocurrido el error: "+e);
            alert.setContentText("Verifique sus credenciales");
            alert.showAndWait();
        }
    }
    
    private void populateDatabases() {
        try {
            
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");

            List<String> databases = new ArrayList<>();
            while (resultSet.next()) {
                databases.add(resultSet.getString(1));
            }

            cboBase.getItems().addAll(databases);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     private void populateTables(String databaseName) {
        cboTabla.getItems().clear(); // Clear previous items

        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);

            ResultSet resultSet = statement.executeQuery("SHOW TABLES");

            List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                tables.add(resultSet.getString(1));
            }

            cboTabla.getItems().addAll(tables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
