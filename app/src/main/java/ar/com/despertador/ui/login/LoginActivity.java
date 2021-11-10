package ar.com.despertador.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;

import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import ar.com.despertador.AgregarCuentaActivity;
import ar.com.despertador.AgregarCuentaActivity;
import ar.com.despertador.MapsActivity;
import ar.com.despertador.R;

//import ar.com.despertador.databinding.ActivityLoginBinding;
import ar.com.despertador.ui.login.LoginViewModel;
import ar.com.despertador.ui.login.LoginViewModelFactory;


public class LoginActivity extends AppCompatActivity {

  //  private LoginViewModel loginViewModel;
 //   private Button agregar;
  private EditText _email, _contrasenia;
 //   private ActivityLoginBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);
        _email = (EditText)findViewById(R.id.username);
        _contrasenia = (EditText)findViewById(R.id.password);
      //  this.setTitle("llega");
     /*   agregar = (Button) findViewById(R.id.login2);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AgregarCuentaActivity.class);
               startActivity(intent);
            }
        });*/

    }
      /*  loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

       // final EditText usernameEditText = binding.username;
        //final EditText passwordEditText = binding.password;
        //final Button loginButton = binding.btnAceptar;

                //findViewById(R.id.login2);
        //final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
          //      loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
          //          usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
            //        passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                //loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

*//*        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
         //      loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
          //              passwordEditText.getText().toString());
            }
        };*//*
    //    usernameEditText.addTextChangedListener(afterTextChangedListener);
    //    passwordEditText.addTextChangedListener(afterTextChangedListener);
    //    passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

 *//*           @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });
*//*

        

        //loginButton.setOnClickListener(new View.OnClickListener() {
            //@Override
        *//*    public void onClick(View v) {
              *//**//*  loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());*//**//*
            }*//*
      //  });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
*/
    public void Agregar (View view)
    {
        Intent agregar = new Intent( this, AgregarCuentaActivity.class);
        startActivity(agregar);
    }
    //Método para el inicio de sesion
    public void IniciarSesion(View view){
        Intent principal = new Intent( this, MapsActivity.class);
        principal.putExtra("email",_email.getText().toString());
        startActivity(principal);
        /*AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String usuario = _usuario.getText().toString();
        String contrasenia = _contrasenia.getText().toString();

        boolean result = false;

        if(!usuario.isEmpty() && !contrasenia.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select nombre, usuario from usuarios where usuario ='" + usuario + "' and contrasenia='" + contrasenia + "'", null);

            if(fila.moveToFirst()){
                //aca abro la pantalla del menu "Mi cuenta" y "Parqueos"
                //Toast.makeText(this, "Sesion Exitosa", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
                Intent principal = new Intent( this, Principal.class);
                principal.putExtra("nombre",fila.getString(0));
                principal.putExtra("email",fila.getString(1));
                startActivity(principal);
            } else {
                Toast.makeText(this, "Usuario o Contaseña erroneos", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }

        } else {
            Toast.makeText(this, "Debes introducir los datos, son obligatorios", Toast.LENGTH_SHORT).show();
        }*/
    }
}