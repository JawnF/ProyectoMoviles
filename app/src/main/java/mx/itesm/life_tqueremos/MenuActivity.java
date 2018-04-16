package mx.itesm.life_tqueremos;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth firebaseAuth;
    public CardView socialCard, emocionalCard, espiritualCard, financieroCard, fisicoCard, ocupacionalCard, intelectualCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth = FirebaseAuth.getInstance();

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        socialCard = (CardView) findViewById(R.id.social_card);
        emocionalCard = (CardView) findViewById(R.id.emocional_card);
        espiritualCard = (CardView) findViewById(R.id.espiritual_card);
        financieroCard = (CardView) findViewById(R.id.financiero_card);
        fisicoCard = (CardView) findViewById(R.id.fisico_card);
        ocupacionalCard = (CardView) findViewById(R.id.ocupacional_card);
        intelectualCard = (CardView) findViewById(R.id.intelectual_card);

        socialCard.setOnClickListener(this);
        emocionalCard.setOnClickListener(this);
        espiritualCard.setOnClickListener(this);
        financieroCard.setOnClickListener(this);
        fisicoCard.setOnClickListener(this);
        ocupacionalCard.setOnClickListener(this);
        intelectualCard.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()) {

            case R.id.social_card:
                intent = new Intent(this, PollActivity.class);
                intent.putExtra("Dimension", "Social");
                startActivity(intent);
                break;


            case R.id.emocional_card:
                intent = new Intent(this, PollActivity.class);
                intent.putExtra("Dimension", "Emocional");
                startActivity(intent);
                break;

            case R.id.espiritual_card:
                intent = new Intent(this, PollActivity.class);
                intent.putExtra("Dimension", "Espiritual");
                startActivity(intent);
                break;

            case R.id.ocupacional_card:
                intent = new Intent(this, PollActivity.class);
                intent.putExtra("Dimension", "Oocupacional");
                startActivity(intent);
                break;

            case R.id.fisico_card:
                intent = new Intent(this, PollActivity.class);
                intent.putExtra("Dimension", "Fisico");
                startActivity(intent);
                break;

            case R.id.intelectual_card:
                intent = new Intent(this, PollActivity.class);
                intent.putExtra("Dimension", "Intelectual");
                startActivity(intent);
                break;

            case R.id.financiero_card:
                intent = new Intent(this, PollActivity.class);
                intent.putExtra("Dimension", "Financiero");
                startActivity(intent);
                break;

        }
    }
}
