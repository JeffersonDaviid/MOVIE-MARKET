package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.services.DbUsuarioMaster;
import modelo.entities.Usuario;
import vista.FrmMenuMaster;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CtrUsuarioMaster implements ActionListener {
    private Usuario mod;
    private DbUsuarioMaster modDb;
    private FrmMenuMaster frm;
    private boolean verificarBusqueda = false;

    public CtrUsuarioMaster(Usuario mod, DbUsuarioMaster modDB, FrmMenuMaster frm) {
        this.mod = mod;
        this.modDb = modDB;
        this.frm = frm;
        this.frm.btnGuardarUsuario.addActionListener(this);
        this.frm.btnBuscarUsuario.addActionListener(this);
        this.frm.btnActualizarUsuario1.addActionListener(this);
        this.frm.btnEliminarUsuario.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Usuario");
        frm.setLocationRelativeTo(null);
        frm.txtID.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Guardar
        if (e.getSource() == frm.btnGuardarUsuario) {
            mod.setCorreo(frm.txtCorreoUsuario.getText());
            mod.setNombre(frm.txtNombreUsuario.getText());
            mod.setApellido(frm.txtApellidoUsuario.getText());
            mod.setFechaRegistro(new Date());

            if (modDb.guardar(mod)) {
                JOptionPane.showMessageDialog(null, "Usuario registrado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar");
                limpiar();
            }
        }

        // Buscar
        if (e.getSource() == frm.btnBuscarUsuario) {

            mod.setCorreo(frm.txtCorreoUsuario1.getText());

            if (modDb.buscar(mod)) {
                frm.idUsuario.setText(String.valueOf(mod.getUsuarioID()));
                frm.txtCorreoUsuario1.setText(mod.getCorreo());
                frm.txtNombreUsuario1.setText(mod.getNombre());
                frm.txtApellidoUsuario1.setText(mod.getApellido());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fechaRegistro = dateFormat.format(mod.getFechaRegistro());
                frm.txtFechaRegistro.setText(fechaRegistro);

                verificarBusqueda = true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario");
                limpiarActualizar();
            }
        }

        // Actualizar
        if (e.getSource() == frm.btnActualizarUsuario1) {
            if (verificarBusqueda) {
                mod.setCorreo(frm.txtCorreoUsuario1.getText());
                mod.setNombre(frm.txtNombreUsuarioEditable.getText());
                mod.setApellido(frm.txtApellidoUsuarioEditable.getText());
                mod.setFechaRegistro(new Date());

                if (modDb.modificar(mod)) {
                    JOptionPane.showMessageDialog(null, "Usuario actualizado");
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
        if (e.getSource() == frm.btnEliminarUsuario) {
            if (verificarBusqueda) {
                mod.setUsuarioID(Integer.parseInt(frm.idUsuario.getText()));
                if (modDb.eliminar(mod)) {
                    JOptionPane.showMessageDialog(null, "Usuario eliminado");
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
        frm.txtCorreoUsuario.setText(null);
        frm.txtNombreUsuario.setText(null);
        frm.txtApellidoUsuario.setText(null);
    }

    public void limpiarActualizar() {
        frm.txtCorreoUsuario1.setText(null);
        frm.txtNombreUsuario1.setText(null);
        frm.txtApellidoUsuario1.setText(null);
        frm.txtFechaRegistro.setText(null);
        frm.txtID.setText(null);
        frm.txtNombreUsuarioEditable.setText(null);
        frm.txtApellidoUsuarioEditable.setText(null);
    }
}
