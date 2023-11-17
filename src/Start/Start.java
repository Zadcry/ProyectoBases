package Start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application{
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage ventana) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/login.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        
        ventana.setTitle("Unravel-a-Data");
        ventana.setResizable(false);
                
        ventana.show();
    }
    
}
