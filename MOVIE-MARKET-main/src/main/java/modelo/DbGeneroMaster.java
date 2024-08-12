package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbGeneroMaster extends ConexionMaster {

    public boolean guardar(Genero g) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "INSERT INTO Generos (nombre) VALUES (?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, g.getNombre());
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

    public boolean modificar(Genero g) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "UPDATE Generos SET nombre=? WHERE generoID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, g.getNombre());
            ps.setInt(2, g.getGeneroID());
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

    public boolean eliminar(Genero g) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "DELETE FROM Generos WHERE generoID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, g.getGeneroID());
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

    public boolean buscar(Genero g) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
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
    
    
    public List<Genero> listar() {
    List<Genero> generos = new ArrayList<>();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = getConexionMaster();
    String sql = "SELECT * FROM Generos";

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

    
    public List<GeneroAuditoria> listarAuditoria() {
    List<GeneroAuditoria> auditoriaList = new ArrayList<>();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = getConexionMaster();
    String sql = "SELECT * FROM auditoriaGeneros";

    try {
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            GeneroAuditoria a = new GeneroAuditoria();
            a.setUserName(rs.getString("userName"));
            a.setFecha(rs.getDate("fecha"));
            a.setTipoOperacion(rs.getString("tipoOperacion"));
            a.setNombreTabla(rs.getString("nombreTabla"));
            a.setAnterior(rs.getString("anterior"));
            a.setNuevo(rs.getString("nuevo"));

            auditoriaList.add(a);
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

    return auditoriaList;
}
    
    public boolean exists(int generoID) {
        // Implementar la lógica para verificar si el generoID existe en la base de datos.
        String query = "SELECT COUNT(*) FROM Generos WHERE GeneroID = ?";
        try (Connection con = getConexionMaster();
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
