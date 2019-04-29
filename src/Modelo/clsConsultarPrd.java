package Modelo;

import Controlador.clsConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class clsConsultarPrd extends clsConexion {

    public boolean registrar(clsProducto pro) {
        PreparedStatement ps = null;
        Connection con = Conexion();
        String sql = "INSERT INTO producto(codigo,nombre,precio,cantidad) VALUES(?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getIdCodProd());
            ps.setString(2, pro.getNombProd());
            ps.setInt(3, pro.getPrecio());
            ps.setInt(4, pro.getCantidad());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e1) {
                System.out.println(e1);
            }
        }
    }

    public boolean modificar(clsProducto pro) {
        PreparedStatement ps = null;
        Connection con = Conexion();
        String sql = "UPDATE producto SET codigo=?,nombre=?,precio=?,cantidad=? WHERE codigo=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getIdCodProd());
            ps.setString(2, pro.getNombProd());
            ps.setInt(3, pro.getPrecio());
            ps.setInt(4, pro.getCantidad());
            ps.setString(5, pro.getIdCodProd());
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
public boolean eliminar(clsProducto pro) {
        PreparedStatement ps = null;
        Connection con = Conexion();
        String sql = "DELETE FROM producto WHERE codigo=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getIdCodProd());
            ps.execute();
           
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }        
    }

    public boolean buscar(clsProducto pro) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = Conexion();
        String sql = "SELECT * FROM producto WHERE codigo=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getIdCodProd());
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setIdCodProd(rs.getString("codigo"));
                pro.setNombProd(rs.getString("nombre"));
                pro.setPrecio(Integer.parseInt(rs.getString("precio")));
                pro.setCantidad(Integer.parseInt(rs.getString("cantidad")));
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println(e);
            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

}
