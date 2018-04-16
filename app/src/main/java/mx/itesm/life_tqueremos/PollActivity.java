package mx.itesm.life_tqueremos;

import android.content.Intent;
import android.graphics.Color;
import android.media.tv.TvContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class PollActivity extends AppCompatActivity {

    private String sNombreEncuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

//        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        sNombreEncuesta = intent.getStringExtra("encuesta");
//        System.out.println("nombre encuesta: "+sNombreEncuesta);


        PollFragment pollFragment = new PollFragment();

//        Bundle bundle = new Bundle();
        Bundle bundle = new Bundle();
        bundle.putString("encuesta", sNombreEncuesta);
        pollFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, pollFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
