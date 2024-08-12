/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyecto.movies;

import controlador.CtrComentario;
import controlador.CtrDirector;
import controlador.CtrGenero;
import controlador.CtrPelicula;
import controlador.CtrTransaccion;
import controlador.CtrUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.FrmInicioSesion;
import javax.swing.JOptionPane;
import modelo.Comentario;
import modelo.DbComentarioMaster;
import modelo.DbDirectorMaster;
import modelo.DbGeneroMaster;
import modelo.DbPeliculaMaster;
import modelo.DbTransaccionMaster;
import modelo.DbUsuarioMaster;
import modelo.Director;
import modelo.Genero;
import modelo.Pelicula;
import modelo.Transaccion;
import modelo.Usuario;
import vista.FrmMenuMaster;

/**
 *
 * @author TOMMY
 */
public class MOVIES {

    private static final String USUARIO_MASTER = "master";
    private static final String CONTRASENA_MASTER = "masterpass";
    private static final String USUARIO_REPLICA = "replica";
    private static final String CONTRASENA_REPLICA = "replicapass";
    private static final int MAX_INTENTOS = 5;
    private static int intentosFallidos = 0;

    public static void main(String[] args) {
        
        // Crear la ventana principal
        FrmInicioSesion frmInicioSesion = new FrmInicioSesion();
        frmInicioSesion.setLocationRelativeTo(null);
        frmInicioSesion.setVisible(true);
                
        frmInicioSesion.btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener valores de los campos de texto
                String usuario = frmInicioSesion.campoUsuario.getText();
                String contrasena = new String(frmInicioSesion.campoContrasena.getPassword());
                
                // Verificar el inicio de sesión
                //if (usuario.equals(USUARIO_MASTER) && contrasena.equals(CONTRASENA_MASTER)) {
                if (true) {
                    JOptionPane.showMessageDialog(frmInicioSesion, "Inicio de sesión exitoso como MASTER");
                    // Abre la ventana para el usuario MASTER
                    frmInicioSesion.setVisible(false);
                    abrirVentanaMaster();
                    
                } else if (usuario.equals(USUARIO_REPLICA) && contrasena.equals(CONTRASENA_REPLICA)) {
                    JOptionPane.showMessageDialog(frmInicioSesion, "Inicio de sesión exitoso como REPLICA");
                    // Abre la ventana para el usuario REPLICA
                    abrirVentanaReplica();
                    
                } else {
                    intentosFallidos++;
                    if (intentosFallidos >= MAX_INTENTOS) {
                        JOptionPane.showMessageDialog(frmInicioSesion, "Intentos de acceso superados. El programa se cerrará.");
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(frmInicioSesion, "Usuario o contraseña incorrectos. Intentos restantes: " + (MAX_INTENTOS - intentosFallidos));
                    }
                }
            }
        });
    }

    private static void abrirVentanaMaster() {
       
        Director modDirector = new Director();
        DbDirectorMaster modDbDirectorMaster = new DbDirectorMaster();
        
        Genero modGenero = new Genero();
        DbGeneroMaster modDbGeneroMaster = new DbGeneroMaster();
        
        Usuario modUsuario = new Usuario();
        DbUsuarioMaster modDbUsuarioMaster = new DbUsuarioMaster();
        
        Pelicula modPelicula = new Pelicula();
        DbPeliculaMaster modDbPeliculaMaster = new DbPeliculaMaster();
        
        Comentario modComentario = new Comentario();
        DbComentarioMaster modDbComentarioMaster = new DbComentarioMaster();
        
        Transaccion modTransaccion = new Transaccion();
        DbTransaccionMaster modDbTransaccionMaster = new DbTransaccionMaster();
        
        FrmMenuMaster frmMenuMaster = new FrmMenuMaster();
        CtrDirector ctrDirector = new CtrDirector(modDirector, modDbDirectorMaster, frmMenuMaster);
        ctrDirector.iniciar();
        CtrGenero ctrGenero = new CtrGenero(modGenero, modDbGeneroMaster, frmMenuMaster);
        ctrGenero.iniciar();
        CtrUsuario ctrUsuario = new CtrUsuario(modUsuario, modDbUsuarioMaster, frmMenuMaster);
        ctrUsuario.iniciar();
        CtrPelicula ctrPelicula = new CtrPelicula(modPelicula, modDbPeliculaMaster, frmMenuMaster);
        ctrPelicula.iniciar();
        CtrComentario ctrComentario = new CtrComentario(modComentario, modDbComentarioMaster, frmMenuMaster);
        ctrComentario.iniciar();
        CtrTransaccion ctrTransaccion = new CtrTransaccion(modTransaccion, modDbTransaccionMaster, frmMenuMaster);
        ctrTransaccion.iniciar();
        
        
        
        frmMenuMaster.setVisible(true);
        
    }

    private static void abrirVentanaReplica() {
        // Implementa la lógica para abrir la ventana del usuario REPLICA
        // Por ejemplo:
        // FrmVentanaReplica ventanaReplica = new FrmVentanaReplica();
        // ventanaReplica.setVisible(true);
    }
} 