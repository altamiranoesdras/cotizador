package com.proyecto.ipm.android.curso.cotizador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        //Mostrando los datos del objeto crédito para la confirmación
        textViewTasa.setText(String.valueOf(credito.getCartera().getTaza())+" %");
        textViewMontoCuota.setText("Q "+String.valueOf((int) credito.getMontoCuota()));
        textViewPlazo.setText(String.valueOf(credito.getPlazo()));
    }

    public void onClickNo(View view){
        finish();
    }

    public void onClickSi(View view){
        Intent intent= new Intent();
        intent.putExtra("confirma",credito);
        setResult(RESULT_OK,intent);
        finish();
    }
}
