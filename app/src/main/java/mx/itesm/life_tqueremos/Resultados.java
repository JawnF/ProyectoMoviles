package mx.itesm.life_tqueremos;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
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

public class Resultados implements Parcelable {
    private int iEspiritual;
    private int iOcupacional;
    private int iSocial;
    private int iEmocional;
    private int iFisico;
    private int iFinanciero;
    private int iIntelectual;


    public Resultados(final OnFragmentReadyListener listener, long id) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users").child(uid).child("entries")
                .child(String.valueOf(id));
        iEspiritual = 0;
        iOcupacional = 0;
        iSocial = 0;
        iEmocional = 0;
        iFisico = 0;
        iFinanciero = 0;
        iIntelectual = 0;
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Ocupacional").getValue()!=null)
                    iOcupacional = Integer.valueOf(dataSnapshot.child("Ocupacional").getValue(String.class));
                Log.d("PRUEBA2", "Ocupacional es: " + iOcupacional);

                if(dataSnapshot.child("Espiritual").getValue()!=null)
                    iEspiritual = Integer.valueOf(dataSnapshot.child("Espiritual").getValue(String.class));
                Log.d("PRUEBA2", "Espiritual es: " + iEspiritual);

                if(dataSnapshot.child("Social").getValue()!=null)
                    iSocial = Integer.valueOf(dataSnapshot.child("Social").getValue(String.class));
                Log.d("PRUEBA2", "Social es: " + iSocial);

                if(dataSnapshot.child("Emocional").getValue()!=null)
                    iEmocional = Integer.valueOf(dataSnapshot.child("Emocional").getValue(String.class));
                Log.d("PRUEBA2", "Emocional es: " + iEmocional);

                if(dataSnapshot.child("Físico").getValue()!=null)
                    iFisico = Integer.valueOf(dataSnapshot.child("Físico").getValue(String.class));
                Log.d("PRUEBA2", "Fisico es: " + iFisico);

                if(dataSnapshot.child("Intelectual").getValue()!=null)
                    iIntelectual = Integer.valueOf(dataSnapshot.child("Intelectual").getValue(String.class));
                Log.d("PRUEBA2", "Intelectual es: " + iIntelectual);

                if(dataSnapshot.child("Financiero").getValue()!=null)
                    iFinanciero = Integer.valueOf(dataSnapshot.child("Financiero").getValue(String.class));
                Log.d("PRUEBA2", "Financiero es: " + iFinanciero);

                listener.onFragmentReady();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("PRUEBA", "Failed to read value.", error.toException());
            }
        });
    }

    public void setSocial(int param){
        iSocial = param;
    }

    public int getSocial(){
        int temp = 100 * iSocial / 20;
        return temp;
    }

    public void setOcupacional(int param){
        iOcupacional = param;
    }

    public int getOcupacional(){
        int temp = 100 * iOcupacional / 20;
        return temp;
    }

    public void setFisico(int param){
        iFisico = param;
    }

    public int getFisico(){
        int temp = 100 * iFisico / 20 ;
        return temp;
    }

    public void setEspiritual(int param){
        iEspiritual = param;
    }

    public int getEspiritual(){
        int temp = 100 * iEspiritual / 20;
        return temp;
    }

    public void setIntelectual(int param){
        iIntelectual = param;
    }

    public int getIntelectual(){
        int temp = 100 * iIntelectual / 20;
        return temp;
    }

    public void setFinanciero(int param){
        iFinanciero = param;
    }

    public int getFinanciero(){
        int temp = 30 - iFinanciero;
        temp = 100 * temp / 30 ;
        return temp;
    }

    public void setEmocional(int param){
        iEmocional = param;
    }

    public int getEmocional(){
        int temp = 100 * iEmocional / 20;
        return temp;
    }


    public Resultados(Parcel in){
        iSocial = in.readInt();
        iOcupacional = in.readInt();
        iFinanciero = in.readInt();
        iFisico = in.readInt();
        iEmocional = in.readInt();
        iIntelectual = in.readInt();
        iEspiritual = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iSocial);
        dest.writeInt(iOcupacional);
        dest.writeInt(iFisico);
        dest.writeInt(iEmocional);
        dest.writeInt(iIntelectual);
        dest.writeInt(iFinanciero);
        dest.writeInt(iEspiritual);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Resultados createFromParcel(Parcel in) {
            return new Resultados(in);
        }

        public Resultados[] newArray(int size) {
            return new Resultados[size];
        }
    };
}