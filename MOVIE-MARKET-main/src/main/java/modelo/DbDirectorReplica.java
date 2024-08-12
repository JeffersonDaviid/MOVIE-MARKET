package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DbDirectorReplica extends ConexionReplica {

    public boolean buscar(Director d) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM Directores WHERE nombre=? AND apellido=?";

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
}
