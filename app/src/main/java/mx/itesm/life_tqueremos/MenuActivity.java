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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth firebaseAuth;
    private CardView cvEspiritual, cvEmocional;
    TextView tvNombreUsuario;
    TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseAuth = FirebaseAuth.getInstance();

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        View headerView = navigationView.getHeaderView(0);
        tvNombreUsuario = (TextView) headerView.findViewById(R.id.textView_nombre);
        tvEmail = (TextView) headerView.findViewById(R.id.textView_mail);

        tvNombreUsuario.setText(user.getDisplayName());
        tvEmail.setText(user.getEmail());




        cvEspiritual = (CardView) findViewById(R.id.cardView_espiritual);
        cvEmocional = (CardView) findViewById(R.id.cardView_emocional);

        cvEspiritual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PollActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("encuesta", "Espiritual");
                intent.putExtra("encuesta", "Espiritual");
                startActivity(intent);
            }
        });

        cvEmocional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PollActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("encuesta", "Espiritual");
                intent.putExtra("encuesta", "Emocional");
                startActivity(intent);
            }
        });

//        PollDimension pollDimension = new PollDimension("what", false);
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference mRef = firebaseDatabase.getReference().child("s");
//        mRef.child("um").setValue(pollDimension);
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
}
