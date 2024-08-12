/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.DbGeneroMaster;
import modelo.Genero;
import vista.FrmMenuMaster;

/**
 *
 * @author aseba
 */
public class CtrGenero implements ActionListener {
    private Genero mod;
    private DbGeneroMaster modDb;
    private FrmMenuMaster frm;
    private boolean verificarBusqueda = false;

    public CtrGenero(Genero mod, DbGeneroMaster modDB, FrmMenuMaster frm) {
        this.mod = mod;
        this.modDb = modDB;
        this.frm = frm;
        this.frm.btnGuardarGeneros.addActionListener(this);
        this.frm.btnBuscarGenero.addActionListener(this);
        this.frm.btnActualizarGenero1.addActionListener(this);
        this.frm.btnEliminarGenero.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Director");
        frm.setLocationRelativeTo(null);
        frm.txtID.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Guardar
        if (e.getSource() == frm.btnGuardarGeneros) {
            mod.setNombre(frm.txtNombreGeneros.getText());
            
            if (modDb.guardar(mod)) {
                JOptionPane.showMessageDialog(null, "Genero registrado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar");
                limpiar();
            }
        }

        // Buscar
        if (e.getSource() == frm.btnBuscarGenero) {

            mod.setNombre(frm.txtNombreGenero1.getText());

            if (modDb.buscar(mod)) {
                frm.idGenero.setText(String.valueOf(mod.getGeneroID()));
                frm.txtNombreGenero1.setText(mod.getNombre());
                verificarBusqueda = true;
                JOptionPane.showMessageDialog(null, "Genero existente en sistema");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el genero");
                limpiarActualizar();
            }
        }

        // Actualizar
        if (e.getSource() == frm.btnActualizarGenero1) {
            if (verificarBusqueda) {
                mod.setNombre(frm.txtNombreGenero1.getText());


                if (modDb.modificar(mod)) {
                    JOptionPane.showMessageDialog(null, "Genero actualizado");
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
        if (e.getSource() == frm.btnEliminarGenero) {
            if (verificarBusqueda) {
                mod.setGeneroID(Integer.parseInt(frm.idGenero.getText()));
                if(modDb.eliminar(mod)){
                    JOptionPane.showMessageDialog(null, "Genero eliminado");
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
        frm.txtNombreGeneros.setText(null);
    }
    
    public void limpiarActualizar() {
        frm.txtNombreGenero1.setText(null);
    }
}
