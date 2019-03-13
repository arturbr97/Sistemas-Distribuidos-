package com.trabalhosd.servidor;

import java.io.IOException;

public class Principal {

	private static Servidor servidor;

	public static void main(String[] args) {
		try {
		servidor = new Servidor(3000);
		servidor.ouvir();
		}catch (IOException e) {
			System.out.println("Erro no servidor: "+e.getMessage());
		}
	}

}
