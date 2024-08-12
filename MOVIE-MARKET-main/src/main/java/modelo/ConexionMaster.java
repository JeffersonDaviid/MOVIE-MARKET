package modelo;
import java.sql.*;

/**
 *
 * @author aseba
 */
public class ConexionMaster {
    private final String base = "orcl";
    private final String user = "master";
    private final String password = "master";
    private final String url="jdbc:oracle:thin:@//Alejandro:1521/"+base;
    private Connection con=null;
    
    public Connection getConexionMaster(){
        try{
            con=DriverManager.getConnection(this.url, this.user, this.password);
        }
        catch(SQLException e){
            System.err.println(e);            
        }
        return con;
    }
}
