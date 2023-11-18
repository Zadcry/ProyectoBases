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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConsultaController implements Initializable {

    private String User;
    private String Password;
    private Connection conn;
    
    @FXML
    private ComboBox<String> cboConBase;
    @FXML
    private ComboBox<String> cboConTabla;
    @FXML
    private ComboBox<?> cboAtri;
    @FXML
    private ComboBox<?> cboCond1;
    @FXML
    private ComboBox<?> cboCond2;
    @FXML
    private TextField txtComp1;
    @FXML
    private TextField txtComp2;
    @FXML
    private MenuItem menItemRegresar;
    @FXML
    private TableView<?> tblConsulta;
    @FXML
    private AnchorPane ancConsulta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*LoginController loginController = new LoginController(); 
        String usuario = loginController.getUser();
        String pass = loginController.getPass();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/",usuario,pass);
        } catch (Exception e) {
            System.out.println("No se pudo rey, pass es:"+pass+" user es: "+usuario);;
        }*/
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
    }

    @FXML
    private void doAtributoMostrar(ActionEvent event) {
    }

    @FXML
    private void doCond1Mostrar(ActionEvent event) {
    }

    @FXML
    private void doCond2Mostrar(ActionEvent event) {
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

            cboConBase.getItems().addAll(databases);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     private void populateTables(String databaseName) {
        cboConTabla.getItems().clear(); // Clear previous items

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
    
}
