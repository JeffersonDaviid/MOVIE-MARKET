package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUsuarioReplica extends ConexionReplica {

    public boolean buscar(Usuario u) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM Usuarios WHERE correo=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getCorreo());
            rs = ps.executeQuery();

            if (rs.next()) {
                u.setUsuarioID(rs.getInt("usuarioID"));
                u.setCorreo(rs.getString("correo"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setFechaRegistro(rs.getDate("fechaRegistro"));
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
