package computer_network;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

class Packet {
    int seq_num;
    String data;

    public Packet(int seq_num, String data) {
        this.seq_num = seq_num;
        this.data = data;
    }
}

class Peer {
    int peer_id;
    int window_size;
    int timeout;
    Deque<Packet> window;
    int seq_num;
    int expected_ack;

    public Peer(int peer_id, int window_size, int timeout) {
        this.peer_id = peer_id;
        this.window_size = window_size;
        this.timeout = timeout;
        this.window = new ArrayDeque<>(window_size);
        this.seq_num = 0;
        this.expected_ack = 0;
    }

    public Packet send_packet(String data) {
        if (window.size() < window_size) {
            Packet packet = new Packet(seq_num, data);
            window.add(packet);
            System.out.println("Peer " + peer_id + " sent packet with sequence number " + seq_num);
            seq_num++;
            return packet;
        } else {
            System.out.println("Peer " + peer_id + " sliding window is full. Cannot send packet.");
            return null;
        }
    }

    public boolean receive_ack(int ack_num) {
        if (ack_num >= expected_ack) {
            System.out.println("Peer " + peer_id + " received ACK for sequence number " + ack_num);
            while (!window.isEmpty() && window.peek().seq_num <= ack_num) {
                window.poll();
                expected_ack++;
            }
            return true;
        } else {
            System.out.println("Peer " + peer_id + " received duplicate/out-of-order ACK for sequence number " + ack_num + ". Ignoring.");
            return false;
        }
    }

    public void simulate(int num_packets) {
        Packet[] packets = new Packet[num_packets];
        for (int i = 0; i < num_packets; i++) {
            send_packet("Data_" + i);
        }

        Random random = new Random();
        while (!window.isEmpty()) {
            Packet packet = window.peek();

            try {
                Thread.sleep(timeout * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (random.nextDouble() < 0.3) {
                System.out.println("Packet with sequence number " + packet.seq_num + " was dropped.");
                continue;
            }

            if (random.nextDouble() < 0.2) {
                System.out.println("Packet with sequence number " + packet.seq_num + " was corrupted.");
                continue;
            }

            int ack_num = expected_ack + random.nextInt(window.size() + 1);
            receive_ack(ack_num);
        }
    }
}

public class GoBackN {
    public static void main(String[] args) {
        int window_size = 4;
        int timeout = 1;
        int num_packets = 10;

        Peer peer1 = new Peer(1, window_size, timeout);
        Peer peer2 = new Peer(2, window_size, timeout);

        System.out.println("Simulating peer-to-peer communication using Go-Back-N sliding window protocol:");
        peer1.simulate(num_packets);
        System.out.println();
        peer2.simulate(num_packets);
    }
}
