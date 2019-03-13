package com.trabalhosd.conexaosockets.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trabalhosd.conexaosockets.R;
import com.trabalhosd.conexaosockets.classes.DadosServidor;

public class ServidorActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtIP;
    private Button btnVoltar;
    private Button btnSalvar;
    private DadosServidor dadosServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidor);
        instanciarComponentes();
        verificarSeExisteIP();
    }

    private void verificarSeExisteIP() {
        if(dadosServidor.ipExiste()){
            edtIP.setText(dadosServidor.pegarIP());
        }
    }

    private void instanciarComponentes() {

        edtIP = findViewById(R.id.edt_ip);

        btnSalvar = findViewById(R.id.btn_salvar);
        btnSalvar.setOnClickListener(this);
        btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(this);

        dadosServidor = new DadosServidor(getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_salvar){

            if(TextUtils.isEmpty(edtIP.getText())){
                Toast.makeText(this, "Preencha o IP", Toast.LENGTH_SHORT).show();
            }else{
                if(dadosServidor.salvar(edtIP.getText().toString())){
                    Toast.makeText(this, "Salvo!", Toast.LENGTH_SHORT).show();
                }
            }

        }else if(v.getId() == R.id.btn_voltar){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
