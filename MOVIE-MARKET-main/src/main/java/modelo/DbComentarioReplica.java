package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbComentarioReplica extends ConexionReplica {

    public boolean guardar(Comentario c) {
        PreparedStatement ps;
        Connection con = getConexionReplica();
        String sql = "INSERT INTO Comentarios (peliculaID, usuarioID, comentario, fechaComentario) VALUES (?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getPeliculaID());
            ps.setInt(2, c.getUsuarioID());
            ps.setString(3, c.getComentario());
            ps.setDate(4, new java.sql.Date(c.getFechaComentario().getTime()));
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

    public boolean modificar(Comentario c) {
        PreparedStatement ps;
        Connection con = getConexionReplica();
        String sql = "UPDATE Comentarios SET peliculaID=?, usuarioID=?, comentario=?, fechaComentario=? WHERE comentarioID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getPeliculaID());
            ps.setInt(2, c.getUsuarioID());
            ps.setString(3, c.getComentario());
            ps.setDate(4, new java.sql.Date(c.getFechaComentario().getTime()));
            ps.setInt(5, c.getComentarioID());
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

    public boolean eliminar(Comentario c) {
        PreparedStatement ps;
        Connection con = getConexionReplica();
        String sql = "DELETE FROM Comentarios WHERE comentarioID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getComentarioID());
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

    public boolean buscar(Comentario c) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM Comentarios WHERE comentario=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getComentario());
            rs = ps.executeQuery();

            if (rs.next()) {
                c.setComentarioID(rs.getInt("comentarioID"));
                c.setPeliculaID(rs.getInt("peliculaID"));
                c.setUsuarioID(rs.getInt("usuarioID"));
                c.setComentario(rs.getString("comentario"));
                c.setFechaComentario(rs.getDate("fechaComentario"));
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
