package ar.com.despertador.data.model;

public class Usuario {

    private int idUsuario;
    private String email;
    private String contrasena;

    public Usuario() {
    }

    public Usuario(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
    }

    public Usuario(int idUsuario, String email, String contrasena) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.contrasena = contrasena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasena;
    }

    public void setContrasenia(String contrasena) {
        this.contrasena = contrasena;
    }
}
