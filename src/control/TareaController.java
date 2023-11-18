package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TareaController implements Initializable {

    @FXML
    private ComboBox<String> cboBase;
    @FXML
    private ComboBox<?> cboTabla;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    
    
    private void populateDatabases() {
        try {
            //No hay conexion de mysql en este punto
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
    }
    
    @FXML
    private void doBaseMostrar(ActionEvent event) {
    }

    @FXML
    private void doTablaMostrar(ActionEvent event) {
    }
    
}
