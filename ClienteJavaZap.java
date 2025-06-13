package prog_redes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteJavaZap {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("127.0.0.1",10000);
		System.out.println("Cliente conectado ao servidor de mensagens com sucesso");
		
		//Thread para enviar mensagens
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Scanner teclado = new Scanner(System.in);
					PrintStream out = new PrintStream(client.getOutputStream());
					
					while (teclado.hasNextLine()) {
						out.println(teclado.nextLine());
					} teclado.close();
					out.close();					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		//Thread para receber mensagens
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Scanner s = new Scanner(client.getInputStream());
					while (s.hasNextLine()) {
						System.out.println(s.nextLine());
					}s.close();
					client.close();
					}catch (IOException e) {
						e.printStackTrace();						
					}
				}
		}).start();
		
	
		
		
	}

}
