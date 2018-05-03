package mx.itesm.life_tqueremos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Android on 14/04/2018.
 */

public class FinancieroFragment extends Fragment implements Encuesta.OnPollReadyListener {
    Encuesta encuesta;
    ArrayList<String> preguntas;
    TextView tvPregunta, tvActual;
    Button resp1, resp2, resp3, resp4;
    int iActual = 0;
    private String sNombreEncuesta;


    /*
        Empty constructor
    */
    public FinancieroFragment(){  }



    /*
        Instanciador con argumentos
    */
    public static FinancieroFragment newInstance(){
        FinancieroFragment pollFragment = new FinancieroFragment();
        Bundle args = new Bundle();
        pollFragment.setArguments(args);
        return pollFragment;
    }



    /*
        Listener para sumar puntos al resultado cuando se contesta pregunta
    */
    interface OnQuestionAnsweredListener{
        void onQuestionAnswered(int points);
    }



    /*
        Esta funcion se llama cuando la encuesta se termina de cargar de la base de datos.
        Sirve para cargar la primera pregunta cuando este lista.
    */
    @Override
    public void onPollReady() {
        tvPregunta.setText(encuesta.getPreguntas().get(iActual));
        tvActual.setText((iActual+1)+" de 10");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        super.onCreateView(inflater, container, savedInstance);
        /*Bundle b = getArguments();
        iMode = b.getInt("modeS");
        iCorrectos = b.getInt("iCorrectos");*/
        View scoreView = inflater.inflate(R.layout.financiero_fragment, container, false);

        sNombreEncuesta = getArguments().getString("encuesta");

        tvPregunta = (TextView) scoreView.findViewById(R.id.tvQuestion);
        tvActual = (TextView) scoreView.findViewById(R.id.tvCount);

//        System.out.println("pollfragment2: "+sNombreEncuesta);


        encuesta = new Encuesta(this, sNombreEncuesta);

        resp1 = (Button) scoreView.findViewById(R.id.Btn_1);
        resp2 = (Button) scoreView.findViewById(R.id.Btn_2);
        resp3 = (Button) scoreView.findViewById(R.id.Btn_3);
        resp4 = (Button) scoreView.findViewById(R.id.Btn_4);

        resp1.setOnClickListener(onClik);
        resp2.setOnClickListener(onClik);
        resp3.setOnClickListener(onClik);
        resp4.setOnClickListener(onClik);

        return scoreView;
    }



    View.OnClickListener onClik = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            switch (v.getId()){

                case R.id.Btn_1:
                case R.id.Btn_2:
                case R.id.Btn_3:
                case R.id.Btn_4:
                    nextQuestion();
                    break;

            }
        }
    };



    public void nextQuestion(){
        iActual++;
        if(iActual == 10)
            iActual = 0;
        tvPregunta.setText(encuesta.getPreguntas().get(iActual));
        tvActual.setText((iActual+1)+" de 10");
    }



    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {

        }
    }
}