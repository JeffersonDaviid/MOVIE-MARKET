package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbDirectorMaster extends ConexionMaster {

    public boolean guardar(Director d) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "INSERT INTO Directores (nombre, apellido, fechaNacimiento, paisOrigen, premios, peliculasDirigidas, fechaCreacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            ps.setDate(3, new java.sql.Date(d.getFechaNacimiento().getTime()));
            ps.setString(4, d.getPaisOrigen());
            ps.setInt(5, d.getPremios());
            ps.setInt(6, d.getPeliculasDirigidas());
            ps.setDate(7, new java.sql.Date(d.getFechaCreacion().getTime()));
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

    public boolean modificar(Director d) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "UPDATE Directores SET nombre=?, apellido=?, fechaNacimiento=?, paisOrigen=?, premios=?, peliculasDirigidas=?, fechaCreacion=? WHERE directorID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            if (d.getFechaNacimiento() != null) {
                ps.setDate(3, new java.sql.Date(d.getFechaNacimiento().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            ps.setString(4, d.getPaisOrigen());
            ps.setInt(5, d.getPremios());
            ps.setInt(6, d.getPeliculasDirigidas());
            ps.setDate(7, new java.sql.Date(d.getFechaCreacion().getTime()));
            ps.setInt(8, d.getDirectorID());
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

    public boolean eliminar(Director d) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "DELETE FROM Directores WHERE directorID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, d.getDirectorID());
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

    public boolean buscar(Director d) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
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
    
    
    public List<Director> listar() {
        List<Director> directores = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
        String sql = "SELECT * FROM Directores";

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
    
    
    public List<DirectorAuditoria> listarAuditoria() {
    List<DirectorAuditoria> auditoriaList = new ArrayList<>();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = getConexionMaster();
    String sql = "SELECT * FROM auditoriaDirectores";

    try {
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            DirectorAuditoria a = new DirectorAuditoria();
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
    
    public boolean exists(int directorID) {
        // Implementar la lógica para verificar si el directorID existe en la base de datos.
        String query = "SELECT COUNT(*) FROM Directores WHERE DirectorID = ?";
        try (Connection con = getConexionMaster();  
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
