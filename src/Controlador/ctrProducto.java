package Controlador;

import Modelo.clsConsultarPrd;
import Modelo.clsProducto;

import Vista.frmProduto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ctrProducto implements ActionListener {

    clsConexion objCon = new clsConexion();
    DefaultTableModel modelo = new DefaultTableModel();
    private clsProducto prod;
    clsConsultarPrd cprod;
    private frmProduto frm;

    public ctrProducto(clsProducto prod, clsConsultarPrd cprod, frmProduto frm) {
        this.cprod = cprod;
        this.prod = prod;
        this.frm = frm;
        this.frm.btnEliminar.addActionListener(this);
        this.frm.btnGuardar.addActionListener(this);
        this.frm.btnLimpiar.addActionListener(this);
        this.frm.btnModificar.addActionListener(this);
        this.frm.btnBuscar.addActionListener(this);
        this.frm.btnListar.addActionListener(this);

    }

    public void iniciar()
    {
        frm.setTitle("Nomina");
        frm.setLocationRelativeTo(null);
        frm.txtId.setVisible(false);
        primero();
    }

    public void primero() {
        try {
            frm.jtProductos.setModel(modelo);
            PreparedStatement ps = null;
            ResultSet rs = null;
            String sql = "SELECT codigo, nombre, precio, cantidad FROM producto";
            ps = objCon.Conexion().prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Código");
            modelo.addColumn("Producto");
            modelo.addColumn("Precio");
            modelo.addColumn("Cantidad");

            int[] anchos = {50, 200, 50, 50};
            for (int i = 0; i < frm.jtProductos.getColumnCount(); i++) {
                frm.jtProductos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modelo.addRow(filas);
            }

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frm.btnGuardar) {
            prod.setIdCodProd(frm.txtCodigo.getText());
            prod.setNombProd(frm.txtNombProd.getText());
            prod.setPrecio(Integer.parseInt(frm.txtPrecio.getText()));
            prod.setCantidad(Integer.parseInt(frm.txtCantidad.getText()));

            if (cprod.registrar(prod)) {
                JOptionPane.showMessageDialog(null, "Registro Gudado");
                Object[] fila = new Object[4];
                fila[0] = frm.txtCodigo.getText();
                fila[1] = frm.txtNombProd.getText();
                fila[2] = frm.txtPrecio.getText();
                fila[3] = frm.txtCantidad.getText();
                modelo.addRow(fila);

                limpiar();

            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }

        }
        if (e.getSource() == frm.btnEliminar) {
            prod.setIdCodProd(frm.txtCodigo.getText());

            if (cprod.eliminar(prod)) {
                JOptionPane.showMessageDialog(null, "registro eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar");
                limpiar();
            }
        }

        if (e.getSource() == frm.btnModificar) {
            prod.setIdCodProd(frm.txtCodigo.getText());
            prod.setNombProd(frm.txtNombProd.getText());
            prod.setPrecio(Integer.parseInt(frm.txtPrecio.getText()));
            prod.setCantidad(Integer.parseInt(frm.txtCantidad.getText()));

            if (cprod.modificar(prod)) {
                JOptionPane.showMessageDialog(null, "registro modificoado");
                Object[] fila = new Object[4];
                fila[0] = frm.txtCodigo.getText();
                fila[1] = frm.txtNombProd.getText();
                fila[2] = frm.txtPrecio.getText();
                fila[3] = frm.txtCantidad.getText();
                limpiar();

            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        }

        if (e.getSource() == frm.btnBuscar) {
            prod.setIdCodProd(frm.txtCodigo.getText());
            String campo = frm.txtCodigo.getText();
            String where = "";
            if (cprod.buscar(prod)) {

                frm.txtCodigo.setText(prod.getIdCodProd());
                frm.txtNombProd.setText(prod.getNombProd());
                frm.txtPrecio.setText(String.valueOf(prod.getPrecio()));
                frm.txtCantidad.setText(String.valueOf(prod.getCantidad()));
            }

            if (!"".equals(campo)) {
                where = "WHERE codigo = '" + campo + "'";
            }

            try {
                DefaultTableModel modelo = new DefaultTableModel();
                frm.jtProductos.setModel(modelo);

                PreparedStatement ps = null;
                ResultSet rs = null;

                String sql = "SELECT codigo, nombre, precio, cantidad FROM producto " + where;
                System.out.println(sql);
                ps = objCon.Conexion().prepareStatement(sql);

                rs = ps.executeQuery();

                ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
                int cantidadColumnas = rsMd.getColumnCount();

                modelo.addColumn("Código");
                modelo.addColumn("Producto");
                modelo.addColumn("Precio");
                modelo.addColumn("Cantidad");

                int[] anchos = {50, 200, 50, 50};
                for (int i = 0; i < frm.jtProductos.getColumnCount(); i++) {
                    frm.jtProductos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }

                while (rs.next()) {
                    Object[] filas = new Object[cantidadColumnas];
                    for (int i = 0; i < cantidadColumnas; i++) {
                        filas[i] = rs.getObject(i + 1);
                    }
                    modelo.addRow(filas);
                }

            } catch (Exception ex) {
                System.err.println(ex.toString());
            }

        }

        if (e.getSource() == frm.btnListar) {

            try {
                DefaultTableModel modelo = new DefaultTableModel();
                frm.jtProductos.setModel(modelo);

                PreparedStatement ps = null;
                ResultSet rs = null;

                String sql = "SELECT codigo, nombre, precio, cantidad FROM producto ";
                System.out.println(sql);
                ps = objCon.Conexion().prepareStatement(sql);

                rs = ps.executeQuery();

                ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
                int cantidadColumnas = rsMd.getColumnCount();

                modelo.addColumn("Código");
                modelo.addColumn("Producto");
                modelo.addColumn("Precio");
                modelo.addColumn("Cantidad");

                int[] anchos = {50, 200, 50, 50};
                for (int i = 0; i < frm.jtProductos.getColumnCount(); i++) {
                    frm.jtProductos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }

                while (rs.next()) {
                    Object[] filas = new Object[cantidadColumnas];
                    for (int i = 0; i < cantidadColumnas; i++) {
                        filas[i] = rs.getObject(i + 1);
                    }
                    modelo.addRow(filas);
                }

            } catch (Exception ex) {
                System.err.println(ex.toString());
            }

        }
    }

    public void limpiar() {
        frm.txtPrecio.setText(null);
        frm.txtCodigo.setText(null);
        frm.txtId.setText(null);
        frm.txtNombProd.setText(null);
        frm.txtCantidad.setText(null);

    }
}
