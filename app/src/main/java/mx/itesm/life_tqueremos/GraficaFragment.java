package mx.itesm.life_tqueremos;
/**
 * Created by Android on 03/04/2018.
 */


//////////////////////////////////////////////////////////
//Clase: <DetalleUso>
// Descripción:<Fragmento que muestra los detalles del uso>
// Autor: <Adrián Peña>  <A00816456>
// Fecha de creación: 03/04/2018
// Fecha de última modificación: 06/04/2018
//////////////////////////////////////////////////////////

import android.annotation.SuppressLint;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mx.itesm.life_tqueremos.R;

/**
 * Created by Android on 26/03/2018.
 */

public class GraficaFragment extends Fragment {

    private String[] mActivities = new String[]{"Ocupacional", "Social", "Emocional", "Financiero", "Espiritual","Intelectual","Fisico"};
    ListView list;
    private RadarChart rChart;
    Resultados resultados;
    Long id;

    public static GraficaFragment newInstance(OnFragmentReadyListener listener, long id){
        GraficaFragment fragment = new GraficaFragment();
        Resultados resultados = new Resultados(listener, id);
        Bundle args = new Bundle();
        args.putLong("id",id);
        args.putParcelable("results", resultados);
        fragment.setArguments(args);
        return fragment;
    }

    interface OnDimensionSelectedListener{
        void onDimensionSelected(Long id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        super.onCreateView(inflater, container, savedInstance);


        View graficaView = inflater.inflate(R.layout.grafica_fragment, container, false);

        list=(ListView)graficaView.findViewById(R.id.list);

        ArrayAdapter Adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,mActivities);

        list.setAdapter(Adapter);

        rChart = (RadarChart) graficaView.findViewById(R.id.chart);

        rChart.setWebLineWidth(1f);
        rChart.setWebColor(Color.BLACK);
        rChart.setWebLineWidthInner(1f);
        rChart.setWebColorInner(Color.BLACK);
        rChart.setWebAlpha(100);

        id = getArguments().getLong("id");
        resultados = getArguments().getParcelable("results");

        setData();

        XAxis xAxis = rChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.BLACK);

        YAxis yAxis = rChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);
        return graficaView;

    }

    public void setData()
    {
        int count=7;
        ArrayList<RadarEntry> entry = new ArrayList<RadarEntry>();

        entry.add(new RadarEntry(resultados.getOcupacional()));
        entry.add(new RadarEntry(resultados.getSocial()));
        entry.add(new RadarEntry(resultados.getEmocional()));
        entry.add(new RadarEntry(resultados.getFinanciero()));
        entry.add(new RadarEntry(resultados.getEspiritual()));
        entry.add(new RadarEntry(resultados.getIntelectual()));
        entry.add(new RadarEntry(resultados.getFisico()));

        RadarDataSet set = new RadarDataSet(entry,"Bienestar");
        set.setColor(Color.parseColor("#64bf5d"));
        set.setFillColor(Color.parseColor("#6edd66"));
        set.setDrawFilled(true);
        set.setFillAlpha(180);
        set.setLineWidth(2f);
        set.setDrawHighlightCircleEnabled(true);
        set.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(true);
        data.setValueTextColor(Color.BLACK);

        rChart.setData(data);
        Description description = new Description();
        description.setText(" ");
        rChart.setDescription(description);
        rChart.setTouchEnabled(false);
        rChart.getLegend().setEnabled(false);
        rChart.invalidate();
    }

}
