/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author aseba
 */
public class Sucursales {
    private int sucursalId;
    private int matrizId;
    private String ubicacion;
    private int gerenteId;
    private String datosLog;
    private String evenPromo;

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public int getMatrizId() {
        return matrizId;
    }

    public void setMatrizId(int matrizId) {
        this.matrizId = matrizId;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getGerenteId() {
        return gerenteId;
    }

    public void setGerenteId(int gerenteId) {
        this.gerenteId = gerenteId;
    }

    public String getDatosLog() {
        return datosLog;
    }

    public void setDatosLog(String datosLog) {
        this.datosLog = datosLog;
    }

    public String getEvenPromo() {
        return evenPromo;
    }

    public void setEvenPromo(String evenPromo) {
        this.evenPromo = evenPromo;
    }    
}
