package com.example.salarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public Button registrar, buscar, limpiar;
    public TableLayout tabla;
    public TextView sinresultados, mnombre, mquincenal, mporhora, mhoras, mbruto, msocial, meducativo, mrenta, mfijo, mneto;
    public EditText cedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registrar = (Button) findViewById(R.id.bt_registrar);
        buscar = (Button) findViewById(R.id.bt_buscar);
        limpiar = (Button) findViewById(R.id.bt_limpiar);
        tabla = (TableLayout)  findViewById(R.id.tl_tabla);
        sinresultados = (TextView)  findViewById(R.id.tv_sinresultados);
        cedula = (EditText)  findViewById(R.id.et_buscarcedula);

        mnombre= (TextView)  findViewById(R.id.mostrar_nombre);
        mquincenal= (TextView)  findViewById(R.id.mostrar_quincenal);
        mporhora= (TextView)  findViewById(R.id.mostrar_porhora);
        mhoras= (TextView)  findViewById(R.id.mostrar_horas);
        mbruto= (TextView)  findViewById(R.id.mostrar_bruto);
        msocial= (TextView)  findViewById(R.id.mostrar_social);
        meducativo= (TextView)  findViewById(R.id.mostrar_educativo);
        mrenta= (TextView)  findViewById(R.id.mostrar_renta);
        mfijo= (TextView)  findViewById(R.id.mostrar_descuento);
        mneto= (TextView)  findViewById(R.id.mostrar_neto);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Registrar.class));
            }
        });
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabla.setVisibility(View.INVISIBLE);
                sinresultados.setVisibility(View.INVISIBLE);
                cedula.setText("");
            }
        });
    }

    //BUSCAR
    public void BuscarDatos (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1 );
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String txt = cedula.getText().toString();
        String cadena = "\"" + txt + "\"";
        sinresultados.setText(cadena);
        if (!cadena.isEmpty()){
            Cursor fila  = BaseDeDatos.rawQuery("select * from asalariados where bdcedula =" + cadena, null);
            tabla.setVisibility(View.VISIBLE);
            if(fila.moveToFirst()){
                sinresultados.setVisibility(View.INVISIBLE);
                tabla.setVisibility(View.VISIBLE);
                mnombre.setText(fila.getString(1));
                mquincenal.setText(fila.getString(2));
                mporhora.setText(fila.getString(3));
                mhoras.setText(fila.getString(4));
                mbruto.setText(fila.getString(5));
                msocial.setText(fila.getString(6));
                meducativo.setText(fila.getString(7));
                mrenta.setText(fila.getString(8));
                mfijo.setText(fila.getString(9));
                mneto.setText(fila.getString(10));
                BaseDeDatos.close();
            }
            else{
                sinresultados.setText("SIN RESULTADOS");
                sinresultados.setVisibility(View.VISIBLE);
                tabla.setVisibility(View.INVISIBLE);
                BaseDeDatos.close();
            }
        }
        else{
            sinresultados.setText("INGRESE DATOS");
            tabla.setVisibility(View.INVISIBLE);
            sinresultados.setVisibility(View.VISIBLE);
        }


    }
}