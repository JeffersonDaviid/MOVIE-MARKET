package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbPeliculaReplica extends ConexionReplica {

    public boolean guardar(Pelicula p) {
        PreparedStatement ps;
        Connection con = getConexionReplica();
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
        Connection con = getConexionReplica();
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
        Connection con = getConexionReplica();
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
        Connection con = getConexionReplica();
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
}

