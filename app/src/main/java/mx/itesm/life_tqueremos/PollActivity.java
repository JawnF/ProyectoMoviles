package mx.itesm.life_tqueremos;

import android.content.Intent;
import android.graphics.Color;
import android.media.tv.TvContract;
import android.os.Debug;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PollActivity extends AppCompatActivity implements PollFragment.OnPollAnsweredListener,
                                                                Encuesta.OnPollReadyListener  {

    private String sNombreEncuesta;
    Encuesta encuesta;

    @Override
    public void onPollAnswered(int points) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .child("active").child(sNombreEncuesta).setValue(points);
        finish();
    }

    //    Esta funcion se llama cuando la encuesta se termina de cargar de la base de datos.
    //    Sirve para cargar la primera pregunta cuando este lista.
    @Override
    public void onPollReady() {

        if(sNombreEncuesta.equals("Financiero")) {
            Log.d("Nombre", "Debi entrar a financiero");
            FinancieroFragment financieroFragment = new FinancieroFragment();
            Bundle bundle = new Bundle();
            bundle.putString("encuesta", sNombreEncuesta);
            financieroFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, financieroFragment);
            fragmentTransaction.commit();
        }
        else {
            Log.d("Nombre", "No entre a financiero");
            PollFragment pollFragment = PollFragment.newInstance(encuesta);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, pollFragment);
            fragmentTransaction.commit();
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);


//        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        sNombreEncuesta = intent.getStringExtra("encuesta");
        encuesta = new Encuesta(this, sNombreEncuesta);
//        System.out.println("nombre encuesta: "+sNombreEncuesta);

        Log.d("Nombre encuesta", sNombreEncuesta);


    }

}
