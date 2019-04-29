
package Controlador;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class clsConexion1 {
    java.sql.Connection con = null;
    
    public java.sql.Connection Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/factura", "root", "1234");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return con;

    }

}
