package modelo.entities;

import java.sql.*;

/**
 *
 * @author aseba
 */
public class ConexionReplica {
    private final String base = "orcl";
    private final String user = "replicaproyecto";
    private final String password = "replicaproyecto";
    private final String url = "jdbc:oracle:thin:@//LAPTOP-3608JNJI:1521/" + base;
    private Connection con = null;

    public Connection getConexionReplica() {
        try {
            con = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return con;
    }
}
