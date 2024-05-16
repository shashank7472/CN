package computer_network;

import java.net.*;

public class UDPserver {
    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        try {
            // Create a UDP socket at port 8080
            serverSocket = new DatagramSocket(8080);
            byte[] receiveData = new byte[1024];
            byte[] sendData = "Hello".getBytes();

            // Server loop
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Receive message from client
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Print client's address and port
                InetAddress clientIPAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                System.out.println("Connected to client: " + clientIPAddress + ":" + clientPort);
                System.out.println("Received from client: " + sentence);

                // Send response to client
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, clientPort);
                serverSocket.send(sendPacket);
                System.out.println("Sent to client: Hello");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}






package computer_network;

import java.net.*;

public class UDPclient {
    public static void main(String[] args) {
        DatagramSocket clientSocket = null;
        try {
            // Create a UDP socket
            clientSocket = new DatagramSocket();

            // Server address and port
            InetAddress serverIPAddress = InetAddress.getByName("localhost");
            int serverPort = 8080;

            byte[] sendData = "Hello".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverPort);

            // Send the packet to the server
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // Receive the response from the server
            clientSocket.receive(receivePacket);
            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received from server: " + receivedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null) {
                clientSocket.close();
            }
        }
    }
}

