package control;

import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.SpinnerValueFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdministrarController implements Initializable {

    private String User;
    private String Password;
    private String Database;
    private Connection conn;
    
    @FXML
    private Menu menItemSalir;
    @FXML
    private TextField tfBase;
    @FXML
    private TextField tfTabla;

    @FXML
    private TextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9;
    @FXML
    private ComboBox<String> cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
    
    private ComboBox[] rawTypeComboBoxes;
    private ComboBox<String>[] typeComboBoxes;
    private TextField[] nameTextFields;
    
    @FXML
    private Button btnAceptar;
    @FXML
    private AnchorPane ancPane;
    @FXML
    private ComboBox<String> cboColumnas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int maxColumns = 9;
        String[] columnNumber = {"1","2","3","4","5","6","7","8","9"};
        cboColumnas.setItems(FXCollections.observableArrayList(columnNumber));
        cboColumnas.setValue(columnNumber[0]);
        
        ComboBox[] rawTypeComboBoxes = {cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9};
        ComboBox<String>[] typeComboBoxes = rawTypeComboBoxes;
        TextField[] nameTextFields = {tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9};
        
        this.rawTypeComboBoxes = rawTypeComboBoxes;
        this.typeComboBoxes = typeComboBoxes;
        this.nameTextFields = nameTextFields;
        cantidadCampos();
        
        String[] columnTypes = {"VARCHAR(50)", "INT", "FLOAT", "DATE", "TIME"};
        
        for (int i = 0; i < maxColumns; i++) {
            typeComboBoxes[i].setItems(FXCollections.observableArrayList(columnTypes));
        }
    }
    
    private void cantidadCampos(){
        int newValue = (Integer.parseInt(cboColumnas.getValue()));
        for (int i = 0; i < typeComboBoxes.length; i++) {
            typeComboBoxes[i].setVisible(i < newValue);
            typeComboBoxes[i].setManaged(i < newValue);
            nameTextFields[i].setVisible(i < newValue);
            nameTextFields[i].setManaged(i < newValue);
        }
    }

    @FXML
    private void cambiarColumnas(ActionEvent event) {
        cantidadCampos();
    }
    
    @FXML
    private void doSalir(ActionEvent event) {
        volver();
    }
    
    private void volver(){
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
            Stage myStage=(Stage)this.ancPane.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)
        {
            Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void doAceptar(ActionEvent event) {
        try {
            Statement state = conn.createStatement();
            state.execute("USE " + this.Database);
            int maxColumns = Integer.parseInt(cboColumnas.getValue()); 
            
            String tableName = tfTabla.getText(); 

            StringBuilder query = new StringBuilder("CREATE TABLE " + tableName + " (");

            for (int i = 0; i < maxColumns; i++) {
                String columnName = nameTextFields[i].getText();
                String columnType = typeComboBoxes[i].getValue(); 

                if (!columnName.isEmpty() && columnType != null) {
                    query.append(columnName).append(" ").append(columnType);
                    if (i < maxColumns - 1) {
                        query.append(", ");
                    }
                }
            }
            query.append(")");
            
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate(query.toString());
                System.out.println("Table created successfully!");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmacion");
                alert.setHeaderText("Tabla Creada con Exito");
                alert.setContentText("");
                alert.showAndWait();
                volver();
            } catch (SQLException e) {
                System.err.println("Error executing query: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    void receiveConection(String Database, String User, String Password) {
        try {
            this.User = User;
            this.Password = Password;      
            this.Database = Database;
            this.tfBase.setPromptText(this.Database);
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/",User,Password);
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
