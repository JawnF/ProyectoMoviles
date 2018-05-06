package mx.itesm.life_tqueremos;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jawn on 5/05/18.
 */

public class FechaAdapter extends ArrayAdapter<String> {

    HistoryFragment.OnFechaSelectedListener mListener;

    public FechaAdapter(@NonNull Context context, ArrayList<String> list) {
        super(context, 0, list);
        Activity activity;

        if(context instanceof Activity){
            activity = (Activity) context;
            try{
                mListener = (HistoryFragment.OnFechaSelectedListener) activity;
            } catch (ClassCastException e){
                throw new ClassCastException(activity.toString()
                        + " must implement OnFechaSelectedListener");
            }
        }

    }


    private Context context;

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fecha_row, parent, false);
        }

        final int pos = position;

        TextView tvFecha = convertView.findViewById(R.id.text_fecha);
        tvFecha.setText(getItem(position));
        CardView cardView = convertView.findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFechaSelected(getItem(pos));
            }
        });

        return convertView;

    }
}
