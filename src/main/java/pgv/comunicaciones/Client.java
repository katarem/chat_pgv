package pgv.comunicaciones;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private Client(){}

    public static ClientThread createClient(TextArea chat, TextField input) throws IOException {
        var id = "default" + (int)(Math.random()*900)+1;
        return createClient(id,chat,input);
    }


    public static ClientThread createClient(String name,TextArea chat, TextField input) throws IOException {
        Socket s = new Socket("localhost",1234);

        DataOutputStream outToServer = new DataOutputStream(s.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));

        ClientThread c = new ClientThread(name,outToServer,inFromServer,s,chat,input);
        return c;
    }
    public static ClientThread createClient(InetAddress ip,String name, TextArea chat, TextField input) throws IOException {
        Socket s = new Socket(ip,1234);

        DataOutputStream outToServer = new DataOutputStream(s.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));

        ClientThread c = new ClientThread(name,outToServer,inFromServer,s,chat,input);
        return c;
    }


}

