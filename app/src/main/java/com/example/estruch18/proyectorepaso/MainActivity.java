package com.example.estruch18.proyectorepaso;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    //Atributos de la clase
    private TextView textoInfo, textoNumPulsaciones;
    private Button btnPulsaciones;
    private int countPulsaciones;

    private static final int ACTIVITY2=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaración de atributos de la clase
        textoInfo = (TextView)findViewById(R.id.tv_infoPulsaciones);
        textoNumPulsaciones = (TextView)findViewById(R.id.tv_numPulsaciones);
        btnPulsaciones = (Button)findViewById(R.id.btn_Pulsaciones);
        countPulsaciones = 0;
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

    //Método Listener del botón
    public void accionBtnPulsar(View v){
        btnPulsaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countPulsaciones++;
                textoInfo.setText("Muy bien!");
                textoNumPulsaciones.setTextColor(Color.RED);
                textoNumPulsaciones.setText(String.valueOf(countPulsaciones));

                //Por cada pulsación del botón mostramos una notificación informativa
                Notification.Builder notificacion = new Notification.Builder(getApplicationContext());
                notificacion.setContentTitle("Pulsaciones: " + countPulsaciones);
                notificacion.setContentText("Pulsaciones sobre el botón");
                notificacion.setSmallIcon(R.drawable.advertencia);
                notificacion.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.advertencia));

                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(1, notificacion.build());

                //Cuando el contador de pulsaciones sea == 5, este nos cambiará al Activity2
                if (countPulsaciones == 5) {
                    Intent act2 = new Intent(getApplicationContext(), Activity2.class);
                    startActivityForResult(act2, ACTIVITY2);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Segun el código recibido, gestionamos el subActivity correspondiente
        switch (requestCode){
            case ACTIVITY2:
                gestionActivity2(resultCode, data);
                break;
        }

    }

    //Método encargado de gestionar los datos de vuelta del Activity2
    public void gestionActivity2(int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(data.getExtras().getBoolean("estadoSwitch")){
                //Si el estado del switch recogido es true mostramos la notificación expandida
                Notification.Builder notificacion = new Notification.Builder(getApplicationContext());
                notificacion.setContentTitle("Advertencia");
                notificacion.setContentText("Desea reiniciar el contador de pulsaciones?");
                notificacion.setSmallIcon(R.drawable.advertencia);
                notificacion.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.advertencia));
                notificacion.addAction(android.R.drawable.ic_dialog_alert, "Continuar", null);
                notificacion.addAction(android.R.drawable.ic_input_delete, "Reiniciar", null);

                NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                nm.notify(1,notificacion.build());

            }
            else{
                //Si el estado del switch recogido es false mostramos la información a través de un Toast
                Toast.makeText(getApplicationContext(),data.getExtras().getString("nombre"), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),data.getExtras().getString("pueblo"), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),data.getExtras().getString("provincia"), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Has pulsado 5 veces" , Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(getApplicationContext(), "Error en el subActivity 2.", Toast.LENGTH_SHORT).show();
        }
    }
}
