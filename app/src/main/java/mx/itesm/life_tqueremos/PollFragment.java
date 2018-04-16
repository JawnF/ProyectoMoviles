package mx.itesm.life_tqueremos;

import android.content.Intent;
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

public class PollFragment extends Fragment {
    Encuesta encuesta;
    ArrayList<String> preguntas;
    TextView tvPregunta, tvActual;
    Button resp1, resp2, resp3;
    int iActual =0;
    String dimensionName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        super.onCreateView(inflater, container, savedInstance);

        dimensionName = getArguments().getString("Dimension");

        View scoreView = inflater.inflate(R.layout.poll_fragment, container, false);


        tvPregunta = (TextView) scoreView.findViewById(R.id.tvQuestion);
        tvActual = (TextView) scoreView.findViewById(R.id.tvCount);

        encuesta = new Encuesta(dimensionName);

        resp1 = (Button) scoreView.findViewById(R.id.Btn_1);
        resp2 = (Button) scoreView.findViewById(R.id.Btn_2);
        resp3 = (Button) scoreView.findViewById(R.id.Btn_3);

        resp1.setOnClickListener(onClik);
        resp2.setOnClickListener(onClik);
        resp3.setOnClickListener(onClik);

        return scoreView;
    }

    View.OnClickListener onClik = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            switch (v.getId()){

                case R.id.Btn_1:
                case R.id.Btn_2:
                case R.id.Btn_3:
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
        if (encuesta.getReady() == true) {
            tvPregunta.setText(encuesta.getPreguntas().get(iActual));
            tvActual.setText((iActual+1)+" de 10");
        }
    }
}