package prog_redes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorJavaZap {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(10000); //passa a porta para o construtor
		System.out.println("Porta 10000 aberta, aguardando conexão"); //fica parado
		//neste ponto ouvindo a rede e aguardando conexão de um cliente
		
		Socket client = server.accept();
		System.out.println("Conexão do cliente "+client.getInetAddress().getHostAddress());
		
		//Thread para receber mensagens
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Scanner s = new Scanner(client.getInputStream());
					while (s.hasNextLine()) {
						System.out.println("Cliente: " + s.nextLine());
					}
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		//Thread para enviar mensagens ao cliente
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Scanner teclado = new Scanner(System.in);
					PrintStream out = new PrintStream(client.getOutputStream());
					
					while(teclado.hasNextLine()) {
						out.println("Servidor: " + teclado.nextLine());
					} out.close();
					teclado.close();
					server.close();
				}catch (IOException e) {
					e.printStackTrace();
					}				
			}
		}).start();


	}

}
