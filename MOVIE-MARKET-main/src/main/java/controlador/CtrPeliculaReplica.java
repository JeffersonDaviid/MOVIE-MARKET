package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.entities.Pelicula;
import modelo.services.DbDirectorMaster;
import modelo.services.DbDirectorReplica;
import modelo.services.DbGeneroMaster;
import modelo.services.DbGeneroReplica;
import modelo.services.DbPeliculaReplica;
import vista.FrmMenuReplica;

public class CtrPeliculaReplica implements ActionListener {
    private Pelicula mod;
    private DbPeliculaReplica modDb;
    private FrmMenuReplica frm;
    private boolean verificarBusqueda = false;

    public CtrPeliculaReplica(Pelicula mod, DbPeliculaReplica modDB, FrmMenuReplica frm) {
        this.mod = mod;
        this.modDb = modDB;
        this.frm = frm;
        this.frm.btnGuardarPelicula.addActionListener(this);
        this.frm.btnBuscarPelicula.addActionListener(this);
        this.frm.btnActualizarPelicula1.addActionListener(this);
        this.frm.btnEliminarPelicula.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Película");
        frm.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Acción para el botón Guardar
if (e.getSource() == frm.btnGuardarPelicula) {
    int directorID = Integer.parseInt((String) frm.boxDirectorIDPelicula.getSelectedItem());
    int generoID = Integer.parseInt((String) frm.boxGeneroIDPelicula.getSelectedItem());
    int anioLanzamiento = frm.anioLanzamiento.getYear();

    DbDirectorReplica directorDb = new DbDirectorReplica();
    DbGeneroReplica generoDb = new DbGeneroReplica();
    
    if (anioLanzamiento<2000){
    if (!directorDb.exists(directorID)) {
        JOptionPane.showMessageDialog(null, "El Director no existe");
    } else if (!generoDb.exists(generoID)) {
        JOptionPane.showMessageDialog(null, "El Género no existe");
    } else {
        // Si ambos existen, guardar la película
        mod.setTitulo(frm.txtTituloPelicula.getText());
        mod.setDirectorID(directorID);
        mod.setGeneroID(generoID);
        mod.setAnoLanzamiento(frm.anioLanzamiento.getYear());
        mod.setSinopsis(frm.txtSinopsisPelicula.getText());

        if (modDb.guardar(mod)) {
            JOptionPane.showMessageDialog(null, "Película registrada");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar");
            limpiar();
        }
    }
    }else{
        JOptionPane.showMessageDialog(null, "No puede ingresar peliculas lanzadas despues de los 2000");
        limpiar();
    }
    
}


        // Acción para el botón Buscar
if (e.getSource() == frm.btnBuscarPelicula) {

    // Buscar la película por título
    mod.setTitulo(frm.txtNombrePelicula1.getText());

    if (modDb.buscar(mod)) {
        int directorID = mod.getDirectorID();
        int generoID = mod.getGeneroID();

        // Crear instancias de las clases que verifican la existencia
        DbDirectorReplica directorDb = new DbDirectorReplica();
        DbGeneroReplica generoDb = new DbGeneroReplica();

        // Verificar existencia del DirectorID
        if (!directorDb.exists(directorID)) {
            JOptionPane.showMessageDialog(null, "El Director asociado no existe");
            limpiarActualizar();
            return; // Salir del método si el director no existe
        }

        // Verificar existencia del GeneroID
        if (!generoDb.exists(generoID)) {
            JOptionPane.showMessageDialog(null, "El Género asociado no existe");
            limpiarActualizar();
            return; // Salir del método si el género no existe
        }

        // Si ambos existen, actualizar los campos del formulario
        frm.idPelicula.setText(String.valueOf(mod.getPeliculaID()));
        frm.txtNombrePelicula1.setText(mod.getTitulo());
        frm.txtDirectorID1.setText(String.valueOf(directorID));
        frm.txtGeneroID1.setText(String.valueOf(generoID));
        frm.txtAnioLanzamiento1.setText(String.valueOf(mod.getAnoLanzamiento()));
        frm.txtSinopsisPelicula1.setText(mod.getSinopsis());

        verificarBusqueda = true;
    } else {
        JOptionPane.showMessageDialog(null, "No se encontró la película");
        limpiarActualizar();
    }
}


        // Acción para el botón Actualizar
if (e.getSource() == frm.btnActualizarPelicula1) {
    if (verificarBusqueda) {
        // Obtener los valores de los campos
        String titulo = frm.txtNombrePelicula1.getText();
        int directorID = Integer.parseInt((String) frm.boxDirectorIDPelicula1.getSelectedItem());
        int generoID = Integer.parseInt((String) frm.boxGeneroIDPelicula1.getSelectedItem());
        int anoLanzamiento = frm.anioLanzamientoEditable.getYear();
        String sinopsis = frm.txtSinopsisPelicula1.getText();

        // Crear instancias de las clases que verifican la existencia
        DbDirectorMaster directorDb = new DbDirectorMaster();
        DbGeneroMaster generoDb = new DbGeneroMaster();

        // Verificar existencia del DirectorID
        if (!directorDb.exists(directorID)) {
            JOptionPane.showMessageDialog(null, "El Director asociado no existe");
            limpiarActualizar();
            return; // Salir del método si el director no existe
        }

        // Verificar existencia del GeneroID
        if (!generoDb.exists(generoID)) {
            JOptionPane.showMessageDialog(null, "El Género asociado no existe");
            limpiarActualizar();
            return; // Salir del método si el género no existe
        }

        // Si ambos existen, actualizar la película
        mod.setTitulo(titulo);
        mod.setDirectorID(directorID);
        mod.setGeneroID(generoID);
        mod.setAnoLanzamiento(anoLanzamiento);
        mod.setSinopsis(sinopsis);

        if (modDb.modificar(mod)) {
            JOptionPane.showMessageDialog(null, "Película actualizada");
            limpiarActualizar();
            verificarBusqueda = false;
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            limpiarActualizar();
        }
    } else {
        JOptionPane.showMessageDialog(null, "No se realizó una búsqueda antes de la actualización");
    }
}


        // Eliminar
        if (e.getSource() == frm.btnEliminarPelicula) {
            if (verificarBusqueda) {
                mod.setPeliculaID(Integer.parseInt(frm.idPelicula.getText()));
                if (modDb.eliminar(mod)) {
                    JOptionPane.showMessageDialog(null, "Película eliminada");
                    limpiarActualizar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar");
                    limpiarActualizar();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se realizó una búsqueda antes de la eliminación");
            }
        }
    }

    public void limpiar() {
        frm.txtTituloPelicula.setText(null);
        frm.anioLanzamiento.setYear(2024);
        frm.txtSinopsisPelicula.setText(null);
    }

    public void limpiarActualizar() {
        frm.txtNombrePelicula1.setText(null);
        frm.txtDirectorID1.setText(null);
        frm.txtGeneroID1.setText(null);
        frm.anioLanzamientoEditable.setYear(2024);
        frm.txtSinopsisPelicula1.setText(null);
        frm.txtAnioLanzamiento1.setText(null);
    }
}
