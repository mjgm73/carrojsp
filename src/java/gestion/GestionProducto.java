/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import administrativo.Producto;
import administrativo.Usuario;
import conexion.AbstractDB;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author luismb
 */
public class GestionProducto extends AbstractDB {

    public GestionProducto() {
        super();
    }

    public boolean crearProducto(Producto prod) {
        boolean flag = false;
        PreparedStatement pst = null;
        try {
            String sql = "call newProducto(?,?,?,?,?)";
            pst = this.conn.prepareStatement(sql);
            System.out.println(prod.getNombre());
            pst.setString(1, prod.getNombre());
            pst.setFloat(2, prod.getPrecioCompra());
            pst.setFloat(3, prod.getPrecioVenta());
            pst.setInt(4, prod.getExistencias());
            pst.setString(5, prod.getNombreFoto());

            if (pst.executeUpdate() == 1) {
                flag = true;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            cierraConexion();
        }
        return flag;
    }

    public ArrayList<Producto> getTodos() {
        ArrayList<Producto> productos = new ArrayList();

        try {
            Statement stmt = this.conn.createStatement();

            ResultSet res = stmt.executeQuery("call getAllProducts()");
            while (res.next()) {
                Producto prod = new Producto();
                prod.setId(res.getString("idProducto"));
                prod.setNombre(res.getString("nombre"));
                prod.setPrecioCompra(res.getFloat("precioCompra"));
                prod.setPrecioVenta(res.getFloat("precioVenta"));
                prod.setExistencias(res.getInt("existencias"));
                prod.setNombreFoto("foto");

                productos.add(prod);
                

            }
            res.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return productos;

    }

    

    public void cierraConexion() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //==========================================================================

}
