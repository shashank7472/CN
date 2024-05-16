package CN_LAB;


import java.util.*;

public class AODV {
    static class Route {
        int destination;
        int nextHop;
        int hops;

        public Route(int destination, int nextHop, int hops) {
            this.destination = destination;
            this.nextHop = nextHop;
            this.hops = hops;
        }
    }

    List<Route> routingTable;

    public AODV() {
        routingTable = new ArrayList<>();
    }

    public void addRoute(int destination, int nextHop, int hops) {
        routingTable.add(new Route(destination, nextHop, hops));
    }

    public void printRoutingTable() {
        System.out.println("Destination \t NextHop \t Hops");
        for (Route route : routingTable) {
            System.out.println(route.destination + " \t\t\t\t " + route.nextHop + " \t\t\t " + route.hops);
        }
    }

    public static void main(String[] args) {
        AODV aodv = new AODV();
        aodv.addRoute(1, 2, 1);
        aodv.addRoute(2, 3, 2);
        aodv.addRoute(3, 4, 3);
        aodv.printRoutingTable();
    }
}


/*
This Java code implements a simple version of the AODV (Ad hoc On-Demand Distance Vector) routing protocol. Below is a breakdown of the code:

Route Class:

This class represents a route in the network.
It contains three fields: destination (the destination node ID), nextHop (the next node to which the packet should be forwarded), and hops (the number of hops to reach the destination).
AODV Class:

This class manages the AODV routing table and provides methods to add routes and print the routing table.
It has a routingTable member variable of type List<Route> to store the routes.

addRoute Method:

This method adds a new route to the routing table.
It takes three parameters: destination (the destination node ID), nextHop (the next node to which the packet should be forwarded), and hops (the number of hops to reach the destination).
It creates a new Route object with the given parameters and adds it to the routingTable list.

printRoutingTable Method:

This method prints the contents of the routing table.
It iterates over each route in the routingTable list and prints the destination, nextHop, and hops fields in a tabular format.

main Method:

This is the entry point of the program.
It creates an instance of the AODV class.
It adds three sample routes to the routing table using the addRoute method.
It then calls the printRoutingTable method to print the contents of the routing table.
 */
