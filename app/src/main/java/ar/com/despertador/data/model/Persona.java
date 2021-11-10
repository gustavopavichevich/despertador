package ar.com.despertador.data.model;

public class Persona {
    private int idPersona;
    private String apellido;
    private String nombre;
    private String telefono;
    private String tipo;
    private String email;


    public Persona(int idPersona, String apellido, String nombre, String telefono, String tipo, String email) {
        this.idPersona = idPersona;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipo = tipo;
        this.email = email;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Persona() {
    }


    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
