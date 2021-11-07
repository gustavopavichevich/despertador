package ar.com.despertador.data.model;

public class Usuario {
    private int idUsuario;

    private int idPersona;

    private String email;
    private String contrasena;

    public Usuario() {
    }

    public Usuario(int idPersona, String email, String contrasena) {
        this.idPersona = idPersona;
        this.email = email;
        this.contrasena = contrasena;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
