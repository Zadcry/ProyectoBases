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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ConsultaController implements Initializable {

    private String User;
    private String Password;
    private Connection conn;
    private ObservableList<ObservableList> data;
 
    
    @FXML
    private ComboBox<String> cboConBase;
    @FXML
    private ComboBox<String> cboConTabla;
    @FXML
    private ComboBox<String> cboAtri;
    @FXML
    private ComboBox<String> cboCond1;
    @FXML
    private ComboBox<String> cboCond2;
    @FXML
    private TextField txtComp1;
    @FXML
    private TextField txtComp2;
    @FXML
    private MenuItem menItemRegresar;
    @FXML
    private TableView tblConsulta;
    @FXML
    private AnchorPane ancConsulta;
    @FXML
    private ComboBox<String> cboConector;
    @FXML
    private Button btnConsultar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        this.llenarCombos();
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
    private void doConTablaMostrar(ActionEvent event) {
        String selectedDatabase = cboConBase.getValue();
        String selectedTable = cboConTabla.getValue();
            if (selectedDatabase != null && selectedTable != null) {
                populateAtributes(selectedDatabase, selectedTable);
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
        cboConTabla.getItems().clear(); 

        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);

            ResultSet resultSet = statement.executeQuery("SHOW TABLES");

            List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                tables.add(resultSet.getString(1));
            }
            cboConTabla.getItems().addAll(tables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      private void populateAtributes(String databaseName,  String tableName) {
        cboAtri.getItems().clear(); 

        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);

            ResultSet resultSet = statement.executeQuery("DESCRIBE " + tableName);

            List<String> atri = new ArrayList<>();
            while (resultSet.next()) {
                int i=0;
                atri.add(resultSet.getString(1));
            }
            cboAtri.getItems().addAll(atri);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void hacerConsulta(String databaseName, String tableName, String AtributeName, String Condicion1,String Complemento1,String Conector, String Condicion2, String Complemento2)
    {
        tblConsulta.setItems(null);
        data = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);
            ResultSet columnSet = statement.executeQuery("DESCRIBE "+tableName);
            List<String> Columnas = new ArrayList<>();
            while (columnSet.next()) {
                Columnas.add(columnSet.getString(1));
            }
            if(Conector!=null)
            {
            resultSet = statement.executeQuery("SELECT * FROM "+tableName+" WHERE "+AtributeName+" "+Condicion1+" "+Complemento1+" "+Conector+" "+AtributeName+Condicion2+Complemento2);            
            } else { 
            System.out.println("SELECT * FROM "+tableName+" WHERE "+AtributeName+"  "+Condicion1+"  "+Complemento1);
            resultSet = statement.executeQuery("SELECT * FROM "+tableName+" WHERE "+AtributeName+" "+Condicion1+" "+Complemento1);
            
            } 
            for(int i=0; i<resultSet.getMetaData().getColumnCount();i++){
                final int j=i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
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
    private void llenarCombos()
    {
        
        this.cboCond1.getItems().add("<");
        this.cboCond1.getItems().add(">");
        this.cboCond1.getItems().add("<=");
        this.cboCond1.getItems().add(">=");
        this.cboCond1.getItems().add("=");
        this.cboCond1.getItems().add("<>");
        this.cboCond1.getItems().add("LIKE");
        this.cboCond1.getItems().add("NOT LIKE");
        this.cboCond1.getItems().add("IS NULL");
        this.cboCond1.getItems().add("NOT NULL");
        this.cboCond2.getItems().add("");
        this.cboCond2.getItems().add("<");
        this.cboCond2.getItems().add(">");
        this.cboCond2.getItems().add("<=");
        this.cboCond2.getItems().add(">=");
        this.cboCond2.getItems().add("=");
        this.cboCond2.getItems().add("<>");
        this.cboCond2.getItems().add("LIKE");
        this.cboCond2.getItems().add("NOT LIKE");
        this.cboCond2.getItems().add("IS NULL");
        this.cboCond2.getItems().add("IS NOT NULL");
        this.cboConector.getItems().add("AND");
        this.cboConector.getItems().add("OR");
    }

    @FXML
    private void doConsulta(ActionEvent event) 
        {
        String selectedDatabase = cboConBase.getValue();
        String selectedTable = cboConTabla.getValue();
        String selectedAtribute = cboAtri.getValue();
        String selectedCond1 = cboCond1.getValue();
        String selectedComp1 = txtComp1.getText();
        String selectedConector = cboConector.getValue();
        String selectedCond2 = cboCond2.getValue();
        String selectedComp2 = txtComp2.getText();
            if (selectedDatabase != null && selectedTable != null) {
                hacerConsulta(selectedDatabase, selectedTable, selectedAtribute, selectedCond1, selectedComp1, selectedConector,selectedCond2, selectedComp2);
            }
    }    
}
