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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableView<ObservableList<String>> tblTarea;
    @FXML
    private Button btnBase;
    @FXML
    private Button btnTabla;

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
        String selectedDatabase = cboBase.getValue();
        String selectedTable = cboTabla.getValue();
        if (selectedDatabase != null && selectedTable != null) {
            populateTableView(selectedDatabase, selectedTable);
        }
    }
    
    @FXML
    private void adminBase(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Selecciona");
        alert.setHeaderText("Elije crear o eliminar una Base de Datos:");
        alert.setContentText("Escoge una accion:");

        ButtonType createButton = new ButtonType("Crear");
        ButtonType deleteButton = new ButtonType("Eliminar");
        ButtonType cancelButton = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(createButton, deleteButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == createButton) {
                    System.out.println("Selected: " + response.getText());
                Alert createAlert = new Alert(Alert.AlertType.CONFIRMATION);
                createAlert.setTitle("Crear Base de Datos");
                createAlert.setHeaderText("Ingrese el nombre de la nueva Base de Datos:");

                TextField inputField = new TextField();
                createAlert.getDialogPane().setContent(inputField);

                ButtonType createBaseButton = new ButtonType("Crear");
                ButtonType cancelBaseButton = new ButtonType("Cancelar");
                createAlert.getButtonTypes().setAll(createButton, cancelButton);

                Optional<ButtonType> result = createAlert.showAndWait();
                if (result.isPresent() && result.get() == createButton) {
                    String dbName = inputField.getText().trim();
                    if (!dbName.isEmpty()) {
                        try {
                            Statement statement = conn.createStatement();
                            String sql = "CREATE DATABASE " + dbName;
                            statement.executeUpdate(sql);
                            System.out.println("Database created successfully: " + dbName);
                            populateDatabases();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Handle empty input or no input provided
                        System.out.println("Please enter a valid database name.");
                    }
                }
                
            } else if (response == deleteButton) {
                Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                deleteAlert.setTitle("Eliminar Base de Datos");
                deleteAlert.setHeaderText("Seleccione la Base de Datos a eliminar:");

                ComboBox<String> cboBaseEliminar = new ComboBox<>();
                List<String> databases = new ArrayList<>();
                try{
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
                    while (resultSet.next()) {
                        databases.add(resultSet.getString(1));
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                }

                cboBaseEliminar.getItems().addAll(databases);
                deleteAlert.getDialogPane().setContent(cboBaseEliminar);

                ButtonType deleteBaseButton = new ButtonType("Eliminar");
                ButtonType cancelBaseButton = new ButtonType("Cancelar");
                deleteAlert.getButtonTypes().setAll(deleteButton, cancelButton);
                
                Optional<ButtonType> result = deleteAlert.showAndWait();
                if (result.isPresent() && result.get() == deleteButton) {
                    String dbName = cboBaseEliminar.getValue();
                    if (dbName != null && !dbName.isEmpty()) {
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmacion");
                        confirmation.setHeaderText("Confirmar Eliminacion");
                        confirmation.setContentText("Estas seguro de eliminar la base " + dbName + "?");

                        confirmation.showAndWait().ifPresent(deleteResult -> {
                            if (deleteResult == ButtonType.OK) {
                                try {
                                    Statement statement = conn.createStatement();
                                    String sql = "DROP DATABASE " + dbName;
                                    statement.executeUpdate(sql);
                                    System.out.println("Database deleted successfully: " + dbName);
                                    populateDatabases();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Delete canceled");
                            }
                        });
                    } else {
                        System.out.println("Please select a valid database to delete.");
                    }
                }
            }    
        });   
    }

    @FXML
    private void adminTabla(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Selecciona");
        alert.setHeaderText("Elige crear o eliminar una tabla:");
        alert.setContentText("Escoge una accion:");

        ButtonType createButton = new ButtonType("Crear");
        ButtonType deleteButton = new ButtonType("Eliminar");
        ButtonType cancelButton = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(createButton, deleteButton, cancelButton);
        String Base = cboBase.getValue();
        alert.showAndWait().ifPresent(response -> {
            if (response == createButton && Base != null && !Base.isEmpty()) {
                    System.out.println("Selected: " + response.getText());
                try {
                    Stage stage=new Stage();
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/Administrar.fxml"));
                    Parent root=loader.load();
                    Scene scene=new Scene (root);

                    try {
                        conn.close();
                        System.out.println("Disconnected from MySQL.");
                    } catch (SQLException ex) {
                        Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                    AdministrarController administrarController = loader.getController();
                    administrarController.receiveConection(Base,this.User, this.Password);

                    stage.setTitle("Unravel-a-data");
                    stage.setScene(scene);
                    stage.show();
                    Stage myStage=(Stage)this.ancTarea.getScene().getWindow();
                    myStage.close();
                }
                catch(IOException ex) {
                    Logger.getLogger(UnravelController.class.getName()).log(Level.SEVERE,null,ex);
                }
                
            } else if (response == deleteButton) {
                String tableName = cboTabla.getValue();
                if(tableName != null && !tableName.isEmpty()){
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Confirmacion");
                    confirmation.setHeaderText("Confirmar Eliminacion");
                    confirmation.setContentText("Estas seguro de eliminar la tabla "+cboTabla.getValue()+"?");

                    confirmation.showAndWait().ifPresent(result -> {
                        if (result == ButtonType.OK) {
                            System.out.println("Delete confirmed");
                            String deleteQuery = "DROP TABLE " + tableName;

                            try (Statement statement = conn.createStatement()) {
                                statement.executeUpdate(deleteQuery);
                                System.out.println("Table '" + tableName + "' deleted successfully!");
                            } catch (SQLException e) {
                                System.err.println("Error deleting table: " + e.getMessage());                                
                            }
                            populateTables(Base);
                        } else {
                            System.out.println("Delete canceled");
                        }
                    });
                } else{
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Error: No se ha escogido una tabla");
                    errorAlert.setContentText("Por favor selecciona una tabla antes de eliminarla");
                    errorAlert.show();
                }             
            } else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Error: No se ha escogido una base de datos");
                errorAlert.setContentText("Por favor selecciona una base de datos antes de crear una tabla");
                errorAlert.show();
            }
        });        
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
            Stage myStage=(Stage)this.ancTarea.getScene().getWindow();
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
            cboBase.getItems().clear();
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
     
    private void populateTableView(String databaseName, String tableName) {
        tblTarea.getColumns().clear(); // Clear previous columns

        try {
            Statement statement = conn.createStatement();
            statement.execute("USE " + databaseName);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                final int index = columnIndex;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(metaData.getColumnName(columnIndex));
                column.setCellValueFactory(data -> {
                    ObservableList<String> row = data.getValue();
                    return new SimpleStringProperty(row.get(index - 1));
                });
                tblTarea.getColumns().add(column);
            }

            // Populate data into the TableView
            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
            tblTarea.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
}
