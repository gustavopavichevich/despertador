package ar.com.despertador;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class Validaciones {
    public Validaciones() {
    }

    //metodo para validar si es un valor numerico
    public  boolean isNumeric(String cadena) {
        boolean resultado=false;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    //metodo para validar si es un email
    public  boolean isEmail(String cadena) {
        if (Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            return true;
        } else {
            return false;
        }
    }

    //metodo para validar si editext esta vacio
    public  boolean Vacio(EditText campo){
        String dato = campo.getText().toString().trim();
        if(TextUtils.isEmpty(dato)){
            campo.setError("Campo Requerido");
            campo.requestFocus();
            return true;
        }
        else{
            return false;
        }
    }
}