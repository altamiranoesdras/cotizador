package com.proyecto.ipm.android.curso.cotizador.objetos;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by USUARIO on 13/09/2016.
 */

public class Cartera implements Parcelable {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private float taza;
    @Getter @Setter
    private float motoMax;
    @Getter @Setter
    private int plazoMax;
    @Getter @Setter
    private String codeudor;

    public Cartera(){}

    public Cartera(Integer id,String nombre, float taza, float motoMax, int plazoMax, String codeudor) {
        this.id = id;
        this.nombre = nombre;
        this.taza = taza;
        this.motoMax = motoMax;
        this.plazoMax = plazoMax;
        this.codeudor = codeudor;
    }

    protected Cartera(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        taza = in.readFloat();
        motoMax = in.readFloat();
        plazoMax = in.readInt();
        codeudor = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeFloat(taza);
        dest.writeFloat(motoMax);
        dest.writeInt(plazoMax);
        dest.writeString(codeudor);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cartera> CREATOR = new Parcelable.Creator<Cartera>() {
        @Override
        public Cartera createFromParcel(Parcel in) {
            return new Cartera(in);
        }

        @Override
        public Cartera[] newArray(int size) {
            return new Cartera[size];
        }
    };
}
