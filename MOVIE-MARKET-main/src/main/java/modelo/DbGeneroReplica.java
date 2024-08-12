package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbGeneroReplica extends ConexionReplica {

    public boolean buscar(Genero g) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM Generos WHERE nombre=?";

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
}
