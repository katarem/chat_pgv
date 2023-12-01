package pgv.comunicaciones;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        int port = 6789;

        ServerSocket welcomeSocket = new ServerSocket(port);
        
        while (true) {
            //esto detiene la ejecución en espera de un cliente
            System.out.println("ESPERANDO CONEXIÓN A PUERTO " + port);
            Socket connectionSocket = welcomeSocket.accept();
            
            //datos recibidos por cliente
            BufferedReader inFromClient = 
            new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            
            //datos que enviamos al cliente
            DataOutputStream outToClient =
            new DataOutputStream(connectionSocket.getOutputStream());

            String clientSentence = inFromClient.readLine();
            System.out.println("Recibido: " + clientSentence);
            String serverResponse = "pong";
            outToClient.writeBytes(serverResponse);
        }


    }
}
