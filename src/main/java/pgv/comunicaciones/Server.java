package pgv.comunicaciones;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends Thread{

    protected ConcurrentLinkedDeque<Socket> sockets;
    private int port;
    public Server(int port){
        this.port = port;
    }

    public Server(){
        this(1234);
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Creando server");
            ServerThread st = new ServerThread(server, this);
            Thread.currentThread().setName("server-mensajes");
            sockets = new ConcurrentLinkedDeque<>();
            st.start();
            System.out.println("Server creado con éxito!");
            var arrancar = true;
            while(arrancar){
                //Tendré que iterar por cada DataOutput y por cada BufferedReader
                    for(Socket client: sockets){
                        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        if(inFromClient.ready()){
                            var sentenceFromClients = inFromClient.readLine();
                            broadcast(sentenceFromClients);
                        }
                    }
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ya existía server, conectando a server...");
        }
    }

    private void broadcast(String sentenceFromClients) {
        for (Socket socket: sockets){
            try {
                DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                outToClient.writeBytes(sentenceFromClients + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ServerThread extends Thread{

    private final ServerSocket serverSocket;

    private final Server server;

    public ServerThread(ServerSocket serverSocket, Server server){
        this.serverSocket = serverSocket;
        this.server = server;
    }


    @Override
    public void run() {
        System.out.println("Esperando clientes...");
        while(true){
            try {
                Socket s = serverSocket.accept();
                server.sockets.add(s);
                System.out.println("Cliente aceptado");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


