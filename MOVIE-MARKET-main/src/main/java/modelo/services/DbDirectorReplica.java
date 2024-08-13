package modelo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entities.ConexionReplica;
import modelo.entities.Director;

public class DbDirectorReplica extends ConexionReplica {


    public boolean buscar(Director d) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM vistaDirectores WHERE nombre=? AND apellido=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            rs = ps.executeQuery();

            if (rs.next()) {
                d.setDirectorID(rs.getInt("directorID"));
                d.setNombre(rs.getString("nombre"));
                d.setApellido(rs.getString("apellido"));
                d.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                d.setPaisOrigen(rs.getString("paisOrigen"));
                d.setPremios(rs.getInt("premios"));
                d.setPeliculasDirigidas(rs.getInt("peliculasDirigidas"));
                d.setFechaCreacion(rs.getDate("fechaCreacion"));
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
    

    
    public List<Director> listar() {
        List<Director> directores = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM vistaDirectores";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Director d = new Director();
                d.setDirectorID(rs.getInt("directorID"));
                d.setNombre(rs.getString("nombre"));
                d.setApellido(rs.getString("apellido"));
                d.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                d.setPaisOrigen(rs.getString("paisOrigen"));
                d.setPremios(rs.getInt("premios"));
                d.setPeliculasDirigidas(rs.getInt("peliculasDirigidas"));
                d.setFechaCreacion(rs.getDate("fechaCreacion"));
                
                directores.add(d);
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
        
        return directores;
    }
    
    public boolean exists(int directorID) {
        // Implementar la lógica para verificar si el directorID existe en la base de datos.
        String query = "SELECT COUNT(*) FROM vistaDirectores WHERE DirectorID = ?";
        try (Connection con = getConexionReplica();  
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, directorID);
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
