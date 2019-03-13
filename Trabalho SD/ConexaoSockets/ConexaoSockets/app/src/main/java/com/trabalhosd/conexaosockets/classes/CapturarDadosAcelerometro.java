package com.trabalhosd.conexaosockets.classes;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CapturarDadosAcelerometro implements SensorEventListener {

    private SensorManager gerenciarSensores;
    private Sensor acelerometro;
    private final List<Leitura> leituras;
    private Context contexto;
    private Handler handler;
    private int numeroLeituras = 0;

    private TextView textX;
    private TextView textY;
    private TextView textZ;

    public CapturarDadosAcelerometro(Context context, TextView[] valores) {
        contexto = context;
        leituras = new ArrayList<>();
        gerenciarSensores = (SensorManager) contexto.getSystemService(Context.SENSOR_SERVICE);
        acelerometro = gerenciarSensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        textX = valores[0];
        textY = valores[1];
        textZ = valores[2];

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //aqui
        final double x = event.values[0];
        final double y = event.values[1];
        final double z = event.values[2];

        if(numeroLeituras >= 12){

            leituras.add(new Leitura(x, y, z));

            textX.setText("X=" + String.format("%.3f", x));
            textY.setText("Y=" + String.format("%.3f", y));
            textZ.setText("Z=" + String.format("%.3f", z));
            Log.i("ACCELEROMETER", "X="+x+"; Y="+y+"; Z="+z);

            numeroLeituras = 0;
        }
        numeroLeituras++;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void iniciarCaptura(){
        gerenciarSensores.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pararCaptura(){
        gerenciarSensores.unregisterListener(this);
    }

    public List<Leitura> pegarLeituras(){
        return leituras;
    }

}
