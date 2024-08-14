package modelo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entities.ConexionMaster;
import modelo.entities.Pelicula;
import modelo.entities.PeliculaAuditoria;

public class DbPeliculaMaster extends ConexionMaster {

    public boolean guardar(Pelicula p) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "INSERT INTO Peliculas (titulo, directorID, generoID, anoLanzamiento, sinopsis) VALUES (?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getTitulo());
            ps.setInt(2, p.getDirectorID());
            ps.setInt(3, p.getGeneroID());
            ps.setInt(4, p.getAnoLanzamiento());
            ps.setString(5, p.getSinopsis());
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

    public boolean modificar(Pelicula p) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "UPDATE Peliculas SET titulo=?, directorID=?, generoID=?, anoLanzamiento=?, sinopsis=? WHERE peliculaID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getTitulo());
            ps.setInt(2, p.getDirectorID());
            ps.setInt(3, p.getGeneroID());
            ps.setInt(4, p.getAnoLanzamiento());
            ps.setString(5, p.getSinopsis());
            ps.setInt(6, p.getPeliculaID());
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

    public boolean eliminar(Pelicula p) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "DELETE FROM Peliculas WHERE peliculaID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getPeliculaID());
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

    public boolean buscar(Pelicula p) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
        String sql = "SELECT * FROM Peliculas WHERE titulo=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getTitulo());
            rs = ps.executeQuery();

            if (rs.next()) {
                p.setPeliculaID(rs.getInt("peliculaID"));
                p.setTitulo(rs.getString("titulo"));
                p.setDirectorID(rs.getInt("directorID"));
                p.setGeneroID(rs.getInt("generoID"));
                p.setAnoLanzamiento(rs.getInt("anoLanzamiento"));
                p.setSinopsis(rs.getString("sinopsis"));
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

    public List<Pelicula> listar() {
        List<Pelicula> peliculas = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
        String sql = "SELECT * FROM Peliculas";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Pelicula p = new Pelicula();
                p.setPeliculaID(rs.getInt("peliculaID"));
                p.setTitulo(rs.getString("titulo"));
                p.setDirectorID(rs.getInt("directorID"));
                p.setGeneroID(rs.getInt("generoID"));
                p.setAnoLanzamiento(rs.getInt("anoLanzamiento"));
                p.setSinopsis(rs.getString("sinopsis"));
                peliculas.add(p);
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

        return peliculas;
    }

    public List<PeliculaAuditoria> listarAuditoria() {
        List<PeliculaAuditoria> auditoriaList = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
        String sql = "SELECT * FROM auditoria";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                PeliculaAuditoria a = new PeliculaAuditoria();
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

    public boolean exists(int peliculaID) {
        // Implementar la lógica para verificar si el peliculaID existe en la base de
        // datos.
        String query = "SELECT COUNT(*) FROM Peliculas WHERE PeliculaID = ?";
        try (Connection con = getConexionMaster();
                PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, peliculaID);
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
