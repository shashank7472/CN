package computer_network;

import java.io.*;
import java.net.*;

public class TCP_transfer_server {

    public static void main(String[] args) {
        int port = 12345; // Server port

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Say Hello to the client
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                writer.println("Hello, client!");

                // Handle file transfer
                InputStream input = socket.getInputStream();
                FileOutputStream fos = new FileOutputStream("received_file.txt");
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = input.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                System.out.println("File received");

                fos.close();
                socket.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}







package computer_network;

import java.io.*;
import java.net.*;

public class TCP_transfer_client {

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(hostname, port)) {
            // Receive hello message from server
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String serverMessage = reader.readLine();
            System.out.println("Server: " + serverMessage);

            // Send a file to the server
            File file = new File("send_file.txt");
            FileInputStream fis = new FileInputStream(file);
            OutputStream output = socket.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent");

            fis.close();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}