package controlador;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import modelo.services.DbDirectorMaster;
import modelo.entities.Director;
import vista.FrmMenuMaster;
import java.util.Date;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

/**
 *
 * @author aseba
 */
public class CtrDirectorMaster implements ActionListener {
    private Director mod;
    private DbDirectorMaster modDb;
    private FrmMenuMaster frm;
    private boolean verificarBusqueda = false;

    public CtrDirectorMaster(Director mod, DbDirectorMaster modDB, FrmMenuMaster frm) {
        this.mod = mod;
        this.modDb = modDB;
        this.frm = frm;
        this.frm.btnGuardar.addActionListener(this);
        this.frm.btnBuscarDirector.addActionListener(this);
        this.frm.btnActualizarDirector.addActionListener(this);
        this.frm.btnEliminarDirector.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Director");
        frm.setLocationRelativeTo(null);
        frm.txtID.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Guardar
        if (e.getSource() == frm.btnGuardar) {
            mod.setNombre(frm.txtNombre.getText());
            mod.setApellido(frm.txtApellido.getText());
            mod.setFechaNacimiento(frm.dateFechaNacimiento.getDate());
            mod.setPaisOrigen(frm.txtPaisOrigen.getText());
            mod.setPremios((int) frm.intPremios.getValue());
            mod.setPeliculasDirigidas((int) frm.intPeliculasDirigidas.getValue());
            mod.setFechaCreacion(new Date());
            
            if (modDb.guardar(mod)) {
                JOptionPane.showMessageDialog(null, "Director registrado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar");
                limpiar();
            }
        }

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
                verificarBusqueda = true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el director");
                limpiarActualizar();
            }
        }

        // Actualizar
        if (e.getSource() == frm.btnActualizarDirector) {
            if (verificarBusqueda) {
                mod.setNombre(frm.txtNombreDirector.getText());
                mod.setApellido(frm.txtApellidoDirector.getText());
                mod.setFechaNacimiento(frm.fechaNacimientoDirector.getDate());
                mod.setPaisOrigen(frm.PaisOrigen.getText());
                mod.setPremios((int) frm.PremiosDirector.getValue());
                mod.setPeliculasDirigidas((int) frm.peliculasDirigidas.getValue());
                mod.setFechaCreacion(new Date());

                if (modDb.modificar(mod)) {
                    JOptionPane.showMessageDialog(null, "Director actualizado");
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
        if (e.getSource() == frm.btnEliminarDirector) {
            if (verificarBusqueda) {
                mod.setDirectorID(Integer.parseInt(frm.idDirector.getText()));
                if(modDb.eliminar(mod)){
                    JOptionPane.showMessageDialog(null, "Cliente eliminado");
                    limpiarActualizar();
                }else{
                    JOptionPane.showMessageDialog(null, "Error al eliminar");
                    limpiarActualizar();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No se realizó una búsqueda antes de la actualización");
            }
        }
    }
    

    public void limpiar() {
        frm.txtNombre.setText(null);
        frm.txtApellido.setText(null);
        frm.dateFechaNacimiento.setDate(null);
        frm.txtPaisOrigen.setText(null);
        frm.intPremios.setValue(0);
        frm.intPeliculasDirigidas.setValue(0);
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
