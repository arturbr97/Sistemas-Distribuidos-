package com.trabalhosd.conexaosockets.classes;

import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.google.gson.Gson;

public class Cliente {
	private static final int porta = 3000;
	private Socket cliente;
	private PrintStream escritor;
	private List<Leitura> leituras;
	public Cliente(String host) throws UnknownHostException, IOException {
		cliente = new Socket();
		cliente.connect(new InetSocketAddress(host, porta), 5000);
		escritor = new PrintStream(cliente.getOutputStream());
	}
	
	public void adicionarDadosParaEnviar(List<Leitura> leituras) {
		this.leituras = leituras;
	}
	
	public void enviar() {
		String mensagem = new Gson().toJson(leituras);
		Log.i("ACCELEROMETER", mensagem);
		escritor.println(mensagem);
		escritor.flush();
		escritor.close();
	}
	public void encerrar() throws IOException {
		cliente.close();
	}
}
