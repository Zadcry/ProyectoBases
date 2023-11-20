package control;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RelacionController implements Initializable {

    private String User;
    private String Password;
    private Connection conn;
    private ObservableList<ObservableList> data;
 
    
    @FXML
    private ComboBox<String> cboConBase;
    @FXML
    private MenuItem menItemRegresar;
    @FXML
    private TableView tblConsulta;
    @FXML
    private AnchorPane ancConsulta;
    private ComboBox<String> cboConector;
    @FXML
    private Button btnConsultar;
    @FXML
    private ComboBox<String> cboConTabla1;
    @FXML
    private ComboBox<String> cboConTabla2;
    @FXML
    private ComboBox<String> cboAtri01;
    @FXML
    private Label txtAtri11;
    @FXML
    private ComboBox<String> cboAtri02;
    @FXML
    private RadioButton rbt1;
    @FXML
    private RadioButton rbt2;
    @FXML
    private Label txtAtri12;
    @FXML
    private ComboBox<String> cboAtri11;
    @FXML
    private ComboBox<String> cboAtri12;

    @Override
    public void initialize(URL url, ResourceBundle rb) {   
    }    
    private void conexion(String username, String pass) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/",username,pass);
    }
    
    @FXML
    private void doRegresar(ActionEvent event) {
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
            Stage myStage=(Stage)this.ancConsulta.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)
        {
            Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    @FXML
    private void doConBaseMostrar(ActionEvent event) {
        String selectedDatabase = cboConBase.getValue();
            if (selectedDatabase != null) {
                populateTables(selectedDatabase);
            }
    }
    @FXML
    private void doConTabla2Mostrar(ActionEvent event) {
        String selectedDatabase = cboConBase.getValue();
        String selectedTable = cboConTabla1.getValue();       
        String selectedTable2 = cboConTabla2.getValue();
            if (selectedDatabase != null && selectedTable != null && selectedTable2!= null) {
                populateAtributes1(selectedDatabase, selectedTable);
                populateAtributes2(selectedDatabase, selectedTable2);
                populateAtributesx(selectedDatabase, selectedTable, selectedTable2);
            }
    }

    void receiveConection(String Usuario, String Contraseña) {
        try {
            this.User = Usuario;
            this.Password = Contraseña;            
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/",Usuario,Contraseña);
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

            cboConBase.getItems().addAll(databases);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     private void populateTables(String databaseName) {
        cboConTabla1.getItems().clear(); 

        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);

            ResultSet resultSet = statement.executeQuery("SHOW TABLES");

            List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                tables.add(resultSet.getString(1));
            }
            cboConTabla1.getItems().addAll(tables);
            cboConTabla2.getItems().addAll(tables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      private void populateAtributes1(String databaseName,  String tableName) {
        cboAtri01.getItems().clear(); 

        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);

            ResultSet resultSet = statement.executeQuery("DESCRIBE " + tableName);

            List<String> atri = new ArrayList<>();
            while (resultSet.next()) {
                int i=0;
                atri.add(resultSet.getString(1));
            }
            System.out.println(atri);
            cboAtri01.getItems().addAll(atri);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      private void populateAtributes2(String databaseName,  String tableName2) {
        cboAtri02.getItems().clear(); 

        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);

            ResultSet resultSet = statement.executeQuery("DESCRIBE " + tableName2);

            List<String> atri = new ArrayList<>();
            while (resultSet.next()) {
                int i=0;
                atri.add(resultSet.getString(1));
            }
            System.out.println(atri);
            cboAtri02.getItems().addAll(atri);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
       private void populateAtributesx(String databaseName,  String tableName1, String tableName2) {
        cboAtri11.getItems().clear(); 
        cboAtri12.getItems().clear(); 

        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);

            ResultSet resultSet = statement.executeQuery("DESCRIBE " + tableName1);

            List<String> atri = new ArrayList<>();
            while (resultSet.next()) {
                int i=0;
                atri.add(resultSet.getString(1));
            }
            ResultSet resultSet2 = statement.executeQuery("DESCRIBE " + tableName2);
            while (resultSet2.next()) {
                int i=0;
                atri.add(resultSet2.getString(1));
            }
            cboAtri11.getItems().addAll(atri);
            cboAtri12.getItems().addAll(atri);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        private void hacerConsulta(String databaseName, String tableName1, String tableName2, String AtributeName1, String AtributeName2,String AtributeName3, String AtributeName4)
    {
        data = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);
            ResultSet columnSet = statement.executeQuery("DESCRIBE "+tableName1);
            List<String> Columnas = new ArrayList<>();
            while (columnSet.next()) {
                Columnas.add(columnSet.getString(1));
            }
            ResultSet column2Set = statement.executeQuery("DESCRIBE " + tableName2);
            while (column2Set.next()) {
                int i=0;
                Columnas.add(column2Set.getString(1));
            }
            if(rbt1.isSelected())
            {
            resultSet = statement.executeQuery("SELECT * FROM "+tableName1+", "+tableName2+" WHERE "+AtributeName1+" = "+AtributeName2);               
            } else {  
            resultSet = statement.executeQuery("SELECT * FROM "+tableName1+","+tableName2+" WHERE "+tableName1+"."+AtributeName1+"="+tableName2+"."+AtributeName2+" AND "+AtributeName3+" = "+AtributeName4);            
            } 
            for(int i=0; i<resultSet.getMetaData().getColumnCount();i++){
                final int j=i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                
                tblConsulta.getColumns().addAll(col);
            }
            while(resultSet.next())
            {
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1; i<=resultSet.getMetaData().getColumnCount();i++)
                {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
            tblConsulta.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void doConsulta(ActionEvent event) 
        {
        String selectedDatabase = cboConBase.getValue();
        String selectedTable1 = cboConTabla1.getValue();
        String selectedTable2 = cboConTabla2.getValue();        
        String selectedAtribute1 = cboAtri01.getValue();        
        String selectedAtribute2 = cboAtri02.getValue();        
        String selectedAtribute3 = cboAtri11.getValue();        
        String selectedAtribute4 = cboAtri12.getValue();
            if (selectedDatabase != null && selectedTable1 != null && selectedTable2 != null) {
                hacerConsulta(selectedDatabase, selectedTable1, selectedTable2, selectedAtribute1,selectedAtribute2,selectedAtribute3,selectedAtribute4);
            }
    }    

    @FXML
    private void doConTabla1Mostrar(ActionEvent event) {
    }

    @FXML
    private void do1(ActionEvent event) {
        if(rbt1.isSelected()){
        cboAtri11.setDisable(true); 
        cboAtri12.setDisable(true);               
        rbt2.setSelected(false);
        } else {
        rbt2.setSelected(true);
        cboAtri11.setDisable(false);    
        cboAtri12.setDisable(false);        
        }
    }
    @FXML
    private void do2(ActionEvent event) {
        if(rbt2.isSelected()){
        cboAtri11.setDisable(false);           
        cboAtri12.setDisable(false);
        rbt1.setSelected(false);
        } else {
        rbt1.setSelected(true);
        cboAtri11.setDisable(true);
        cboAtri12.setDisable(true);             
        }
    }
}
