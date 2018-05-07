package mx.itesm.life_tqueremos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by ivan on 05/05/2018.
 */

public class RecomendacionActivity extends AppCompatActivity {

    int iPosition;
    String[] String;
    String sNombreEncuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendacion);

        Intent intent = getIntent();
        iPosition= intent.getIntExtra("Posicion",0);
        String=intent.getStringArrayExtra("String");

        sNombreEncuesta=String[iPosition];
        Toast.makeText(RecomendacionActivity.this, Integer.toString(iPosition), Toast.LENGTH_SHORT).show();
        Toast.makeText(RecomendacionActivity.this, sNombreEncuesta, Toast.LENGTH_SHORT).show();

        FragmentoRecomendaciones frRec = new FragmentoRecomendaciones();

        Bundle bundle = new Bundle();
        bundle.putString("encuesta", sNombreEncuesta);
        frRec.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, frRec);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
