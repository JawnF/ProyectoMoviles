package mx.itesm.life_tqueremos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    private TextView etYaRegistrado;
    private TextInputEditText tietNombre;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;
    private Button btnRegistrar;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setupUIViews();
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        etYaRegistrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Registrando usuario..");
                progressDialog.show();
                if(validate()) {
                    //upload data to the database
//                    String name = tietNombre.getText().toString();
                    String email = tietEmail.getText().toString().trim();
                    String password = tietPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(tietNombre.getText().toString())
                                        .build();
                                user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                        }
                                    }
                                });
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void setupUIViews() {
        etYaRegistrado = (TextView) findViewById(R.id.editText_yaregistrado);
        tietNombre = (TextInputEditText) findViewById(R.id.editText_nombreRegistro);
        tietEmail = (TextInputEditText) findViewById(R.id.editText_correoRegistro);
        tietPassword = (TextInputEditText) findViewById(R.id.editText_passwordRegistro);
        btnRegistrar = (Button) findViewById(R.id.button_registrar);
    }

    private Boolean validate() {
        Boolean result = false;
        String name =  tietNombre.getText().toString();
        String password = tietPassword.getText().toString();
        String email = tietEmail.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this,"Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }
}
