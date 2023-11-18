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
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConsultaController implements Initializable {

    @FXML
    private ComboBox<?> cboConBase;
    @FXML
    private ComboBox<?> cboConTabla;
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
        // TODO
    }    

    @FXML
    private void doRegresar(ActionEvent event) {
    try
        {
            Stage stage=new Stage();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/Unravel.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene (root);
            
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
    
}
