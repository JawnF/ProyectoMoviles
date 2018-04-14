package mx.itesm.life_tqueremos;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private Button btnAcceder;
    private TextView tvRegistrar;
    private TextInputEditText tietMail;
    private TextInputEditText tietPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tietMail = (TextInputEditText) findViewById(R.id.editText_mail);
        tietPassword = (TextInputEditText) findViewById(R.id.editText_password);
        btnAcceder = (Button) findViewById(R.id.button_acceder);
        tvRegistrar = (TextView) findViewById(R.id.textView_registrar);

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
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
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
}
