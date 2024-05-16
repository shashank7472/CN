package computer_network;

import java.util.*;

public class shortestPath {

    // Define a class for representing graph edges
    static class Edge {
        int source, destination, weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Dijkstra's algorithm to find the shortest path
    static void dijkstra(ArrayList<ArrayList<Edge>> graph, int source) {
        int V = graph.size();
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        for (int i = 0; i < V - 1; i++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (Edge e : graph.get(u)) {
                if (!visited[e.destination] && dist[u] != Integer.MAX_VALUE &&
                        dist[u] + e.weight < dist[e.destination]) {
                    dist[e.destination] = dist[u] + e.weight;
                }
            }
        }

        printSolution(dist);
    }

    static int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        int V = dist.length;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    static void printSolution(int[] dist) {
        System.out.println("Vertex \t Distance from Source");
        for (int i = 0; i < dist.length; i++) {
            System.out.println(i + "\t\t" + dist[i]);
        }
    }

    public static void main(String[] args) {
        int V = 5; // Number of vertices
        int source = 0; // Source node

        // Adjacency list representation of the graph
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }

        // Add edges to the graph (source, destination, weight)
        graph.get(0).add(new Edge(0, 1, 2));
        graph.get(0).add(new Edge(0, 2, 4));
        graph.get(1).add(new Edge(1, 2, 1));
        graph.get(1).add(new Edge(1, 3, 7));
        graph.get(2).add(new Edge(2, 4, 3));
        graph.get(3).add(new Edge(3, 4, 1));

        // Call Dijkstra's algorithm
        dijkstra(graph, source);
    }
}
