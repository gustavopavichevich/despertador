package ar.com.despertador.data.model;

public class Ubicacion {
    private int idUbicacion;
    private String poi;

    public Ubicacion() {
    }


    public Ubicacion(int idUbicacion, String poi) {
        this.idUbicacion = idUbicacion;
        this.poi = poi;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }
}
