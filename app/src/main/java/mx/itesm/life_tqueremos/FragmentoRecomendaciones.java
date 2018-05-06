package mx.itesm.life_tqueremos;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentoRecomendaciones.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoRecomendaciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoRecomendaciones extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView tvNombre;
    TextView tvRecomendaciones;
    String sNombreEncuesta;
    private PieChart mChart;
    int iResultado=30;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentoRecomendaciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoRecomendaciones.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoRecomendaciones newInstance(String param1, String param2) {
        FragmentoRecomendaciones fragment = new FragmentoRecomendaciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_recomendaciones, container, false);
        tvNombre = (TextView)view.findViewById(R.id.Name);
        tvRecomendaciones = (TextView)view.findViewById(R.id.Recomendaciones);

        sNombreEncuesta=getArguments().getString("encuesta");


        tvNombre.setText(sNombreEncuesta);

        mChart=(PieChart)view.findViewById(R.id.Piechart);
        //mChart = (PieChart) findViewById(R.id.Piechart);
        //findViewById(R.id.Piechart);
        mChart.setBackgroundColor(Color.WHITE);

        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);

        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);

        mChart.setMaxAngle(360f); // HALF CHART
        mChart.setRotationAngle(360f);
        mChart.setCenterTextOffset(0, -20);

        setData(3, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);

        switch (sNombreEncuesta) {
            case "Ocupacional":
                tvRecomendaciones.setText("Centro vida y carrera (CVC).\nParticipar en grupos estudiantiles.\nContacto directo con los directores de carrera.");
                break;

            case "Social":
                tvRecomendaciones.setText("Participar en alguna actividad co-curricular.\nAsistir a curso de comunicación efectiva.\nParticipar en Eventos Tec.");

                break;

            case "Emocional":
                tvRecomendaciones.setText("Actividades en Punto Blanco.\nen Punto Blanco.\nAcercarse a una creencia religiosa o una filosofía de vida.");
                break;

            case "Financiero":
                tvRecomendaciones.setText("Taller de finanzas para no financieros.\nEmpleos en el TEC (Campus JOBS).\nCurso de administración del tiempo.");
                break;

            case "Espiritual":
                tvRecomendaciones.setText("Talleres de desarrollo personal.\nConsejería individual.\nMindfulness.\nActividades de Punto Blanco.\nLínea de apoyo emocional.\n");
                break;

            case "Intelectual":
                tvRecomendaciones.setText("Cátedras académicas.\nMAE´s.\nPasión por la lectura.\nCentro de escritura.");
                break;

            case "Fisico":
                tvRecomendaciones.setText("Participar en actividades deportivas (intramuros, clubs).\nParticipar en actividades de arte y cultura (clases, concursos).\nParticipar en campañas de vacunación y/o promoción de salud.");
                break;
        }

        return view;


    }

    private void setData(int count, float range) {

        ArrayList<PieEntry> values = new ArrayList<PieEntry>();

        if(iResultado<34 && iResultado>=0)
        {
            values.add(new PieEntry(0));
            values.add(new PieEntry(0));
            values.add(new PieEntry(100));

        }
        else if(iResultado<67 && iResultado>=34)
        {
            values.add(new PieEntry(0));
            values.add(new PieEntry(100));
            values.add(new PieEntry(0));
        }
        else
        {
            values.add(new PieEntry(100));
            values.add(new PieEntry(0));
            values.add(new PieEntry(0));
        }



        PieDataSet dataSet = new PieDataSet(values, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        // data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(1f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        mChart.getLegend().setEnabled(false);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        String sResultado=Integer.toString(iResultado);
        SpannableString s = new SpannableString("\n "+sResultado);
        s.setSpan(new RelativeSizeSpan(2.7f), 0, s.length(), 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        // s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        //s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
