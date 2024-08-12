package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbUsuarioMaster extends ConexionMaster {

    public boolean guardar(Usuario u) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "INSERT INTO Usuarios (correo, nombre, apellido, fechaRegistro) VALUES (?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getCorreo());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setDate(4, new java.sql.Date(u.getFechaRegistro().getTime()));
            ps.execute();
            return true;
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

    public boolean modificar(Usuario u) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "UPDATE Usuarios SET correo=?, nombre=?, apellido=?, fechaRegistro=? WHERE usuarioID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getCorreo());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setDate(4, new java.sql.Date(u.getFechaRegistro().getTime()));
            ps.setInt(5, u.getUsuarioID());
            ps.execute();
            return true;
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

    public boolean eliminar(Usuario u) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "DELETE FROM Usuarios WHERE usuarioID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, u.getUsuarioID());
            ps.execute();
            return true;
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

    public boolean buscar(Usuario u) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
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
        Connection con = getConexionMaster();
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
    
    
    // Método para listar las auditorías de usuarios
    public List<UsuarioAuditoria> listarAuditoria() {
        List<UsuarioAuditoria> auditorias = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
        String sql = "SELECT * FROM auditoriaUsuarios";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                UsuarioAuditoria a = new UsuarioAuditoria();
                a.setUserName(rs.getString("userName"));
                a.setFecha(rs.getDate("fecha"));
                a.setTipoOperacion(rs.getString("tipoOperacion"));
                a.setNombreTabla(rs.getString("nombreTabla"));
                a.setAnterior(rs.getString("anterior"));
                a.setNuevo(rs.getString("nuevo"));
                
                auditorias.add(a);
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
        
        return auditorias;
    }
    
    public boolean exists(int usuarioID) {
    // Implementar la lógica para verificar si el usuarioID existe en la base de datos.
    String query = "SELECT COUNT(*) FROM Usuarios WHERE UsuarioID = ?";
    try (Connection con = getConexionMaster();  
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
