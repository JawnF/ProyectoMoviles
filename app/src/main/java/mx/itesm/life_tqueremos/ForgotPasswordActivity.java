package mx.itesm.life_tqueremos;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextView tvRegresar;
    private Button btnSendMail;
    private TextInputEditText tietMail;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tvRegresar = (TextView) findViewById(R.id.textView_forgotPassword2);
        btnSendMail = (Button) findViewById(R.id.button_sendMail);
        tietMail = (TextInputEditText) findViewById(R.id.editText_forgotPassword);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        tvRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = tietMail.getText().toString().trim();

                if (userEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
//                    Toast.makeText(ForgotPasswordActivity.this, "Ingrese una cuenta de correo registrada", Toast.LENGTH_SHORT).show();
                    tietMail.setError("Introduzca una dirección de correo electrónico válida");
                }
                else {
                    progressDialog.setMessage("Verificando..");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                progressDialog.dismiss();
                                onBackPressed();
                                Toast.makeText(ForgotPasswordActivity.this, "Correo de cambio de contraseña enviado", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(ForgotPasswordActivity.this, "Error al enviar correo de cambio de contraseña", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
