package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionMySQL {
    
    private Connection connection;
    private String usuario = "root";
    private String password = "Zoroark";
    private String servidor = "localhost";
    private String puerto = "3306";
    private String nombreBD = "world";
    
    private String url = "jdbc:mysql://"+servidor+":"+puerto+"/"+nombreBD+"?serverTimezone=UTC";
    
    private String driver = "com.mysql.cj.jdbc.Driver";

    public ConexionMySQL() {
        
        try {
            
            Class.forName(driver);
            connection = DriverManager.getConnection(url, usuario, password);
            
            if(connection != null){
                
                System.out.println("Conexión exitosa");
            }
            
        } catch (Exception e) {
            
            System.err.println("Ocurrió un error en la conexión");
            System.err.println("Mensaje del error:"+ e.getMessage());
            System.err.println("Detalle del error: ");
            
            e.printStackTrace();
        }
    }
    
    
    
}
