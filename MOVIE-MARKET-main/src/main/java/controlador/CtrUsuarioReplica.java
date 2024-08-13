package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.entities.Usuario;
import java.text.SimpleDateFormat;
import modelo.services.DbUsuarioReplica;
import vista.FrmMenuReplica;

public class CtrUsuarioReplica implements ActionListener {
    private Usuario mod;
    private DbUsuarioReplica modDb;
    private FrmMenuReplica frm;
    private boolean verificarBusqueda = false;

    public CtrUsuarioReplica(Usuario mod, DbUsuarioReplica modDB, FrmMenuReplica frm) {
        this.mod = mod;
        this.modDb = modDB;
        this.frm = frm;
        this.frm.btnBuscarUsuario.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Usuario");
        frm.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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
    }
    
    public void limpiarActualizar() {
        frm.txtCorreoUsuario1.setText(null);
        frm.txtNombreUsuario1.setText(null);
        frm.txtApellidoUsuario1.setText(null);
        frm.txtFechaRegistro.setText(null);
    }
}
