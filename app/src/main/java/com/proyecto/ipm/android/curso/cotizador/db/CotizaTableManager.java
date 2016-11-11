package com.proyecto.ipm.android.curso.cotizador.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.proyecto.ipm.android.curso.cotizador.R;
import com.proyecto.ipm.android.curso.cotizador.objetos.Cartera;
import com.proyecto.ipm.android.curso.cotizador.objetos.Credito;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 11/11/2016.
 */

public class CotizaTableManager {



    /**
     * Guarda los datos de la cotizacion o credito en la tabla cotiza
     * @param context
     * @param credito
     * @return
     */
    public  static boolean saveCredito(Context context, Credito credito) {
        boolean result = false;

        ContentValues cv = new ContentValues();
        cv.put(DbManager.COTIZA_FIEL_TIPO, credito.getIdxTipoOfi());
        cv.put(DbManager.COTIZA_FIEL_CATERA, credito.getCartera().getId());
        cv.put(DbManager.COTIZA_FIEL_MONTO_SOL, credito.getMontoSol());
        cv.put(DbManager.COTIZA_FIEL_NUM_CUOTAS, credito.getPlazo());
        cv.put(DbManager.COTIZA_FIEL_MONTO_CUOTA, credito.getMontoCuota());

        DbManager db = new DbManager(context);
        SQLiteDatabase database = db.getWritableDatabase();

        result = (database.insert(DbManager.COTIZA_TABLE,null,cv) != -1);

        database.close();
        db.close();

        return result;
    }

    public static List<Credito> getCreditos(Context context){


        List <Credito> creditoList=new ArrayList<>();
        String[] categorias = context.getResources().getStringArray(R.array.categoria);
        String[] tipos = context.getResources().getStringArray(R.array.tipo_prestamo);
        int[] plazos = context.getResources().getIntArray(R.array.max_plazo);
        int[][]  montos = new int[][]{
                context.getResources().getIntArray(R.array.max_def),
                context.getResources().getIntArray(R.array.max_fiduciario),
                context.getResources().getIntArray(R.array.max_vehiculos),
                context.getResources().getIntArray(R.array.max_vivienda)
        };
        float tasa=0;



        DbManager dataBaseManager=new DbManager(context);
        SQLiteDatabase database=dataBaseManager.getWritableDatabase();

        Cursor cursor= database.query(DbManager.COTIZA_TABLE,

                new String[]{DbManager.COTIZA_FIEL_TIPO,
                        DbManager.COTIZA_FIEL_CATERA,
                        DbManager.COTIZA_FIEL_MONTO_SOL,
                        DbManager.COTIZA_FIEL_NUM_CUOTAS,
                        DbManager.COTIZA_FIEL_MONTO_CUOTA,
                        DbManager.COTIZA_FIEL_ID},
                null,null,null,null,null);

        Credito tempCredito;
        Cartera tempCartera;

        if(cursor.moveToFirst()){
            do {

                tempCredito = new Credito();
                tempCartera = new Cartera();

                tempCartera.setId(cursor.getInt(2));
                switch (cursor.getInt(0)){
                    case 0:
                        tempCartera.setCodeudor("NO");
                        tempCartera.setTaza(0);
                        break;
                    case 1:
                        tempCartera.setCodeudor("SI");
                        tempCartera.setTaza(14);
                        break;

                    case 3:case 2:
                        tempCartera.setCodeudor("NO");
                        tempCartera.setTaza(9);
                        break;

                }
                tempCartera.setMotoMax(montos[cursor.getInt(0)][cursor.getInt(1)]);
                tempCartera.setNombre(tipos[cursor.getInt(1)]);

                tempCredito.setCategoria(categorias[cursor.getInt(0)]);
                tempCredito.setIdxTipoOfi(cursor.getInt(1));
                tempCredito.setCartera(tempCartera);
                tempCredito.setMontoSol(cursor.getFloat(2));
                tempCredito.setPlazo(cursor.getInt(3));
                tempCredito.setMontoCuota(cursor.getFloat(4));
                tempCredito.setId(cursor.getInt(5));

                creditoList.add(tempCredito);
            }while(cursor.moveToNext());
        }

        database.close();
        dataBaseManager.close();

        return creditoList;
    }

    public static boolean deleteCredito(Context context, Credito credito){
        boolean result =false;
        DbManager dataBaseManager=new DbManager(context);
        SQLiteDatabase database=dataBaseManager.getWritableDatabase();

        result=(database.delete(DbManager.COTIZA_TABLE,
                DbManager.COTIZA_FIEL_ID + "=?",
                new String[]{Integer.toString(credito.getId())})>=1);

        database.close();
        dataBaseManager.close();

        return  result;
    }
}
