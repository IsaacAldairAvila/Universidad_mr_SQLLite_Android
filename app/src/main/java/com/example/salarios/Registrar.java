package com.example.salarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registrar extends AppCompatActivity {
 public Button regresar, registrarnuevo, limpiarregistro;
 public EditText recedula, renombre, reapellido, rehorastrabajadas, resalarioquincenal, redescuentosfijos;
 public String cedula;
    public String textnombre;
    public String textapellido;
    public String texthorastrabajadas;
    public String textsalarioquincenal;
    public String textdescuentodirecto;
    public String nombrecompleto;
    public double salarioquincenal, horastrabajadas, descuentodirecto, salarioporhora, horasextras, salariobruto, segurosocial, seguroeducativo, impuestorenta, salarioneto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        registrarnuevo = findViewById(R.id.registrarnuevo);
        regresar = findViewById(R.id.bt_regresar);
        limpiarregistro = findViewById(R.id.bt_limpiarregistro);
        recedula = findViewById(R.id.et_cedula);
        renombre = findViewById(R.id.et_nombre);
        reapellido = findViewById(R.id.et_apellido);
        rehorastrabajadas = findViewById(R.id.et_horastrabajadas);
        resalarioquincenal = findViewById(R.id.et_salarioquincenal);
        redescuentosfijos= findViewById(R.id.et_descuentodirecto);
        limpiarregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recedula.setText("");
                renombre.setText("");
                reapellido.setText("");
                rehorastrabajadas.setText("");
                resalarioquincenal.setText("");
                redescuentosfijos.setText("");
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recedula.setText("");
                renombre.setText("");
                reapellido.setText("");
                rehorastrabajadas.setText("");
                resalarioquincenal.setText("");
                redescuentosfijos.setText("");
                startActivity(new Intent(Registrar.this, MainActivity.class));
            }
        });
    }

    public void RegistrarDatos (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        boolean pase = true;
        cedula = String.valueOf(recedula.getText());
        if (cedula.equals("")){
            pase = false;
        }
        textnombre = String.valueOf(renombre.getText());
        textapellido = String.valueOf(reapellido.getText());
        nombrecompleto = textnombre + " " + textapellido;
        if (textnombre.equals("")){
            pase = false;
        }
        if (textapellido.equals("")){
            pase = false;
        }
        textsalarioquincenal = String.valueOf(resalarioquincenal.getText());
        if (!textsalarioquincenal.equals("")) {
            salarioquincenal = Double.parseDouble(textsalarioquincenal);
            salariobruto = salarioquincenal;
            if(salarioquincenal < 0){
                pase = false;
            }
        }
        else{
            salarioquincenal=0;
            pase = false;
        }
        texthorastrabajadas = String.valueOf(rehorastrabajadas.getText());
        if (!texthorastrabajadas.equals("")) {
            horastrabajadas = Double.parseDouble(texthorastrabajadas);
            if(horastrabajadas < 0){
                pase = false;
            }
        }
        else{
            pase = false;
        }
        if (horastrabajadas>104){
            horasextras = horastrabajadas - 104;
            salarioporhora = salarioquincenal / 104;
        }
        else {
            horasextras = 0;
            salarioporhora = salarioquincenal / horastrabajadas;
        }
        textdescuentodirecto = String.valueOf(redescuentosfijos.getText());
        if (!textdescuentodirecto.equals("")) {
            descuentodirecto = Double.parseDouble(textdescuentodirecto);
            if(descuentodirecto < 0){
                pase = false;
            }
        }
        else{
            pase = false;
        }
        segurosocial = salariobruto * 0.09;
        seguroeducativo = salariobruto * 0.0125;
        if (salariobruto > 800){
            impuestorenta = (salariobruto - 800) * 0.15;
        }
        else{
            impuestorenta = 0;
        }
        salarioneto = salariobruto + (horasextras * 1.25) - segurosocial - seguroeducativo - descuentodirecto - impuestorenta;
                /*
                textcedula
                nombrecompleto
                salarioquincenal
                salarioporhora
                horastrabajadas
                segurosocial
                seguroeducativo
                impuestorenta
                impuestorenta
                descuentofijo
                salarioneto
                 */
        String complete = "Complete los campos";
        if (!pase){

            Context context = getApplicationContext();
            CharSequence text = complete;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            ContentValues registro = new ContentValues();
            registro.put("bdcedula", cedula);
            registro.put("bdnombre", nombrecompleto);
            registro.put("bdquincenal", salarioquincenal);
            registro.put("bdporhora", salarioporhora);
            registro.put("bdhoras", horastrabajadas);
            registro.put("bdsalariobruto", salariobruto);
            registro.put("bdssocial", segurosocial);
            registro.put("bdseducativo", seguroeducativo);
            registro.put("bdrenta", impuestorenta);
            registro.put("bddescuento", descuentodirecto);
            registro.put("bdneto", salarioneto);
            BaseDeDatos.insert("asalariados", null, registro);
            BaseDeDatos.close();
            recedula.setText("");
            renombre.setText("");
            reapellido.setText("");
            resalarioquincenal.setText("");
            rehorastrabajadas.setText("");
            redescuentosfijos.setText("");
            Context context = getApplicationContext();
            CharSequence text = cedula;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}