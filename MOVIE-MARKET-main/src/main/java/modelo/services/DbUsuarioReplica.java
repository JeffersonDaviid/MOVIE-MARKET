package modelo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entities.ConexionReplica;
import modelo.entities.Usuario;

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
    
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM Usuarios";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setUsuarioID(rs.getInt("usuarioID"));
                u.setCorreo(rs.getString("correo"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setFechaRegistro(rs.getDate("fechaRegistro"));
                usuarios.add(u);
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

        return usuarios;
    }
    
    
    public boolean exists(int usuarioID) {
    // Implementar la lógica para verificar si el usuarioID existe en la base de datos.
    String query = "SELECT COUNT(*) FROM Usuarios WHERE UsuarioID = ?";
    try (Connection con = getConexionReplica();  
         PreparedStatement pst = con.prepareStatement(query)) {
        pst.setInt(1, usuarioID);
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
