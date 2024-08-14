package modelo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entities.Comentario;
import modelo.entities.ComentarioAuditoria;
import modelo.entities.ConexionReplica;

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
        String sql = "SELECT * FROM Comentarios WHERE peliculaID=? AND usuarioID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getPeliculaID());
            ps.setInt(2, c.getUsuarioID());
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

    public List<Comentario> listar() {
        List<Comentario> comentarios = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM Comentarios";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Comentario comentario = new Comentario();
                comentario.setComentarioID(rs.getInt("comentarioID"));
                comentario.setPeliculaID(rs.getInt("peliculaID"));
                comentario.setUsuarioID(rs.getInt("usuarioID"));
                comentario.setComentario(rs.getString("comentario"));
                comentario.setFechaComentario(rs.getDate("fechaComentario"));
                comentarios.add(comentario);
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

        return comentarios;
    }

    public List<ComentarioAuditoria> listarAuditoria() {
        List<ComentarioAuditoria> listaAuditoria = new ArrayList<>();
        String query = "SELECT * FROM auditoria";

        try (Connection con = getConexionReplica();
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                ComentarioAuditoria auditoria = new ComentarioAuditoria();
                auditoria.setUserName(rs.getString("userName"));
                auditoria.setFecha(rs.getDate("fecha"));
                auditoria.setTipoOperacion(rs.getString("tipoOperacion"));
                auditoria.setNombreTabla(rs.getString("nombreTabla"));
                auditoria.setAnterior(rs.getString("anterior"));
                auditoria.setNuevo(rs.getString("nuevo"));
                listaAuditoria.add(auditoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAuditoria;
    }

}
