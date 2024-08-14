package controlador;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import modelo.entities.Director;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import modelo.services.DbDirectorReplica;
import vista.FrmMenuReplica;

/**
 *
 * @author aseba
 */
public class CtrDirectorReplica implements ActionListener {
    private Director mod;
    private DbDirectorReplica modDb;
    private FrmMenuReplica frm;

    public CtrDirectorReplica(Director mod, DbDirectorReplica modDB, FrmMenuReplica frm) {
        this.mod = mod;
        this.modDb = modDB;
        this.frm = frm;
        this.frm.btnBuscarDirector.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Director");
        frm.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Buscar
        if (e.getSource() == frm.btnBuscarDirector) {

            mod.setNombre(frm.txtNombreDirector.getText());
            mod.setApellido(frm.txtApellidoDirector.getText());

            if (modDb.buscar(mod)) {
                frm.idDirector.setText(String.valueOf(mod.getDirectorID()));
                frm.txtNombreDirector.setText(mod.getNombre());
                frm.txtApellidoDirector.setText(mod.getApellido());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fechaNacimiento = dateFormat.format(mod.getFechaNacimiento());
                frm.txtFechaNacimiento.setText(fechaNacimiento);
                frm.txtPaisOrigenDirector.setText(mod.getPaisOrigen());
                frm.txtPremios.setText(String.valueOf(mod.getPremios()));
                frm.txtPeliculasDirigidas.setText(String.valueOf(mod.getPeliculasDirigidas()));
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el director");
                limpiarActualizar();
            }
        }
    }

    public void limpiarActualizar() {
        frm.txtNombreDirector.setText(null);
        frm.txtApellidoDirector.setText(null);
        frm.txtFechaNacimiento.setText(null);
        frm.txtPaisOrigenDirector.setText(null);
        frm.txtPremios.setText(null);
        frm.txtPeliculasDirigidas.setText(null);
        frm.idDirector.setText(null);
    }
}
