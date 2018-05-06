package mx.itesm.life_tqueremos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;

/**
 * Created by ivan on 14/04/2018.
 */

public class Grafica extends AppCompatActivity{

    private String[] mActivities = new String[]{"Bienestar", "Social", "Emocional", "Financiero", "Espiritual","Intelectual","Fisico"};
    ListView list;
    private RadarChart rChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafica);

        list=(ListView)findViewById(R.id.list);
        ArrayAdapter Adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mActivities);

        list.setAdapter(Adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(Grafica.this,RecomendacionActivity.class);
                intent.putExtra("Posicion",position);
                intent.putExtra("String",mActivities);
                startActivity(intent);

            }


        });

        rChart = (RadarChart) findViewById(R.id.chart);

        rChart.setWebLineWidth(1f);
        rChart.setWebColor(Color.BLACK);
        rChart.setWebLineWidthInner(1f);
        rChart.setWebColorInner(Color.BLACK);
        rChart.setWebAlpha(100);

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
    }

    public void setData()
    {
        int count=7;
        ArrayList<RadarEntry> entry = new ArrayList<RadarEntry>();

        entry.add(new RadarEntry(10));
        entry.add(new RadarEntry(80));
        entry.add(new RadarEntry(30));
        entry.add(new RadarEntry(50));
        entry.add(new RadarEntry(40));
        entry.add(new RadarEntry(20));
        entry.add(new RadarEntry(60));

        RadarDataSet set = new RadarDataSet(entry,"");
        set.setColor(Color.BLUE);
        set.setFillColor(Color.BLUE);
        set.setDrawFilled(true);
        set.setFillAlpha(180);
        set.setLineWidth(2f);
        set.setDrawHighlightCircleEnabled(true);
        set.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.RED);

        rChart.setData(data);
        Description description = new Description();
        description.setText(" ");
        rChart.setDescription(description);
        rChart.getLegend().setEnabled(false);
        rChart.invalidate();


    }
}
