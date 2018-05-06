package mx.itesm.life_tqueremos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
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

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, LoadingFragment.newInstance());
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

    @Override
    public void onFragmentReady() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        if(addToBack)
                ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onFechaSelected(Long id) {
        addToBack = true;
        fragment = GraficaFragment.newInstance(this, id);
    }

    @Override
    public void onDimensionSelected(Long id) {

    }
}
