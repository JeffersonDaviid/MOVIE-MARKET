package Controllers.BASEDEDATOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DataHelper {
    private static DataHelper dh;
    private Connection conexion;
    private static final String URLDB = "jdbc:sqlite:courier.db";

    private DataHelper() throws SQLException {
        try {
            this.conexion = DriverManager.getConnection(URLDB);
            Statement stmt = conexion.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos." + ex);
        }
    }

    public static DataHelper getInstancia() throws SQLException {
        if (dh == null || dh.conexion.isClosed()) {
            dh = new DataHelper();
        }
        return dh;
    }

    /***
     * Método ESTÁTICO que realiza una solicitud GET a la base de datos
     * 
     * @param sql : es la petición a la Base de Datos
     * @return : retorna la respuesta de la Base de Datos, objeto de tipo ResultSet
     * @throws SQLException : indica las excepciones que se pueden lanzar durante la
     *                      ejecución, especificadas en la clase AppException
     */
    public ResultSet executeQueryRead(String sql) throws SQLException {
        Statement stmt;
        ResultSet rs = null;

        try {
            stmt = conexion.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al obtener respuesta en : executeQueryRead(String sql) " + e.getMessage());
        }
        return rs;
    }

    public int executeQueryReadLastId() throws SQLException {
        Statement stmt;
        ResultSet rs = null;

        try {
            stmt = conexion.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_rowid()");

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al obtener respuesta en : executeQueryReadLastId() " + e.getMessage());
        }
        return -1;
    }

    /***
     * Método ESTÁTICO que realiza una solicitud INSERT, PUT y DELETE a la base de
     * datos
     * 
     * @param sql : es la petición a la Base de Datos
     * @return : retorna el numero de filas afectadas, 0 = no se hizo cambios
     * @throws SQLException : indica las excepciones que se pueden lanzar durante la
     *                      ejecución, especificadas en la clase AppException
     */
    public int executeQueryInsertUpdateDelete(String sql) throws SQLException {
        Statement stmt;
        int rs = -1;

        try {
            stmt = conexion.createStatement();
            rs = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al obtener respuesta en : executeQueryInsertUpdateDelete(String sql) " + e.getMessage());
        }
        return rs;
    }

    public void closeConnection() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
