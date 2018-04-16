package mx.itesm.life_tqueremos;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ScrollView;

public class Dashboard extends AppCompatActivity {

    private CardView cvEspiritual, cvEmocional;
    private PollFragment pollFragment;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cvEspiritual = (CardView) findViewById(R.id.cardView_espiritual);
        cvEmocional = (CardView) findViewById(R.id.cardView_emocional);

        cvEspiritual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, PollActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("encuesta", "Espiritual");
                intent.putExtra("encuesta", "Espiritual");
                startActivity(intent);
            }
        });

        cvEmocional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, PollActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("encuesta", "Espiritual");
                intent.putExtra("encuesta", "Emocional");
                startActivity(intent);
            }
        });

    }
}
