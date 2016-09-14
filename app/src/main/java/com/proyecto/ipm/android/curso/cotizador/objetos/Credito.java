package com.proyecto.ipm.android.curso.cotizador.objetos;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by USUARIO on 13/09/2016.
 */



public class Credito implements Parcelable {

    /**
     * Nombre de la categoría seleccionada
     */
    @Getter @Setter
    private String categoria;
    /**
     * Objeto de tipo Cartera, contiene todas las restricciones para el crédito
     */
    @Getter @Setter
    private Cartera cartera;
    /**
     * Monto solicitado para el crédito
     */
    @Getter @Setter
    private float montoSol;
    /**
     * Plazo seleccionado (numero de cuotas)
     */
    @Getter @Setter
    private  int plazo;

    /**
     * Monto a cancelar de cada cuota
     */
    @Getter @Setter
    private float montoCuota;

    public Credito() {}

    public Credito(String categoria, Cartera cartera, float montoSol, int plazo, float montoCuota) {
        this.categoria = categoria;
        this.cartera = cartera;
        this.montoSol = montoSol;
        this.plazo = plazo;
        this.montoCuota = montoCuota;
    }

    protected Credito(Parcel in) {
        categoria = in.readString();
        cartera = (Cartera) in.readValue(Cartera.class.getClassLoader());
        montoSol = in.readFloat();
        plazo = in.readInt();
        montoCuota = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoria);
        dest.writeValue(cartera);
        dest.writeFloat(montoSol);
        dest.writeInt(plazo);
        dest.writeFloat(montoCuota);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Credito> CREATOR = new Parcelable.Creator<Credito>() {
        @Override
        public Credito createFromParcel(Parcel in) {
            return new Credito(in);
        }

        @Override
        public Credito[] newArray(int size) {
            return new Credito[size];
        }
    };
}
