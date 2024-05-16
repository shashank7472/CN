package computer_network;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(8080);

            System.out.println("Server listening on port 8080...");


            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");


            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String messageFromClient = in.readLine();
            System.out.println("Message from client: " + messageFromClient);

            PrintWriter out=new PrintWriter(clientSocket.getOutputStream(),true);
            out.println("HI");


            in.close();
            clientSocket.close();
            serverSocket.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







package computer_network;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 8080);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello!!");

            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseFromServer =in.readLine();
            System.out.println("response from server:"+responseFromServer);

            out.close();
            socket.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
