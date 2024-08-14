package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.entities.Transaccion;
import java.util.Date;
import java.text.SimpleDateFormat;
import modelo.services.DbPeliculaReplica;
import modelo.services.DbTransaccionReplica;
import modelo.services.DbUsuarioReplica;
import vista.FrmMenuReplica;

public class CtrTransaccionReplica implements ActionListener {
    private Transaccion mod;
    private DbTransaccionReplica modDb;
    private FrmMenuReplica frm;
    private boolean verificarBusqueda = false;

    public CtrTransaccionReplica(Transaccion mod, DbTransaccionReplica modDb, FrmMenuReplica frm) {
        this.mod = mod;
        this.modDb = modDb;
        this.frm = frm;
        this.frm.btnGuardarTransaccion.addActionListener(this);
        this.frm.btnBuscarTransaccion.addActionListener(this);
        this.frm.btnActualizarTransaccion1.addActionListener(this);
        this.frm.btnEliminarTransaccion.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Transacción");
        frm.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Guardar
    if (e.getSource() == frm.btnGuardarTransaccion) {
        int peliculaID = Integer.parseInt((String) frm.boxPeliculaIDTransaccion.getSelectedItem());
        int usuarioID = Integer.parseInt((String) frm.boxUsuarioIDTransaccion.getSelectedItem());

        DbPeliculaReplica dbPelicula = new DbPeliculaReplica();
        DbUsuarioReplica dbUsuario = new DbUsuarioReplica();

        if (dbPelicula.exists(peliculaID) && dbUsuario.exists(usuarioID)) {
            mod.setPeliculaID(peliculaID);
            mod.setUsuarioID(usuarioID);
            mod.setFechaTransaccion(new Date());

            String item = frm.boxTipoTransaccion.getSelectedItem().toString();
            mod.setTipoTransaccion(item.equals("Compra") ? "compra" : item.equals("Alquiler") ? "alquiler" : "");
            if(item.equals("Compra"))
                JOptionPane.showMessageDialog(null, "No es posible añadir transacción tipo Compra");     
            else{

            if (modDb.guardar(mod)) {
                JOptionPane.showMessageDialog(null, "Transacción registrada");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar");
                limpiar();
            }}
        } else {
            JOptionPane.showMessageDialog(null, "Película o Usuario no existen");
        }
    }

    // Buscar
    if (e.getSource() == frm.btnBuscarTransaccion) {
        int peliculaID = Integer.parseInt((String) frm.boxPeliculaIDTransaccion1.getSelectedItem());
        int usuarioID = Integer.parseInt((String) frm.boxUsuarioIDTransaccion1.getSelectedItem());

        // Verificar si la película y el usuario existen
        DbPeliculaReplica dbPelicula = new DbPeliculaReplica();
        DbUsuarioReplica dbUsuario = new DbUsuarioReplica();

        if (dbPelicula.exists(peliculaID) && dbUsuario.exists(usuarioID)) {
            mod.setPeliculaID(peliculaID);
            mod.setUsuarioID(usuarioID);

            if (modDb.buscar(mod)) {
                // Mostrar los datos en los campos del formulario
                frm.idTransaccion.setText(String.valueOf(mod.getTransaccionID())); // ID de la transacción
                frm.txtTipoTransaccion1.setText(mod.getTipoTransaccion());

                // Formatear la fecha para mostrar en el formulario
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fechaTransaccion = dateFormat.format(mod.getFechaTransaccion());
                frm.txtFechaTransaccion.setText(fechaTransaccion);

                verificarBusqueda = true; // Actualizar el estado de búsqueda
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la transacción");
                limpiarActualizar(); // Limpiar campos de actualización si no se encuentra la transacción
            }
        } else {
            JOptionPane.showMessageDialog(null, "Película o Usuario no existen");
        }
    }

    // Actualizar
    if (e.getSource() == frm.btnActualizarTransaccion1) {
        if (verificarBusqueda) {
            int peliculaID = Integer.parseInt((String) frm.boxPeliculaIDTransaccion1.getSelectedItem());
            int usuarioID = Integer.parseInt((String) frm.boxUsuarioIDTransaccion1.getSelectedItem());

            DbPeliculaReplica dbPelicula = new DbPeliculaReplica();
            DbUsuarioReplica dbUsuario = new DbUsuarioReplica();

            if (dbPelicula.exists(peliculaID) && dbUsuario.exists(usuarioID)) {
                mod.setPeliculaID(peliculaID);
                mod.setUsuarioID(usuarioID);
                mod.setFechaTransaccion(new Date());

                String item = frm.boxTipoTransaccionEditable.getSelectedItem().toString();
                mod.setTipoTransaccion(item.equals("Compra") ? "compra" : item.equals("Alquiler") ? "alquiler" : "");
                if(item.equals("Compra")){
                    JOptionPane.showMessageDialog(null, "No es posible modificar transacción a tipo Compra");
                    verificarBusqueda=false;
                    limpiarActualizar();
                }    
                else{

                if (modDb.modificar(mod)) {
                    JOptionPane.showMessageDialog(null, "Transacción actualizada");
                    limpiarActualizar();
                    verificarBusqueda = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar");
                    limpiarActualizar();
                }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Película o Usuario no existen");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se realizó una búsqueda antes de la actualización");
        }
    }

    // Eliminar
    if (e.getSource() == frm.btnEliminarTransaccion) {
        if (verificarBusqueda) {
            int peliculaID = Integer.parseInt(frm.idTransaccion.getText());

            DbPeliculaReplica dbPelicula = new DbPeliculaReplica();
            DbUsuarioReplica dbUsuario = new DbUsuarioReplica();

            if (dbPelicula.exists(peliculaID) && dbUsuario.exists(mod.getUsuarioID())) {
                if (modDb.eliminar(mod)) {
                    JOptionPane.showMessageDialog(null, "Transacción eliminada");
                    limpiarActualizar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar");
                    limpiarActualizar();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Película o Usuario no existen");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se realizó una búsqueda antes de la eliminación");
        }
    }
    }

    public void limpiar() {
    }

    public void limpiarActualizar() {
        frm.txtTipoTransaccion1.setText(null);
        frm.txtFechaTransaccion.setText(null);
        frm.idTransaccion.setText(null);
    }
}

