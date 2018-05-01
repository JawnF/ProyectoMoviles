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

public class PollActivity extends AppCompatActivity implements PollFragment.OnQuestionAnsweredListener {

    private String sNombreEncuesta;

    @Override
    public void onQuestionAnswered(int points) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

//        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        sNombreEncuesta = intent.getStringExtra("encuesta");
//        System.out.println("nombre encuesta: "+sNombreEncuesta);

        Log.d("Nombre encuesta", sNombreEncuesta);

        if(sNombreEncuesta.equals("Financiero")) {
            Log.d("Nombre", "Debi entrar a financiero");
            FinancieroFragment financieroFragment = new FinancieroFragment();
            Bundle bundle = new Bundle();
            bundle.putString("encuesta", sNombreEncuesta);
            financieroFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, financieroFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else {
            Log.d("Nombre", "No entre a financiero");
            PollFragment pollFragment = new PollFragment();
            Bundle bundle = new Bundle();
            bundle.putString("encuesta", sNombreEncuesta);
            pollFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, pollFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

//        Bundle bundle = new Bundle();


    }

}
