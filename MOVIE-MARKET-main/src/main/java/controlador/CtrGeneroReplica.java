/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.entities.Genero;
import modelo.services.DbGeneroReplica;
import vista.FrmMenuReplica;

/**
 *
 * @author aseba
 */
public class CtrGeneroReplica implements ActionListener {
    private Genero mod;
    private DbGeneroReplica modDb;
    private FrmMenuReplica frm;

    public CtrGeneroReplica(Genero mod, DbGeneroReplica modDB, FrmMenuReplica frm) {
        this.mod = mod;
        this.modDb = modDB;
        this.frm = frm;
        this.frm.btnBuscarGenero.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Director");
        frm.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Buscar
        if (e.getSource() == frm.btnBuscarGenero) {

            mod.setNombre(frm.txtNombreGenero1.getText());

            if (modDb.buscar(mod)) {
                frm.idGenero.setText(String.valueOf(mod.getGeneroID()));
                frm.txtNombreGenero1.setText(mod.getNombre());
                JOptionPane.showMessageDialog(null, "Genero existente en sistema");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el genero");
                limpiarActualizar();
            }
        }
    }

    public void limpiarActualizar() {
        frm.txtNombreGenero1.setText(null);
    }
}
