/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyecto.movies;

import controlador.CtrComentarioMaster;
import controlador.CtrDirectorMaster;
import controlador.CtrGeneroMaster;
import controlador.CtrPeliculaMaster;
import controlador.CtrTransaccionMaster;
import controlador.CtrUsuarioMaster;
import controlador.CtrComentarioReplica;
import controlador.CtrDirectorReplica;
import controlador.CtrGeneroReplica;
import controlador.CtrPeliculaReplica;
import controlador.CtrTransaccionReplica;
import controlador.CtrUsuarioReplica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.FrmInicioSesion;
import javax.swing.JOptionPane;
import modelo.entities.Comentario;
import modelo.services.DbComentarioMaster;
import modelo.services.DbDirectorMaster;
import modelo.services.DbGeneroMaster;
import modelo.services.DbPeliculaMaster;
import modelo.services.DbTransaccionMaster;
import modelo.services.DbUsuarioMaster;
import modelo.entities.Director;
import modelo.entities.Genero;
import modelo.entities.Pelicula;
import modelo.entities.Transaccion;
import modelo.entities.Usuario;
import modelo.services.DbComentarioReplica;
import modelo.services.DbDirectorReplica;
import modelo.services.DbGeneroReplica;
import modelo.services.DbPeliculaReplica;
import modelo.services.DbTransaccionReplica;
import modelo.services.DbUsuarioReplica;
import vista.FrmMenuMaster;
import vista.FrmMenuReplica;

/**
 *
 * @author TOMMY
 */
public class MOVIES {

    private static final String USUARIO_MASTER = "master";
    private static final String CONTRASENA_MASTER = "master";
    private static final String USUARIO_REPLICA = "replica";
    private static final String CONTRASENA_REPLICA = "replica";
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
                if (usuario.equals(USUARIO_MASTER) && contrasena.equals(CONTRASENA_MASTER)) {
                    // Abre la ventana para el usuario MASTER
                    frmInicioSesion.setVisible(false);
                    abrirVentanaMaster();
                    
                } else if (usuario.equals(USUARIO_REPLICA) && contrasena.equals(CONTRASENA_REPLICA)) {
                    frmInicioSesion.setVisible(false);
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
        CtrDirectorMaster ctrDirector = new CtrDirectorMaster(modDirector, modDbDirectorMaster, frmMenuMaster);
        ctrDirector.iniciar();
        CtrGeneroMaster ctrGenero = new CtrGeneroMaster(modGenero, modDbGeneroMaster, frmMenuMaster);
        ctrGenero.iniciar();
        CtrUsuarioMaster ctrUsuario = new CtrUsuarioMaster(modUsuario, modDbUsuarioMaster, frmMenuMaster);
        ctrUsuario.iniciar();
        CtrPeliculaMaster ctrPelicula = new CtrPeliculaMaster(modPelicula, modDbPeliculaMaster, frmMenuMaster);
        ctrPelicula.iniciar();
        CtrComentarioMaster ctrComentario = new CtrComentarioMaster(modComentario, modDbComentarioMaster, frmMenuMaster);
        ctrComentario.iniciar();
        CtrTransaccionMaster ctrTransaccion = new CtrTransaccionMaster(modTransaccion, modDbTransaccionMaster, frmMenuMaster);
        ctrTransaccion.iniciar();
        frmMenuMaster.setVisible(true);
        
    }

    private static void abrirVentanaReplica() {
        Director modDirector = new Director();
        DbDirectorReplica modDbDirectorReplica = new DbDirectorReplica();
        
        Genero modGenero = new Genero();
        DbGeneroReplica modDbGeneroReplica = new DbGeneroReplica();
        
        Usuario modUsuario = new Usuario();
        DbUsuarioReplica modDbUsuarioReplica = new DbUsuarioReplica();
        
        Pelicula modPelicula = new Pelicula();
        DbPeliculaReplica modDbPeliculaReplica = new DbPeliculaReplica();
        
        Comentario modComentario = new Comentario();
        DbComentarioReplica modDbComentarioReplica = new DbComentarioReplica();
        
        Transaccion modTransaccion = new Transaccion();
        DbTransaccionReplica modDbTransaccionReplica = new DbTransaccionReplica();
        
        FrmMenuReplica frmMenuReplica = new FrmMenuReplica();
        CtrDirectorReplica ctrDirector = new CtrDirectorReplica(modDirector, modDbDirectorReplica, frmMenuReplica);
        ctrDirector.iniciar();
        CtrGeneroReplica ctrGenero = new CtrGeneroReplica(modGenero, modDbGeneroReplica, frmMenuReplica);
        ctrGenero.iniciar();
        CtrUsuarioReplica ctrUsuario = new CtrUsuarioReplica(modUsuario, modDbUsuarioReplica, frmMenuReplica);
        ctrUsuario.iniciar();
        CtrPeliculaReplica ctrPelicula = new CtrPeliculaReplica(modPelicula, modDbPeliculaReplica, frmMenuReplica);
        ctrPelicula.iniciar();
        CtrComentarioReplica ctrComentario = new CtrComentarioReplica(modComentario, modDbComentarioReplica, frmMenuReplica);
        ctrComentario.iniciar();
        CtrTransaccionReplica ctrTransaccion = new CtrTransaccionReplica(modTransaccion, modDbTransaccionReplica, frmMenuReplica);
        ctrTransaccion.iniciar();
        frmMenuReplica.setVisible(true);
    }
} 