package modelo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entities.ConexionReplica;
import modelo.entities.Genero;

public class DbGeneroReplica extends ConexionReplica{

    public boolean buscar(Genero g) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM vistaGeneros WHERE nombre=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, g.getNombre());
            rs = ps.executeQuery();

            if (rs.next()) {
                g.setGeneroID(rs.getInt("generoID"));
                g.setNombre(rs.getString("nombre"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    
    public List<Genero> listar() {
    List<Genero> generos = new ArrayList<>();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = getConexionReplica();
    String sql = "SELECT * FROM vistaGeneros";

    try {
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Genero g = new Genero();
            g.setGeneroID(rs.getInt("generoID"));
            g.setNombre(rs.getString("nombre"));
            generos.add(g);
        }
    } catch (SQLException e) {
        System.err.println(e);
    } finally {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    return generos;
}

    public boolean exists(int generoID) {
        // Implementar la lógica para verificar si el generoID existe en la base de datos.
        String query = "SELECT COUNT(*) FROM vistaGeneros WHERE GeneroID = ?";
        try (Connection con = getConexionReplica();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, generoID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
