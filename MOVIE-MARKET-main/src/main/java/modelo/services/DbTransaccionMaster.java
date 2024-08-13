package modelo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entities.ConexionMaster;
import modelo.entities.Transaccion;
import modelo.entities.TransaccionAuditoria;

public class DbTransaccionMaster extends ConexionMaster {

    public boolean guardar(Transaccion t) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "INSERT INTO Transacciones (peliculaID, usuarioID, fechaTransaccion, tipoTransaccion) VALUES (?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getPeliculaID());
            ps.setInt(2, t.getUsuarioID());
            ps.setDate(3, new java.sql.Date(t.getFechaTransaccion().getTime()));
            ps.setString(4, t.getTipoTransaccion());
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

    public boolean modificar(Transaccion t) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "UPDATE Transacciones SET peliculaID=?, usuarioID=?, fechaTransaccion=?, tipoTransaccion=? WHERE transaccionID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getPeliculaID());
            ps.setInt(2, t.getUsuarioID());
            ps.setDate(3, new java.sql.Date(t.getFechaTransaccion().getTime()));
            ps.setString(4, t.getTipoTransaccion());
            ps.setInt(5, t.getTransaccionID());
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

    public boolean eliminar(Transaccion t) {
        PreparedStatement ps;
        Connection con = getConexionMaster();
        String sql = "DELETE FROM Transacciones WHERE transaccionID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getTransaccionID());
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

    public boolean buscar(Transaccion t) {
        PreparedStatement ps;
    ResultSet rs;
    Connection con = getConexionMaster();
    String sql = "SELECT * FROM Transacciones WHERE peliculaID=? AND usuarioID=?";

    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, t.getPeliculaID());
        ps.setInt(2, t.getUsuarioID());
        rs = ps.executeQuery();

        if (rs.next()) {
            t.setTransaccionID(rs.getInt("transaccionID"));
            t.setPeliculaID(rs.getInt("peliculaID"));
            t.setUsuarioID(rs.getInt("usuarioID"));
            t.setFechaTransaccion(rs.getDate("fechaTransaccion"));
            t.setTipoTransaccion(rs.getString("tipoTransaccion"));
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
    
    
    public List<Transaccion> listar() {
        List<Transaccion> transacciones = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
        String sql = "SELECT * FROM Transacciones";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Transaccion t = new Transaccion();
                t.setTransaccionID(rs.getInt("transaccionID"));
                t.setPeliculaID(rs.getInt("peliculaID"));
                t.setUsuarioID(rs.getInt("usuarioID"));
                t.setFechaTransaccion(rs.getDate("fechaTransaccion"));
                t.setTipoTransaccion(rs.getString("tipoTransaccion"));
                transacciones.add(t);
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

        return transacciones;
    }

    public List<TransaccionAuditoria> listarAuditoria() {
        List<TransaccionAuditoria> auditoriaList = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexionMaster();
        String sql = "SELECT * FROM auditoriaTransacciones";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                TransaccionAuditoria a = new TransaccionAuditoria();
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
}
