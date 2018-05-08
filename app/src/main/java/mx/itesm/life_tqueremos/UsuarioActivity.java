package mx.itesm.life_tqueremos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioActivity extends AppCompatActivity {

    private String name, email;
    private TextView tvNombre;
    private TextView tvMail;
    private LinearLayout llEditarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();
            email = user.getEmail();
        }

        setupUIViews();

        tvNombre.setText(name);
        tvMail.setText(email);

        llEditarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditarUsuarioActivity.class));
                finish();
            }
        });

    }

    public void setupUIViews() {
        tvNombre = (TextView) findViewById(R.id.textView_perfilNombre);
        tvMail = (TextView) findViewById(R.id.textView_perfilMail);
        llEditarUsuario = (LinearLayout) findViewById(R.id.editarNombre);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MenuActivity.class));
        finish();
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
}
