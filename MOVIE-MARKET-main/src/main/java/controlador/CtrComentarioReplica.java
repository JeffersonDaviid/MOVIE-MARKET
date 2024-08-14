package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.entities.Comentario;
import modelo.services.DbComentarioReplica;
import modelo.services.DbPeliculaReplica;
import modelo.services.DbUsuarioReplica;
import vista.FrmMenuReplica;

public class CtrComentarioReplica implements ActionListener {
    private Comentario mod;
    private DbComentarioReplica modDb;
    private FrmMenuReplica frm;
    private boolean verificarBusqueda = false;

    public CtrComentarioReplica(Comentario mod, DbComentarioReplica modDB, FrmMenuReplica frm) {
        this.mod = mod;
        this.modDb = modDB;
        this.frm = frm;
        this.frm.btnGuardarComentario.addActionListener(this);
        this.frm.btnBuscarComentario.addActionListener(this);
        this.frm.btnActualizarComentario1.addActionListener(this);
        this.frm.btnEliminarComentario.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Comentario");
        frm.setLocationRelativeTo(null);
        frm.idComentario.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Acción para el botón Guardar
        if (e.getSource() == frm.btnGuardarComentario) {
            int usuarioID = Integer.parseInt((String) frm.boxUsuarioIDComentario.getSelectedItem());
            int peliculaID = Integer.parseInt((String) frm.boxPeliculaIDComentario.getSelectedItem());

            DbUsuarioReplica usuarioDb = new DbUsuarioReplica();
            DbPeliculaReplica peliculaDb = new DbPeliculaReplica();

            if (!usuarioDb.exists(usuarioID)) {
                JOptionPane.showMessageDialog(null, "El Usuario no existe");
            } else if (!peliculaDb.exists(peliculaID)) {
                JOptionPane.showMessageDialog(null, "La Película no existe");
            } else {
                // Si ambos existen, guardar el comentario
                mod.setUsuarioID(usuarioID);
                mod.setPeliculaID(peliculaID);
                mod.setComentario(frm.txtComentario.getText());
                mod.setFechaComentario(new java.util.Date());

                if (modDb.guardar(mod)) {
                    JOptionPane.showMessageDialog(null, "Comentario registrado");
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar");
                    limpiar();
                }
            }
        }

        // Acción para el botón Buscar
        if (e.getSource() == frm.btnBuscarComentario) {
            mod.setUsuarioID(Integer.parseInt((String) frm.boxUsuarioIDComentario1.getSelectedItem()));
            mod.setPeliculaID(Integer.parseInt((String) frm.boxPeliculaIDComentario1.getSelectedItem()));

            if (modDb.buscar(mod)) {
                frm.idComentario.setText(String.valueOf(mod.getComentarioID()));
                frm.txtComentarioEditable.setText(mod.getComentario());
                frm.txtFechaComentario.setText(String.valueOf(mod.getFechaComentario()));

                verificarBusqueda = true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el comentario");
                limpiarActualizar();
            }
        }

        // Acción para el botón Actualizar
        if (e.getSource() == frm.btnActualizarComentario1) {
            if (verificarBusqueda) {
                int usuarioID = Integer.parseInt((String) frm.boxUsuarioIDComentario1.getSelectedItem());
                int peliculaID = Integer.parseInt((String) frm.boxPeliculaIDComentario1.getSelectedItem());

                DbUsuarioReplica usuarioDb = new DbUsuarioReplica();
                DbPeliculaReplica peliculaDb = new DbPeliculaReplica();

                if (!usuarioDb.exists(usuarioID)) {
                    JOptionPane.showMessageDialog(null, "El Usuario no existe");
                    limpiarActualizar();
                } else if (!peliculaDb.exists(peliculaID)) {
                    JOptionPane.showMessageDialog(null, "La Película no existe");
                    limpiarActualizar();
                } else {
                    // Si ambos existen, actualizar el comentario
                    mod.setUsuarioID(usuarioID);
                    mod.setPeliculaID(peliculaID);
                    mod.setComentario(frm.txtComentarioEditable1.getText());
                    mod.setFechaComentario(new java.util.Date());

                    if (modDb.modificar(mod)) {
                        JOptionPane.showMessageDialog(null, "Comentario actualizado");
                        limpiarActualizar();
                        verificarBusqueda = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar");
                        limpiarActualizar();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se realizó una búsqueda antes de la actualización");
            }
        }

        // Acción para el botón Eliminar
        if (e.getSource() == frm.btnEliminarComentario) {
            if (verificarBusqueda) {
                mod.setComentarioID(Integer.parseInt(frm.idComentario.getText()));
                if (modDb.eliminar(mod)) {
                    JOptionPane.showMessageDialog(null, "Comentario eliminado");
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
        frm.txtComentario.setText(null);
    }

    public void limpiarActualizar() {
        frm.idComentario.setText(null);
        frm.txtComentarioEditable.setText(null);
        frm.txtComentarioEditable1.setText(null);
        frm.txtFechaComentario.setText(null);
    }
}
