package com.example.estruch18.proyectorepaso;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


public class Activity2 extends Activity {
    //Atributos de la clase
    private EditText nombre;
    private Switch swNotificacion;
    private Button btnContinuar;
    private Spinner spProvincias, spPueblos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        //Declaración de atributos de la clase
        nombre = (EditText)findViewById(R.id.et_nombre);
        swNotificacion = (Switch)findViewById(R.id.sw_Notificacion);
        btnContinuar = (Button)findViewById(R.id.btn_Continuar);
        spProvincias = (Spinner)findViewById(R.id.sp_Provincias);
        spPueblos = (Spinner)findViewById(R.id.sp_Pueblos);

        //Ejecución de métodos
        cargarDatosSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
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

    //Método encargado de cargar los datos (Provincias) a partir de los recursos e implementarlos mediante un adaptador en el spinner
    public void cargarDatosSpinner(){
        ArrayAdapter adaptador = ArrayAdapter.createFromResource(this, R.array.provincias, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvincias.setAdapter(adaptador);

        spProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Creación de un objeto TypedArray
                TypedArray pueblos = getResources().obtainTypedArray(R.array.array_provincia_a_localidades);
                String localidades[] = getResources().getStringArray(pueblos.getResourceId(position, 0));

                ArrayAdapter adaptador1 = new ArrayAdapter(Activity2.this, android.R.layout.simple_spinner_item, localidades);
                adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spPueblos.setAdapter(adaptador1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "No se ha seleccionado ninguna provincia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Método Listener del botón Continuar
    public void accionBtnContinuar(View v){
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(nombre.getText().toString().length()==0){
                        Toast.makeText(getApplicationContext(), "Debes introducir un nombre", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Intent
                        Intent act = getIntent();
                        //Implementación de datos en el intent
                        act.putExtra("nombre", nombre.getText().toString());
                        act.putExtra("estadoSwitch", swNotificacion.isChecked());
                        act.putExtra("provincia", spProvincias.getSelectedItem().toString());
                        act.putExtra("pueblo", spPueblos.getSelectedItem().toString());
                        //Establecemos el resultado
                        setResult(RESULT_OK, act);
                        //Finalizamos el Activity para volver a el anterior
                        finish();
                    }
            }
        });
    }
}
