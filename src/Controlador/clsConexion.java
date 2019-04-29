
package Controlador;

import java.sql.DriverManager;

public class clsConexion {
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
