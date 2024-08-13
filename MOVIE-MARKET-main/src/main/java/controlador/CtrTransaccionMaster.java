package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.services.DbTransaccionMaster;
import modelo.entities.Transaccion;
import vista.FrmMenuMaster;
import java.util.Date;
import java.text.SimpleDateFormat;
import modelo.services.DbPeliculaMaster;
import modelo.services.DbUsuarioMaster;

public class CtrTransaccionMaster implements ActionListener {
    private Transaccion mod;
    private DbTransaccionMaster modDb;
    private FrmMenuMaster frm;
    private boolean verificarBusqueda = false;

    public CtrTransaccionMaster(Transaccion mod, DbTransaccionMaster modDb, FrmMenuMaster frm) {
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
        frm.idTransaccion.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Guardar
    if (e.getSource() == frm.btnGuardarTransaccion) {
        int peliculaID = Integer.parseInt(frm.txtPeliculaIDTransaccion.getText());
        int usuarioID = Integer.parseInt(frm.txtUsuarioIDTransaccion.getText());

        DbPeliculaMaster dbPelicula = new DbPeliculaMaster();
        DbUsuarioMaster dbUsuario = new DbUsuarioMaster();

        if (dbPelicula.exists(peliculaID) && dbUsuario.exists(usuarioID)) {
            mod.setPeliculaID(peliculaID);
            mod.setUsuarioID(usuarioID);
            mod.setFechaTransaccion(new Date());

            String item = frm.boxTipoTransaccion.getSelectedItem().toString();
            mod.setTipoTransaccion(item.equals("Compra") ? "compra" : "alquiler");

            if (modDb.guardar(mod)) {
                JOptionPane.showMessageDialog(null, "Transacción registrada");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar");
                limpiar();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Película o Usuario no existen");
        }
    }

    // Buscar
    if (e.getSource() == frm.btnBuscarTransaccion) {
        int peliculaID = Integer.parseInt(frm.txtPeliculaIDTransaccion1.getText());
        int usuarioID = Integer.parseInt(frm.txtUsuarioIDTransaccion1.getText());

        // Verificar si la película y el usuario existen
        DbPeliculaMaster dbPelicula = new DbPeliculaMaster();
        DbUsuarioMaster dbUsuario = new DbUsuarioMaster();

        if (dbPelicula.exists(peliculaID) && dbUsuario.exists(usuarioID)) {
            mod.setPeliculaID(peliculaID);
            mod.setUsuarioID(usuarioID);

            if (modDb.buscar(mod)) {
                // Mostrar los datos en los campos del formulario
                frm.idTransaccion.setText(String.valueOf(mod.getTransaccionID())); // ID de la transacción
                frm.txtUsuarioIDTransaccion1.setText(String.valueOf(mod.getUsuarioID()));
                frm.txtPeliculaIDTransaccion1.setText(String.valueOf(mod.getPeliculaID()));
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
            int peliculaID = Integer.parseInt(frm.txtPeliculaIDTransaccion1.getText());
            int usuarioID = Integer.parseInt(frm.txtUsuarioIDTransaccion1.getText());

            DbPeliculaMaster dbPelicula = new DbPeliculaMaster();
            DbUsuarioMaster dbUsuario = new DbUsuarioMaster();

            if (dbPelicula.exists(peliculaID) && dbUsuario.exists(usuarioID)) {
                mod.setPeliculaID(peliculaID);
                mod.setUsuarioID(usuarioID);
                mod.setFechaTransaccion(new Date());

                String item = frm.boxTipoTransaccionEditable.getSelectedItem().toString();
                mod.setTipoTransaccion(item.equals("Compra") ? "compra" : "alquiler");

                if (modDb.modificar(mod)) {
                    JOptionPane.showMessageDialog(null, "Transacción actualizada");
                    limpiarActualizar();
                    verificarBusqueda = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar");
                    limpiarActualizar();
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

            DbPeliculaMaster dbPelicula = new DbPeliculaMaster();
            DbUsuarioMaster dbUsuario = new DbUsuarioMaster();

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
        frm.txtPeliculaIDTransaccion.setText(null);
        frm.txtUsuarioIDTransaccion.setText(null);
        frm.boxTipoTransaccion.setSelectedIndex(-1);
    }

    public void limpiarActualizar() {
        frm.txtPeliculaIDTransaccion1.setText(null);
        frm.txtUsuarioIDTransaccion1.setText(null);
        frm.txtTipoTransaccion1.setText(null);
        frm.boxTipoTransaccionEditable.setSelectedIndex(-1);
        frm.txtFechaTransaccion.setText(null);
        frm.idTransaccion.setText(null);
    }
}

