package ar.com.despertador.data.model;

public class Alarma {
    private int idAlarma;
    private String nombre;
    private String urlTono;
    private String mensaje;
    private int distanciaActivacion;
    private int volumen;

    public Alarma(){

    }

    public Alarma(int idPersona, String nombre, String urlTono, String mensaje, int distanciaActivacion) {
        this.nombre = nombre;
        this.urlTono = urlTono;
        this.mensaje = mensaje;
        this.distanciaActivacion = distanciaActivacion;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlTono() {
        return urlTono;
    }

    public void setUrlTono(String urlTono) {
        this.urlTono = urlTono;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getDistanciaActivacion() {
        return distanciaActivacion;
    }

    public void setDistanciaActivacion(int distanciaActivacion) {
        this.distanciaActivacion = distanciaActivacion;
    }

    public int getVolumen() {
        return volumen;
    }

    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }
}
