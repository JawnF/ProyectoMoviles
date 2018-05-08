package mx.itesm.life_tqueremos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

public class EditarUsuarioActivity extends AppCompatActivity {

    private Button btnCambiarNombre;
    private TextInputEditText tietNombre;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        btnCambiarNombre = (Button) findViewById(R.id.button_cambiarNombre);
        tietNombre = (TextInputEditText) findViewById(R.id.editText_editarNombre);
        progressDialog = new ProgressDialog(this);

        btnCambiarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()) {
                    progressDialog.setMessage("Cambiando nombre de usuario");
                    progressDialog.show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(tietNombre.getText().toString())
                            .build();
                    user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(EditarUsuarioActivity.this, "Cambio de nombre exitoso", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(EditarUsuarioActivity.this, "Error al cambiar su nombre de usuario", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(getApplicationContext(), UsuarioActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validar() {

        boolean bValid = true;
        String sNombre = tietNombre.getText().toString();

        if (sNombre.isEmpty() || tietNombre.length() > 30) {
            tietNombre.setError("Cheque que el campo de nombre no esté vacio o exceda 30 caracteres");
            bValid = false;
        } else {
            tietNombre.setError(null);
        }
        return bValid;
    }
}
