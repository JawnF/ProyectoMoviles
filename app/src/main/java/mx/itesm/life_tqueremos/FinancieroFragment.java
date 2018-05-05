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

public class FinancieroFragment extends PollFragment {
    Button resp4;

    private final int POINTS_BUENO = 0;
    private final int POINTS_NORMAL = 1;
    private final int POINTS_MALO = 2;
    private final int POINTS_PESIMO = 3;

    /*
        Empty constructor
    */
    public FinancieroFragment(){  }



    /*
        Instanciador con argumentos
    */

    public static FinancieroFragment newInstance(Encuesta encuesta){
        FinancieroFragment pollFragment = new FinancieroFragment();
        Bundle args = new Bundle();
        args.putParcelable("encuesta", encuesta);
        pollFragment.setArguments(args);
        return pollFragment;
    }


    protected View getView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(R.layout.financiero_fragment, container, false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View scoreView = super.onCreateView(inflater, container, savedInstance);

        resp4 = (Button) scoreView.findViewById(R.id.Btn_4);

        resp1.setOnClickListener(onClik);
        resp2.setOnClickListener(onClik);
        resp3.setOnClickListener(onClik);
        resp4.setOnClickListener(onClik);

        return scoreView;
    }



    private View.OnClickListener onClik = new View.OnClickListener(){
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
                case R.id.Btn_4:
                    score += POINTS_PESIMO;
                    last_answers.push(POINTS_PESIMO);
                    nextQuestion();
                    break;
                case R.id.Btn_anterior:
                    prevQuestion();
                    score -= last_answers.pop();
                    break;
            }
        }
    };

}