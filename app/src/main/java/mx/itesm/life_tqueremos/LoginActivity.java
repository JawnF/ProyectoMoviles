package mx.itesm.life_tqueremos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private Button btnAcceder;
    private TextView tvRegistrar;
    private TextView tvForgotPassword;
    private TextInputEditText tietMail;
    private TextInputEditText tietPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        if(user != null) {
            finish();
            startActivity(new Intent(this, MenuActivity.class));
        }

        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    validate(tietMail.getText().toString(), tietPassword.getText().toString());
                }
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
    }

    private boolean validar() {

        boolean bValid = true;
        String sEmail = tietMail.getText().toString();
        String sPassword = tietPassword.getText().toString();

        if (sEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            tietMail.setError("Introduzca una dirección de correo electrónico válida");
            bValid = false;
        } else {
            tietMail.setError(null);
        }
        if (sPassword.isEmpty() || tietPassword.length() < 4 || tietPassword.length() > 10) {
            tietPassword.setError("Entre 4 y 10 caracteres alfanuméricos");
            bValid = false;
        } else {
            tietPassword.setError(null);
        }
        return bValid;
    }

    private void validate(String userName, String userPassword) {
        progressDialog.setMessage("Iniciando sesión..");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    finish();
                }
                else if (isNetworkAvailable()) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Tu mail o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Revisa tu conectividad a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupUIViews() {
        tietMail = (TextInputEditText) findViewById(R.id.editText_mail);
        tietPassword = (TextInputEditText) findViewById(R.id.editText_password);
        btnAcceder = (Button) findViewById(R.id.button_acceder);
        tvRegistrar = (TextView) findViewById(R.id.textView_registrar);
        tvForgotPassword = (TextView) findViewById(R.id.textView_forgotPassword);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
