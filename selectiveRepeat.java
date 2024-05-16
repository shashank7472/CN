package computer_network;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class packet {
    int seq_num;
    String data;

    public packet(int seq_num, String data) {
        this.seq_num = seq_num;
        this.data = data;
    }
}

class peer {
    int peer_id;
    int window_size;
    int timeout;
    Deque<Packet> window;
    Map<Integer, Boolean> ack_status; // Acknowledgment status for each packet
    int seq_num;
    int expected_seq;

    public peer(int peer_id, int window_size, int timeout) {
        this.peer_id = peer_id;
        this.window_size = window_size;
        this.timeout = timeout;
        this.window = new ArrayDeque<>(window_size);
        this.ack_status = new HashMap<>();
        this.seq_num = 0;
        this.expected_seq = 0;
    }

    public Packet send_packet(String data) {
        if (window.size() < window_size) {
            Packet packet = new Packet(seq_num, data);
            window.add(packet);
            ack_status.put(seq_num, false); // Mark the packet as not acknowledged
            System.out.println("Peer " + peer_id + " sent packet with sequence number " + seq_num);
            seq_num++;
            return packet;
        } else {
            System.out.println("Peer " + peer_id + " sliding window is full. Cannot send packet.");
            return null;
        }
    }

    public void receive_ack(int ack_num) {
        if (ack_status.containsKey(ack_num) && !ack_status.get(ack_num)) {
            System.out.println("Peer " + peer_id + " received ACK for sequence number " + ack_num);
            ack_status.put(ack_num, true); // Mark the packet as acknowledged
            while (!window.isEmpty() && ack_status.containsKey(expected_seq) && ack_status.get(expected_seq)) {
                window.poll();
                ack_status.remove(expected_seq);
                expected_seq++;
            }
        } else {
            System.out.println("Peer " + peer_id + " received duplicate/out-of-order ACK for sequence number " + ack_num + ". Ignoring.");
        }
    }

    public void simulate(int num_packets) {
        Packet[] packets = new Packet[num_packets];
        for (int i = 0; i < num_packets; i++) {
            packets[i] = send_packet("Data_" + i);
        }

        Random random = new Random();
        while (!window.isEmpty() || ack_status.size() > 0) {
            if (!window.isEmpty()) {
                Packet packet = window.peek();
                try {
                    Thread.sleep(timeout * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (random.nextDouble() < 0.3) {
                    System.out.println("Packet with sequence number " + packet.seq_num + " was dropped.");
                } else {
                    System.out.println("Peer " + peer_id + " retransmitting packet with sequence number " + packet.seq_num);
                }
            }

            int ack_num = random.nextInt(seq_num);
            receive_ack(ack_num);
        }
    }
}

public class selectiveRepeat {
    public static void main(String[] args) {
        int window_size = 4;
        int timeout = 1;
        int num_packets = 10;

        Peer peer1 = new Peer(1, window_size, timeout);
        Peer peer2 = new Peer(2, window_size, timeout);

        System.out.println("Simulating peer-to-peer communication using Selective Repeat sliding window protocol:");
        peer1.simulate(num_packets);
        System.out.println();
        peer2.simulate(num_packets);
    }
}
