package com.trabalhosd.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends ServerSocket {

	public Servidor(int port) throws IOException {
		super(port);
		System.out.println("Servidor rodando na porta " + port);
	}

	public void ouvir() throws IOException {
		while (true) {
			Socket novoCliente = this.accept();
			new Thread(new TrataNovoCliente(novoCliente)).start();
		}
	}

}
