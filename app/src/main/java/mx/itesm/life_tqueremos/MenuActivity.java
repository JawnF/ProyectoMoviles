package mx.itesm.life_tqueremos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


interface DoneListener{
    void done();
}

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                            DoneListener, DoneFragment.OnStartNewListener{

    class DoneListenerClass implements DoneListener{
        DoneListener callback;
        DoneListenerClass(DoneListener callback){
            this.callback = callback;
        }
        public void done(){
            callback.done();
        }
    }


    private int iCount = 0;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth firebaseAuth;
    private CardView cvEspiritual, cvEmocional, cvOcupacional, cvFisico, cvFinanciero, cvIntelectual, cvSocial;
    DoneListenerClass listener;
    NavigationView navigationView;
    private TextView tvNombre;
    private TextView tvMail;
    private View headerView;
    String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listener = new DoneListenerClass(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address etc
             name = user.getDisplayName();
             email = user.getEmail();
        }

        firebaseAuth = FirebaseAuth.getInstance();

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        headerView = navigationView.getHeaderView(0);
        tvNombre = (TextView) headerView.findViewById(R.id.textView_nombre);
        tvMail = (TextView) headerView.findViewById(R.id.textView_mail);

        tvNombre.setText(name);
        tvMail.setText(email);

        cvSocial = (CardView) findViewById(R.id.cardView_social);
        cvEspiritual = (CardView) findViewById(R.id.cardView_espiritual);
        cvOcupacional = (CardView) findViewById(R.id.cardView_ocupacional);
        cvFisico = (CardView) findViewById(R.id.cardView_fisico);
        cvIntelectual = (CardView) findViewById(R.id.cardView_intelectual);
        cvEmocional = (CardView) findViewById(R.id.cardView_emocional);
        cvFinanciero = (CardView) findViewById(R.id.cardView_Financiero);



        cvOcupacional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PollActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("encuesta", "Espiritual");
                intent.putExtra("encuesta", "Ocupacional");
                startActivity(intent);
            }
        });

        cvSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PollActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("encuesta", "Espiritual");
                intent.putExtra("encuesta", "Social");
                startActivity(intent);
            }
        });

        cvFisico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PollActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("encuesta", "Espiritual");
                intent.putExtra("encuesta", "Físico");
                startActivity(intent);
            }
        });

        cvIntelectual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PollActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("encuesta", "Espiritual");
                intent.putExtra("encuesta", "Intelectual");
                startActivity(intent);
            }
        });

        cvFinanciero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PollActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("encuesta", "Espiritual");
                intent.putExtra("encuesta", "Financiero");
                startActivity(intent);
            }
        });

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

        final View.OnClickListener done = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Dimensión ya contestada", Toast.LENGTH_SHORT).show();
            }
        };

        ArrayList<String> completed = new ArrayList<String>();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootRef.child("users").child(uid).child("active")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        switch (dataSnapshot.getKey()){
                            case "Ocupacional":
                                cvOcupacional.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                                cvOcupacional.setOnClickListener(done);
                                break;
                            case "Social":
                                cvSocial.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                                cvSocial.setOnClickListener(done);
                                break;
                            case "Físico":
                                cvFisico.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                                cvFisico.setOnClickListener(done);
                                break;
                            case "Intelectual":
                                cvIntelectual.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                                cvIntelectual.setOnClickListener(done);
                                break;
                            case "Financiero":
                                cvFinanciero.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                                cvFinanciero.setOnClickListener(done);
                                break;
                            case "Espiritual":
                                cvEspiritual.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                                cvEspiritual.setOnClickListener(done);
                                break;
                            case "Emocional":
                                cvEmocional.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                                cvEmocional.setOnClickListener(done);
                                break;
                        }

                        if(++iCount == 7){
                            listener.done();
                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
        mDrawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.resultados:
                startActivity(new Intent (this, ResultsActivity.class));
                break;
            case R.id.perfil:
                finish();
                startActivity(new Intent(this, UsuarioActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onStartNewInteraction(long id) {
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        intent.putExtra("encuesta", id);
        Intent self = getIntent();
        this.finish();
        startActivity(self);
        startActivity(intent);
    }

    @Override
    public void done() {
        DoneFragment fragment = DoneFragment.newInstance();
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.menu_container, fragment, "DONE_SCREEN").commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawers();
        else
            moveTaskToBack(true);
    }
}
