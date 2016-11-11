package com.proyecto.ipm.android.curso.cotizador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.proyecto.ipm.android.curso.cotizador.db.CotizaTableManager;
import com.proyecto.ipm.android.curso.cotizador.objetos.Credito;

import org.w3c.dom.Text;

public class ConfirmActivity extends AppCompatActivity {

    private TextView textViewTasa;
    private TextView textViewMontoCuota;
    private TextView textViewPlazo;
    private Credito credito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);


        //Instancia de los objetos textview
        textViewTasa=(TextView) findViewById(R.id.tvTasa);
        textViewPlazo=(TextView) findViewById(R.id.tvPlazoSol);
        textViewMontoCuota=(TextView) findViewById(R.id.tvMontoCuota);


        credito=getIntent().getParcelableExtra("credito");

        float tazaMensual=((Float.parseFloat(String.valueOf(credito.getCartera().getTaza()))/12)/100);
        float resultado= (float) (( tazaMensual * credito.getMontoSol() )  / ( 1 - ( Math.pow( 1 + tazaMensual , credito.getPlazo()*-1 )  )  ));


        //Mostrando los datos del objeto crédito para la confirmación
        textViewTasa.setText(String.valueOf(credito.getCartera().getTaza())+" %");
        textViewMontoCuota.setText("Q "+String.format("%.2f",resultado));
        textViewPlazo.setText(String.valueOf(credito.getPlazo()));
    }

    public void onClickNo(View view){
        finish();
    }

    public void onClickSi(View view){
        Intent intent= new Intent();
        intent.putExtra("confirma",credito);
        boolean save = CotizaTableManager.saveCredito(this,credito);
        intent.putExtra("saved",save);

        setResult(RESULT_OK,intent);

        finish();
    }
}
