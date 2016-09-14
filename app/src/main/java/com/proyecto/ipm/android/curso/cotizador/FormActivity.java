package com.proyecto.ipm.android.curso.cotizador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.ipm.android.curso.cotizador.objetos.Cartera;
import com.proyecto.ipm.android.curso.cotizador.objetos.Credito;

public class FormActivity extends AppCompatActivity {

    /**
     * Array de strings de las categorías para llenar spinners
     */
    private String[] categorias;
    /**
     * Array de strings de los tipos de crédito para llenar los spinners
     */
    private String[] tipos;
    /**
     * Array bidimensional para los montos maximos
     * primer vector [] esta definido por la categoría y el segundo [] por el tipo de credito
     */
    private int[][] montos;
    /**
     * Array de enteros para los plazos maximos posibles
     */
    private int[] plazos;
    /**
     * Array de enteros para las tazas de interes que se manejan
     */
    private int[] tazas;

    /**
     * monto maximo
     */
    private float montoMax;
    /**
     * plazo maximo
     */
    private int plazoMax;
    /**
     * Monto solicitado ingresado, operable para el calculo
     */
    private float montoSol;
    /**
     * Monto solicitado ingresado, operable para el calculo
     */
    private int plazoSol;
    /**
     *Tasa operable para el calculo
     */
    private float tasa;


    /**
     * Objeto controla el spinner de las categorías
     */
    private Spinner spCategoria;
    /**
     * Objeto controla el spinner de los tipos de creditos
     */
    private Spinner spTipo;

    /**
     *Elemento del formulario que muestra el monto maximo q puede solicitar en base a la categoría y tipo de crédito
     */
    private TextView tvMontoMax;
    /**
     * Elemento del formulario que muestra el plazo maximo q puede solicitar en base a la categoría y tipo de crédito
     */
    private TextView tvPlazoMax;
    /**
     * Elemento del formulario que muestra si se requiere o no codeudor en base a la categoría y tipo de crédito
     */
    private TextView tvReqCodeudor;
    /**
     * Elemento del formulario que cambia dinámicamente en base a la categoría y tipo de crédito
     */
    private TextView tvTasa;

    /**
     * Campo de texto del formulario para ingresar el monto solicitado
     */
    private EditText editTextMonto;
    /**
     * Campo de texto del formulario para ingresar el numero de cuotas
     */
    private EditText editTextCuotas;


    private Credito credito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        initComponents();
    }

    public  void  cotizaConfirma(View view){
        //Instancia de los campos de texto
        editTextMonto = (EditText) findViewById(R.id.etMontoSol);
        editTextCuotas = (EditText) findViewById(R.id.etCuotas);


        //valida que se seleccione una categoría
        if(spCategoria.getSelectedItemPosition() == 0){
            Toast.makeText(this,"Seleccione una categoría",Toast.LENGTH_LONG).show();
            return;
        }

        //valida que se seleccione un tipo de crédito
        if(spTipo.getSelectedItemPosition() == 0){
            Toast.makeText(this,"Seleccione un tipo crédito",Toast.LENGTH_LONG).show();
            return;
        }

        //Valida que se ingrese un monto
        if(TextUtils.isEmpty(editTextMonto.getText().toString())){
            Toast.makeText(this,"Debe ingresar un monto",Toast.LENGTH_LONG).show();
            editTextMonto.requestFocus();
            return;
        }else{
            montoSol= Float.parseFloat( editTextMonto.getText().toString());
            montoMax= Float.parseFloat(String.valueOf(montoMax));
        }

        //Valida que se ingrese un numero de cuotas
        if(TextUtils.isEmpty(editTextCuotas.getText().toString())){
            Toast.makeText(this,"Debe ingresar un numero de cuotas (plazo)",Toast.LENGTH_LONG).show();
            editTextCuotas.requestFocus();
            return;
        }else{
            plazoSol= Integer.parseInt(editTextCuotas.getText().toString());
            plazoMax= Integer.parseInt(String.valueOf(plazoMax));
        }

        //Valida el monto maximo
        if(montoSol > montoMax){
            Toast.makeText(this,"El monto solicitado no debe se mayor al monto maximo",Toast.LENGTH_LONG).show();
            editTextMonto.requestFocus();
            return;
        }

        //valida el plazo maximo
        if(plazoSol > plazoMax){
            Toast.makeText(this,"El numero de cuotas no debe ser mayor al plazo maximo",Toast.LENGTH_LONG).show();
            editTextCuotas.requestFocus();
            return;
        }



        Intent intent = new Intent(this,ConfirmActivity.class);

        Cartera cartera = new Cartera(spTipo.getSelectedItem().toString(),tasa,montoMax,plazoMax,tvReqCodeudor.getText().toString());
        credito.setCategoria(spCategoria.getSelectedItem().toString());
        credito.setCartera(cartera);
        credito.setPlazo(plazoSol);
        credito.setMontoSol(montoSol);
        intent.putExtra("credito",credito);

        startActivityForResult(intent,6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 6 && resultCode == RESULT_OK) {
            if (data != null) {
                Intent nuevaSolicitud= new Intent();
                Credito envioCredito =data.getParcelableExtra("confirma");
                nuevaSolicitud.putExtra("confirma",envioCredito);

                setResult(RESULT_OK,nuevaSolicitud);
                finish();
            }
        }
    }

    public void initComponents(){

        credito = new Credito();

        //llena los arrays de textos y enteros a travez de valores en el archivo de strings
        categorias = getResources().getStringArray(R.array.categoria);
        tipos = getResources().getStringArray(R.array.tipo_prestamo);
        plazos = getResources().getIntArray(R.array.max_plazo);
        montos = new int[][]{
                getResources().getIntArray(R.array.max_def),
                getResources().getIntArray(R.array.max_fiduciario),
                getResources().getIntArray(R.array.max_vehiculos),
                getResources().getIntArray(R.array.max_vivienda)
        };

        //Instancia de los textViews del formulario
        tvMontoMax=(TextView) findViewById(R.id.tvMontoMax);
        tvPlazoMax=(TextView) findViewById(R.id.tvPlazoMax);
        tvReqCodeudor=(TextView) findViewById(R.id.tvReqCodeudor);
        tvTasa=(TextView) findViewById(R.id.tvTasa);

        //Instancia de los objetos spinners
        spCategoria = (Spinner) findViewById(R.id.spCategorias);
        spTipo = (Spinner) findViewById(R.id.spTipos);

        //Llena los spinners
        spCategoria.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,categorias));
        spTipo.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,tipos));

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                tvMontoMax.setText("Q "+String.valueOf(montos[spTipo.getSelectedItemPosition()][i])+".00");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                tvMontoMax.setText("Q "+String.valueOf(montos[i][spCategoria.getSelectedItemPosition()])+".00");
                montoMax= montos[i][spCategoria.getSelectedItemPosition()];
                tvPlazoMax.setText(String.valueOf(plazos[i]));
                plazoMax= plazos[i];

                switch (i){
                    case 0:
                        tvReqCodeudor.setText("NO");
                        tvTasa.setText("0 %");
                        tasa=0;
                        break;
                    case 1:
                        tvReqCodeudor.setText("SI");
                        tvTasa.setText("14 %");
                        tasa=14;
                        break;
                    case 2:
                        tvReqCodeudor.setText("NO");
                        tvTasa.setText("9 %");
                        tasa=9;
                        break;
                    case 3:
                        tvReqCodeudor.setText("NO");
                        tvTasa.setText("9 %");
                        tasa=9;
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



}
