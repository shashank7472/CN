package computer_network;

import java.util.Scanner;

public class IPAddressClassifier {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter IP address (xxx.xxx.xxx.xxx format): ");
        String ipAddress = scanner.nextLine();


        if (isValidIPAddress(ipAddress)) {
            String[] octets = ipAddress.split("\\.");

            for (int i = 0; i < octets.length; i++) {
                octets[i] = completeOctet(octets[i]);
            }

            String rewrittenIPAddress = String.join(".", octets);

            int firstOctetValue = Integer.parseInt(octets[0]);

            String ipAddressClass;
            String ipAddressType;

            if (firstOctetValue >= 1 && firstOctetValue <= 126) {
                ipAddressClass = "Class A";
            } else if (firstOctetValue >= 128 && firstOctetValue <= 191) {
                ipAddressClass = "Class B";
            } else if (firstOctetValue >= 192 && firstOctetValue <= 223) {
                ipAddressClass = "Class C";
            } else if (firstOctetValue >= 224 && firstOctetValue <= 239) {
                ipAddressClass = "Class D (Multicast)";
            } else if (firstOctetValue >= 240 && firstOctetValue <= 255) {
                ipAddressClass = "Class E (Reserved)";
            } else {
                ipAddressClass = "Unknown";
            }


            ipAddressType = isPrivateIPAddress(rewrittenIPAddress) ? "Private" : "Public";

            System.out.println("IP Address: " + rewrittenIPAddress);
            System.out.println("IP Address Class: " + ipAddressClass);
            System.out.println("IP Address Type: " + ipAddressType);

        } else {
            System.out.println("Invalid IP address format");
        }

        scanner.close();
    }

    private static boolean isValidIPAddress(String ipAddress) {
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
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    private static String completeOctet(String octet) {
        while (octet.length() < 3) {
            octet = "0" + octet;
        }
        return octet;
    }

    private static boolean isPrivateIPAddress(String ipAddress) {
        // Private IP address ranges: 10.0.0.0 to 10.255.255.255, 172.16.0.0 to 172.31.255.255, 192.168.0.0 to 192.168.255.255
        return ipAddress.startsWith("10.") || ipAddress.startsWith("172.") || ipAddress.startsWith("192.168.");
    }
}
