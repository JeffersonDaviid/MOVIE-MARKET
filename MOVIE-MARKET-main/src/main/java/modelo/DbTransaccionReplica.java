package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbTransaccionReplica extends ConexionReplica {

    public boolean guardar(Transaccion t) {
        PreparedStatement ps;
        Connection con = getConexionReplica();
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
        Connection con = getConexionReplica();
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
        Connection con = getConexionReplica();
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
        Connection con = getConexionReplica();
        String sql = "SELECT * FROM Transacciones WHERE transaccionID=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getTransaccionID());
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
}
