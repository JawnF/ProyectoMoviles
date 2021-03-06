package mx.itesm.life_tqueremos;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.google.android.gms.dynamic.SupportFragmentWrapper;

import java.util.ArrayList;

/**
 * Created by ivan on 14/04/2018.
 */

interface OnFragmentReadyListener{
    void onFragmentReady();
}

public class ResultsActivity extends AppCompatActivity implements OnFragmentReadyListener,
                            HistoryFragment.OnFechaSelectedListener,
                            GraficaFragment.OnDimensionSelectedListener{

    Fragment fragment;
    Fragment list;
    boolean addToBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        if(!isNetworkAvailable()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, ConnectionlessFragment.newInstance());
            fragmentTransaction.commit();
        }
        else if (isNetworkAvailable()){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, LoadingFragment.newInstance());
            fragmentTransaction.commit();

            Bundle extras = getIntent().getExtras();
            if(extras != null){
                fragment = GraficaFragment.newInstance(this, extras.getLong("encuesta"));
            }
            else {
                fragment = HistoryFragment.newInstance(this);
                list = fragment;
            }
        }
    }

    @Override
    public void onFragmentReady() {
        getSupportFragmentManager().popBackStack();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        if(addToBack)
                ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onFechaSelected(Long id) {
        if (isNetworkAvailable()) {
            addToBack = true;
            Fragment loading = LoadingFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, loading).addToBackStack(null).commit();
            fragment = GraficaFragment.newInstance(this, id);
        }
        else if (!isNetworkAvailable()) {
            Toast.makeText(this,"Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDimensionSelected(String dim, int val) {
        FragmentoRecomendaciones fragmento = FragmentoRecomendaciones.newInstance(dim, val);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragmento);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
