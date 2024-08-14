package modelo.entities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 *
 * @author aseba
 */
public class ConexionMaster {
    private String base;
    private String user;
    private String password;
    private String url;
    private Connection con = null;

    public Connection getConexionMaster() {
        if (cargarDatos()) {
            try {
                con = DriverManager.getConnection(this.url, this.user, this.password);
            } catch (SQLException e) {
                System.err.println("Error al establecer la conexión: " + e.getMessage());
            }
        } else {
            System.err.println("Los datos no se pudieron cargar. No se intentará la conexión.");
        }
        return con;
    }

    private boolean cargarDatos() {
        Properties props = new Properties();
        try {
            // Asegúrate de que la ruta al archivo "opciones.properties" es correcta.
            Path path = Paths.get("opciones.properties");
            if (!Files.exists(path)) {
                throw new FileNotFoundException(
                        "El archivo opciones.properties no se encontró en la ruta especificada.");
            }

            try (InputStream in = Files.newInputStream(path)) {
                props.load(in);
            }

            base = props.getProperty("base");
            user = props.getProperty("master.user");
            password = props.getProperty("master.password");
            url = props.getProperty("master.url") + base;

            if (user == null || password == null || url == null) {
                throw new IllegalArgumentException("Algunas propiedades no están configuradas correctamente.");
            }

            return true; // Datos cargados correctamente

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Archivo de propiedades no encontrado: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,
                    "Error en las propiedades: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al leer el archivo de propiedades: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error inesperado: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false; // Indica que hubo un error al cargar los datos
    }

}
