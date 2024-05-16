package computer_network;

import java.io.*;
import java.net.*;

public class UDP_transfer_server {

    private static final int BUFFER_SIZE = 1024;
    private static final int PORT = 9876;

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(PORT);

            receiveFile("script_received.txt", serverSocket);
            receiveFile("audio_received.mp3", serverSocket);
            receiveFile("video_received.mp4", serverSocket);

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveFile(String fileName, DatagramSocket socket) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (true) {
            socket.receive(packet);
            bos.write(packet.getData(), 0, packet.getLength());
            if (packet.getLength() < BUFFER_SIZE) break;
        }

        bos.close();
        fos.close();
        System.out.println("Received file: " + fileName);
    }
}







package computer_network;

import java.io.*;
import java.net.*;

public class UDP_transfer_client {

    private static final int BUFFER_SIZE = 1024;
    private static final int SERVER_PORT = 9876;
    private static final int CLIENT_PORT = 9877;

    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getLocalHost();

            sendFile("script.txt", clientSocket, serverAddress, SERVER_PORT);
            sendFile("audio.mp3", clientSocket, serverAddress, SERVER_PORT);
            sendFile("video.mp4", clientSocket, serverAddress, SERVER_PORT);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(String fileName, DatagramSocket socket, InetAddress serverAddress, int serverPort) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        while ((bytesRead = bis.read(buffer)) != -1) {
            DatagramPacket packet = new DatagramPacket(buffer, bytesRead, serverAddress, serverPort);
            socket.send(packet);
        }

        bis.close();
        fis.close();
        System.out.println("Sent file: " + fileName);
    }
}