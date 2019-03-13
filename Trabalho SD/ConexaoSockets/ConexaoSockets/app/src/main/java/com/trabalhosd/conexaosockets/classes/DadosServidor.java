package com.trabalhosd.conexaosockets.classes;

import android.content.Context;
import android.content.SharedPreferences;

public class DadosServidor {
    private static final String CHAVE = "DadosServidor";
    private static final String CHAVE_IP = "IPServidor";
    private Context cotexto;
    SharedPreferences dados;
    public DadosServidor(Context context) {
        this.cotexto = context;
        dados = context.getSharedPreferences(CHAVE, Context.MODE_PRIVATE);
    }

    public boolean salvar(String ip){
        SharedPreferences.Editor editor = dados.edit();
        editor.putString(CHAVE_IP, ip);
        return editor.commit();
    }

    public boolean ipExiste(){
        return (dados.getString(CHAVE_IP, null) == null)? false: true;
    }

    public String pegarIP(){
        return dados.getString(CHAVE_IP, null).toString();
    }
}
