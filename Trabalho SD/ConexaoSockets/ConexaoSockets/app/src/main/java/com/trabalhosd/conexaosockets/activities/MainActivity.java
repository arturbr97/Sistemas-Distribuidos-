package com.trabalhosd.conexaosockets.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.trabalhosd.conexaosockets.R;
import com.trabalhosd.conexaosockets.classes.CapturarDadosAcelerometro;
import com.trabalhosd.conexaosockets.classes.Cliente;
import com.trabalhosd.conexaosockets.classes.DadosServidor;
import com.trabalhosd.conexaosockets.classes.Leitura;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private CapturarDadosAcelerometro capturarDadosAcelerometro;
    private boolean estaColetandoDados = false;

    private Button btnFinalizar;
    private Button btnIniciar;
    private Button btnEnviar;
    private Button btnConfigurar;

    private TextView[] valores = new TextView[3];

    private  DadosServidor dadosServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instanciarComponentes();
        capturarDadosAcelerometro = new CapturarDadosAcelerometro(this, valores);
        verificarSeDadosServidorExiste();
        verificarStatus();
    }

    private void verificarStatus() {
        if(estaColetandoDados){
            btnIniciar.setEnabled(false);
            btnFinalizar.setEnabled(true);
        }else{
            btnIniciar.setEnabled(true);
            btnFinalizar.setEnabled(false);
        }
    }

    private void verificarSeDadosServidorExiste() {
        if(dadosServidor.ipExiste()){
            btnEnviar.setEnabled(true);
        }else{
            btnEnviar.setEnabled(false);
        }
    }

    private void instanciarComponentes() {
        btnFinalizar = findViewById(R.id.btn_finalizar);
        btnFinalizar.setOnClickListener(this);

        btnIniciar = findViewById(R.id.btn_iniciar);
        btnIniciar.setOnClickListener(this);

        btnEnviar = findViewById(R.id.btn_enviar);
        btnEnviar.setOnClickListener(this);

        btnConfigurar = findViewById(R.id.btn_configurar);
        btnConfigurar.setOnClickListener(this);

        valores[0] = findViewById(R.id.text_x);
        valores[1] = findViewById(R.id.text_y);
        valores[2] = findViewById(R.id.text_z);

        dadosServidor = new DadosServidor(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pararCaptura();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //iniciarCaptura();
    }

    private void iniciarCaptura(){
        if(!estaColetandoDados){
            capturarDadosAcelerometro.iniciarCaptura();
            estaColetandoDados = true;
        }
    }
    private void pararCaptura(){
        if (estaColetandoDados){
            capturarDadosAcelerometro.pararCaptura();
            estaColetandoDados = false;
        }
    }

    public void enviarParaServidor(){
        btnEnviar.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Cliente cliente = new Cliente(dadosServidor.pegarIP());
                    List<Leitura> leituras = capturarDadosAcelerometro.pegarLeituras();
                    cliente.adicionarDadosParaEnviar(leituras);
                    cliente.enviar();
                    cliente.encerrar();
                    Toast.makeText(getApplicationContext(), "Dados ENVIADOS!",Toast.LENGTH_SHORT).show();
                    btnEnviar.setEnabled(true);
                } catch (final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "ERRO DE REDE: "+e.getMessage(),Toast.LENGTH_LONG).show();
                            btnEnviar.setEnabled(true);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_finalizar){
            capturarDadosAcelerometro.pararCaptura();
            Toast.makeText(this, "Captura FINALIZADA!",Toast.LENGTH_SHORT).show();
            estaColetandoDados = false;
            verificarStatus();
        }else if(v.getId() == R.id.btn_iniciar){
            capturarDadosAcelerometro.iniciarCaptura();
            Toast.makeText(this, "Captura INICIADA!",Toast.LENGTH_SHORT).show();
            estaColetandoDados = true;
            verificarStatus();
        }else if(v.getId() == R.id.btn_enviar){
            enviarParaServidor();
        }else if(v.getId() == R.id.btn_configurar){
            startActivity(new Intent(this, ServidorActivity.class));
        }
    }
}
