package mx.itesm.life_tqueremos;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.function.Consumer;


public class HistoryFragment extends ListFragment {

    interface OnFechaSelectedListener{
        void onFechaSelected(String fecha);
    }

    ArrayList<String> list;

    public HistoryFragment() {
        // Required empty public constructor
    }


    public static HistoryFragment newInstance(final OnFragmentReadyListener listener) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final ArrayList<String> tList = new ArrayList<String>();
        rootRef.child("users").child(uid).child("past").orderByKey()
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> s = dataSnapshot.getChildren();
                for (DataSnapshot snapshot : s) {
                    tList.add(0,(String)snapshot.getValue());
                    // Para guardar fecha usar new Date().toLocaleString();
                }
                listener.onFragmentReady();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("list", tList);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getStringArrayList("list");
            setListAdapter(new FechaAdapter(getContext(), list));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        return view;
    }
}
