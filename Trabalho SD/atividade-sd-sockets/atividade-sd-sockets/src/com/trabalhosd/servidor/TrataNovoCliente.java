package com.trabalhosd.servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TrataNovoCliente implements Runnable {
	private Socket cliente;
	private PrintStream escritor;
	private Scanner leitor;

	public TrataNovoCliente(Socket cliente) throws IOException {
		this.cliente = cliente;
		this.escritor = new PrintStream(cliente.getOutputStream());
		this.leitor = new Scanner(cliente.getInputStream());
	}

	@Override
	public void run() {
		List<Leitura> leituras = pegarMensagem();
		mostrarLogServidor(leituras);
	}

	private void mostrarLogServidor(List<Leitura> leituras) {
		mostrarCabecalhoLogServidor();
		
		for (int i = (leituras.size()-1); i >= 0; i--) {
			Leitura leitura = leituras.get(i);
			System.out.println(leitura.toString());
		}
		
		try {
			cliente.close();
		} catch (IOException e) {}
	}

	@SuppressWarnings("unused")
	private void escreverMensagem(String mensagem) {
		escritor.println(mensagem);
		escritor.flush();
		escritor.close();
	}

	private List<Leitura> pegarMensagem() {
		String mensagem = leitor.nextLine();
		List<Leitura> leituras = new Gson().fromJson(mensagem, new TypeToken<List<Leitura>>() {
		}.getType());
		leitor.close();
		return leituras;
	}

	private void mostrarCabecalhoLogServidor() {
		System.out.println("############################################");
		System.out.println("# DADOS DE LEITURA RECEBIDOS PELO SERVIDOR #");
		System.out.println("############################################");
		System.out.println("CLIENTE: "+cliente.getInetAddress().getCanonicalHostName());
		System.out.println("--------------------------------------------");
	}

}
