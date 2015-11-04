package com.example.estruch18.proyectorepaso;

import android.app.Activity;
import android.content.Intent;
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
        cargarSpProvincias();
        getProvincia();
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
    public void cargarSpProvincias(){
        ArrayAdapter adaptador = ArrayAdapter.createFromResource(this, R.array.provincias, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvincias.setAdapter(adaptador);
    }

    //Método Listener del spinner de provincias, este llama a "cargarSpPueblos" en la selección de cualquiera de sus items
    public void getProvincia(){
        spProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarSpPueblos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //En caso de no seleccionar, informar de ello por pantalla
                Toast.makeText(getApplicationContext(), "No has seleccionado ninguna provincia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Método cuyo objetivo és, según la selección del spinner spProvincias, cargar unos datos u otros
    public void cargarSpPueblos(){
        ArrayAdapter adaptador = null;
        switch (spProvincias.getSelectedItem().toString()){
            case "Albacete":
                adaptador = ArrayAdapter.createFromResource(this, R.array.localidades_albacete, android.R.layout.simple_spinner_item);
                break;

            case "Barcelona":
                adaptador = ArrayAdapter.createFromResource(this, R.array.localidades_barcelona, android.R.layout.simple_spinner_item);
                break;

            case "Alicante":
                adaptador = ArrayAdapter.createFromResource(this, R.array.localidades_alicante, android.R.layout.simple_spinner_item);

                break;

            case "Almeria":
                adaptador = ArrayAdapter.createFromResource(this, R.array.localidades_almeria, android.R.layout.simple_spinner_item);
                break;

            case "Asturias":
                adaptador = ArrayAdapter.createFromResource(this, R.array.localidades_asturias, android.R.layout.simple_spinner_item);
                break;

            case "Islas Baleares":
                adaptador = ArrayAdapter.createFromResource(this, R.array.localidades_islasBaleares, android.R.layout.simple_spinner_item);
                break;
        }
        spPueblos.setAdapter(adaptador);
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
