package mx.itesm.life_tqueremos;

import android.content.Intent;
import android.graphics.Color;
import android.media.tv.TvContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class PollActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        Bundle bundle = new Bundle();
        String dimensionName = getIntent().getStringExtra("Dimension");

        bundle.putString("Dimension", dimensionName);
        PollFragment modeSelection = new PollFragment();
        modeSelection.setArguments(bundle);

        getSupportFragmentManager().beginTransaction(). add(R.id.fragment_container, modeSelection).commit();
    }

    public void onBackPressed(){
        PollFragment modeSelection = new PollFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, modeSelection).addToBackStack(null).commit();
    }

}
