package pgv.comunicaciones;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread{

    //private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
    private DataOutputStream out;
    private BufferedReader in;
    private Socket socket;
    private String nickname;

    private TextField input;
    private TextArea chat;

    private boolean arrancar = true;

    public ClientThread(String nickname, DataOutputStream out, BufferedReader in, Socket socket, TextArea chat, TextField input) {
        this.out = out;
        this.in = in;
        this.socket = socket;
        this.chat = chat;
        this.input = input;
        this.nickname = nickname;
    }

    void printMessage(String message){
        chat.setText(chat.getText() + "\n" + message);
    }

    public String getNickname() {
        return nickname;
    }

    public void sendMessage() throws IOException {
        String mensaje;
        if((mensaje = input.getText()) != null && !mensaje.isBlank()) {
            if(mensaje.startsWith("/quit")) arrancar = false;

            out.writeBytes(nickname + ": " + mensaje + "\n");
            out.flush();
            input.clear();
        }
    }


    @Override
    public void run() {
        try {
            out.writeBytes("El usuario " + nickname + " se ha unido a la sala!\n");
            out.flush();
            while(arrancar){
                //Si hay contenido en el stream del que leer
                if(in.ready()) printMessage(in.readLine());
                Thread.sleep(100);
            }
            out.writeBytes("El usuario " + nickname + " se ha ido\n");
            out.flush();
            socket.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
