package computer_network;

import java.util.Scanner;

public class subnet {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for IP address
        System.out.print("Enter IP address (in the format xxx.xxx.xxx.xxx): ");
        String ipAddress = scanner.nextLine();

        // Validate IP address format
        if (!isValidIPAddress(ipAddress)) {
            System.out.println("Invalid IP address format. Please enter a valid IP address.");
            return;
        }

        // Prompt user for number of subnets
        System.out.print("Enter number of subnets: ");
        int numberOfSubnets = scanner.nextInt();

        // Validate number of subnets
        if (numberOfSubnets <= 0 || numberOfSubnets > 256) {
            System.out.println("Number of subnets must be between 1 and 256.");
            return;
        }

        // Calculate the difference for subnet ranges
        int difference = 256 / numberOfSubnets;

        // Calculate subnet ranges and subnet masks
        String[][] subnetInfo = calculateSubnetInfo(ipAddress, numberOfSubnets, difference);

        // Display subnet information
        System.out.println("\nSubnet Information:");
        for (int i = 0; i < numberOfSubnets; i++) {
            System.out.println("Subnet " + (i + 1) + ":");
            System.out.println("  Range: " + subnetInfo[i][0]);
            System.out.println("  Mask: " + subnetInfo[i][1]);
        }
    }

    public static boolean isValidIPAddress(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        if (octets.length != 4) {
            return false;
        }
        for (String octet : octets) {
            try {
                int value = Integer.parseInt(octet);
                if (value < 0 || value > 255) {
                    return false;
                }
                if (octet.length() > 1 && octet.startsWith("0")) {
                    return false; // Leading zeros are not allowed
                }
            } catch (NumberFormatException e) {
                return false; // Non-integer value found
            }
        }
        return true;
    }

    public static String[][] calculateSubnetInfo(String ipAddress, int numberOfSubnets, int difference) {
        // Split IP address into octets
        String[] ipOctets = ipAddress.split("\\.");

        // Convert octets to integers
        int[] ipInts = new int[4];
        for (int i = 0; i < 4; i++) {
            ipInts[i] = Integer.parseInt(ipOctets[i]);
        }

        // Calculate subnet ranges and subnet masks
        String[][] subnetInfo = new String[numberOfSubnets][2];
        int start = 0;
        int end = difference - 1;
        for (int i = 0; i < numberOfSubnets; i++) {
            // Adjust subnet range
            int[] subnetStart = ipInts.clone();
            int[] subnetEnd = ipInts.clone();
            subnetStart[subnetStart.length - 1] = start;
            subnetEnd[subnetEnd.length - 1] = end;

            // Format subnet start and end
            String startIp = formatIPAddress(subnetStart);
            String endIp = formatIPAddress(subnetEnd);

            // Calculate subnet mask
            int subnetMask = 32 - (int) (Math.log(numberOfSubnets) / Math.log(2));

            // Format subnet mask
            String subnetMaskString = getSubnetMask(subnetMask);

            // Update subnet information
            subnetInfo[i][0] = startIp + " - " + endIp;
            subnetInfo[i][1] = subnetMaskString;

            start += difference;
            end += difference;
        }

        return subnetInfo;
    }

    public static String formatIPAddress(int... octets) {
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 0; i < octets.length; i++) {
            ipAddress.append(octets[i]);
            if (i < octets.length - 1) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }

    public static String getSubnetMask(int prefixLength) {
        StringBuilder subnetMask = new StringBuilder();
        int fullOctets = prefixLength / 8;
        int partialOctetLength = prefixLength % 8;

        for (int i = 0; i < fullOctets; i++) {
            subnetMask.append("255");
            if (i < fullOctets - 1 || partialOctetLength > 0) {
                subnetMask.append(".");
            }
        }

        if (partialOctetLength > 0) {
            int partialOctetValue = 0;
            for (int i = 0; i < partialOctetLength; i++) {
                partialOctetValue += Math.pow(2, 7 - i);
            }
            subnetMask.append(partialOctetValue);
        }

        return subnetMask.toString();
    }
}
