package com.proyecto.ipm.android.curso.cotizador;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.proyecto.ipm.android.curso.cotizador.db.CotizaTableManager;
import com.proyecto.ipm.android.curso.cotizador.objetos.Credito;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvConvertion;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //Boton para cotizar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FormActivity.class);
                startActivityForResult(intent,5);
            }
        });

        initComponents();
    }

    private void  initComponents(){
        lvConvertion = (ListView) findViewById(R.id.lv_cotiz);

        final List<Credito> convertionList= CotizaTableManager.getCreditos(this);
        final List<String> convertionArray = new ArrayList<>();

        for(int i=0;i< convertionList.size(); i++){
            convertionArray.add(convertionList.get(i).getCategoria()+", \n"+convertionList.get(i).getCartera().getNombre());
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,convertionArray);
        lvConvertion.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == RESULT_OK) {
            if (data != null) {
                Credito credito=data.getParcelableExtra("agrega");
                String msjSave =data.getExtras().getString("msjsaved");
                Toast.makeText(this, msjSave, Toast.LENGTH_LONG).show();

                finish();
                startActivity(getIntent());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
