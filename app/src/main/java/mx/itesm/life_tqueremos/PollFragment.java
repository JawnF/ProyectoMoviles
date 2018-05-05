package mx.itesm.life_tqueremos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Android on 14/04/2018.
 */

public class PollFragment extends Fragment {
    Encuesta encuesta;
    ArrayList<String> preguntas;
    TextView tvPregunta, tvActual;
    Button resp1, resp2, resp3, back;
    int iActual = 0;
    private String sNombreEncuesta;
    private OnPollAnsweredListener mCallback;
    private int score = 0;
    private Stack<Integer> last_answers;
    ProgressBar progress;

    private final int POINTS_BUENO = 2;
    private final int POINTS_NORMAL = 1;
    private final int POINTS_MALO = 0;



    // Empty constructor
    public PollFragment(){  }



    // Instanciador con argumentos
    public static PollFragment newInstance(Encuesta encuesta){
        PollFragment pollFragment = new PollFragment();
        Bundle args = new Bundle();
        args.putParcelable("encuesta", encuesta);
        pollFragment.setArguments(args);
        return pollFragment;
    }



    // Listener para sumar puntos al resultado cuando se contesta pregunta
    interface OnPollAnsweredListener{
        void onPollAnswered(int points);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;

        if(context instanceof Activity){
            activity = (Activity) context;
            try{
                mCallback = (OnPollAnsweredListener) activity;
            } catch (ClassCastException e){
                throw new ClassCastException(activity.toString()
                        + " must implement OnPollAnsweredListener");
            }
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        last_answers = new Stack<Integer>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        super.onCreateView(inflater, container, savedInstance);
        /*Bundle b = getArguments();
        iMode = b.getInt("modeS");
        iCorrectos = b.getInt("iCorrectos");*/
        View scoreView = inflater.inflate(R.layout.poll_fragment, container, false);

        encuesta = getArguments().getParcelable("encuesta");

        tvPregunta = (TextView) scoreView.findViewById(R.id.tvQuestion);
        tvActual = (TextView) scoreView.findViewById(R.id.tvCount);

        progress = scoreView.findViewById(R.id.progress);
        tvPregunta.setText(encuesta.getPreguntas().get(iActual));
        tvActual.setText((iActual+1)+" de 10");

        resp1 = (Button) scoreView.findViewById(R.id.Btn_1);
        resp2 = (Button) scoreView.findViewById(R.id.Btn_2);
        resp3 = (Button) scoreView.findViewById(R.id.Btn_3);
        back = (Button) scoreView.findViewById(R.id.Btn_anterior);

        resp1.setOnClickListener(onClik);
        resp2.setOnClickListener(onClik);
        resp3.setOnClickListener(onClik);
        back.setOnClickListener(onClik);

        return scoreView;
    }



    View.OnClickListener onClik = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.Btn_1:
                    score += POINTS_BUENO;
                    last_answers.push(POINTS_BUENO);
                    nextQuestion();
                    break;
                case R.id.Btn_2:
                    score += POINTS_NORMAL;
                    last_answers.push(POINTS_NORMAL);
                    nextQuestion();
                    break;
                case R.id.Btn_3:
                    score += POINTS_MALO;
                    last_answers.push(POINTS_MALO);
                    nextQuestion();
                    break;
                case R.id.Btn_anterior:
                    prevQuestion();
                    score -= last_answers.pop();
                    break;
            }
        }
    };



    private void prevQuestion(){
        if(--iActual == 0)
            back.setEnabled(false);
        progress.setProgress(iActual);
        tvPregunta.setText(encuesta.getPreguntas().get(iActual));
        tvActual.setText((iActual+1)+" de 10");
    }



    public void nextQuestion(){
        if(++iActual == 10) {
            mCallback.onPollAnswered(score);
            return;
        }
        progress.setProgress(iActual);
        tvPregunta.setText(encuesta.getPreguntas().get(iActual));
        tvActual.setText((iActual+1)+" de 10");
        back.setEnabled(true);
    }
}