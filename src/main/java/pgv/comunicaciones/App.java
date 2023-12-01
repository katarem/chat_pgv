package pgv.comunicaciones;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        TCPServer server = new TCPServer();
        TCPClient client = new TCPClient();
        try{
            server.main(args);
            client.main(args);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
