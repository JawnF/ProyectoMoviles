package mx.itesm.life_tqueremos;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Android on 14/04/2018.
 */

public class Encuesta implements Parcelable {
    private String nombre;
    private ArrayList<String> preguntas = new ArrayList<>();
    private int cantPregs;
    private boolean ready;

    public Encuesta(String name){
        this.nombre = name;
        cantPregs = 0;
        ready = false;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dimensions").child(nombre).child("preguntas");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ArrayList<String> preguntas = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    preguntas.add(postSnapshot.getValue(String.class));
                    Log.d("PRUEBA2", "Value is: " + preguntas.get(cantPregs));
                    cantPregs++;
                    ready = true;
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("PRUEBA", "Failed to read value.", error.toException());
            }
        });
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public ArrayList<String> getPreguntas(){
        return preguntas;
    }

    public boolean getReady(){
        return ready;
    }

    public Encuesta(Parcel in){
        nombre = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Encuesta createFromParcel(Parcel in) {
            return new Encuesta(in);
        }

        public Encuesta[] newArray(int size) {
            return new Encuesta[size];
        }
    };
}