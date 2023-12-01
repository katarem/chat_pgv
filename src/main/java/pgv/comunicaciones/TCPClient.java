package pgv.comunicaciones;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
    
    public static void main(String[] args) throws IOException {
        //de momento estamos con localhost
        InetAddress address = InetAddress.getByName("localhost");
        int port = 6789;
        Socket socket = new Socket(address, port);
        System.out.println("CLIENTE ESCUCHANDO PUERTO " + port);
        //respuesta cliente a servidor
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
        
        //respuesta servidor a cliente
        BufferedReader inFromServer = 
        new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String mensajeAServer = "ping";
        outToServer.writeBytes(mensajeAServer);
        String mensajeDeServer = inFromServer.readLine();
        System.out.println(mensajeDeServer);
        socket.close();
    }


}
