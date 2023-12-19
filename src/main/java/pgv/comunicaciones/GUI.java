package pgv.comunicaciones;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;


public class GUI extends Application {

    private static String IP = "";

    private BorderPane base = new BorderPane();

    private TextField inputUser;
    private TextArea chatGlobal;

    private ClientThread client;

    @Override
    public void start(Stage stage) throws Exception {

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
        base.setPrefSize(600,400);
        inputUser = new TextField();

        chatGlobal = new TextArea();
        chatGlobal.setEditable(false);
        chatGlobal.setFocusTraversable(false);

        Button sendButton = new Button();
        //mandar mensajes con el boton o enter
        sendButton.setOnAction(e -> sendMessage());
        sendButton.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) sendMessage();
        });


        HBox bottomContainer = new HBox();
        bottomContainer.getChildren()
                        .addAll(inputUser,sendButton);

        inputUser.setPrefWidth(550);
        sendButton.setPrefWidth(50);

        sendButton.setPrefWidth(50);
        base.setCenter(chatGlobal);
        base.setBottom(bottomContainer);

        TextInputDialog nicknameGetter = new TextInputDialog();
        nicknameGetter.setContentText("Introduce tu nickname");
        var nick = nicknameGetter.showAndWait();

        stage.setScene(new Scene(base));
        stage.setTitle("CHATXD");
        stage.setResizable(false);
        stage.show();

        if(nick.isPresent()) {
            stage.setTitle(nick.get() + " - CHATXD");
            initializeChat(nick.get());
        }
        else initializeChat();
    }

    public void initializeChat() throws IOException {
        Server s = new Server(1234);
        s.start();
        client = Client.createClient(chatGlobal,inputUser);
        client.start();
    }
    public void initializeChat(String nickname) throws IOException {
        Server s = new Server(1234);
        s.start();
        InetAddress ip;
        if(!IP.isBlank())
             ip = InetAddress.getByName(IP);
        else ip = InetAddress.getByName("localhost");
        client = Client.createClient(ip, nickname, chatGlobal,inputUser);
        client.start();
    }

    public void sendMessage() {
       try{
           client.sendMessage();
       } catch(IOException e){
           e.printStackTrace();
       }
    }




    public static void main(String[] args) {
        launch(args);
        if(args.length>0) GUI.IP = args[0];
    }
}
