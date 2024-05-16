package computer_network;

import java.util.Scanner;

public class ERRHAMMING {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ASCII data (7 bits): ");
        String data = scanner.nextLine();
        if (data.length() != 7 || !data.matches("[01]+")) {
            System.out.println("Invalid input. ASCII data should be 7 bits long and contain only 0s and 1s.");
            return;
        }

        int[] hammingCode = calculateHammingCode(data);
        System.out.println("Original Hamming Code: " + arrayToString(hammingCode));

        System.out.print("Enter the position to introduce error (0-" + (hammingCode.length - 1) + "): ");
        int errorPosition = scanner.nextInt();
        if (errorPosition < 0 || errorPosition >= hammingCode.length) {
            System.out.println("Invalid input. Error position should be within the range of the Hamming code length.");
            return;
        }

        addError(hammingCode, errorPosition);
        System.out.println("Received Hamming Code with Error: " + arrayToString(hammingCode));

        int detectedErrorPosition = detectError(hammingCode);
        if (detectedErrorPosition == 0) {
            System.out.println("No error detected.");
        } else {
            System.out.println("Error detected at position: " + (detectedErrorPosition - 1));
        }

        scanner.close();
    }

    public static int[] calculateHammingCode(String data) {
        int m = data.length();
        int r = 0;
        while (Math.pow(2, r) < m + r + 1) {
            r++;
        }

        int[] hammingCode = new int[m + r];

        int j = 0;
        for (int i = 0; i < m + r; i++) {
            if ((i & (i + 1)) != 0) { // i+1 is not a power of 2
                hammingCode[i] = data.charAt(j++) - '0';
            }
        }

        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;
            int onesCount = 0;
            for ( j = parityIndex; j < m + r; j += 2 * (parityIndex + 1)) {
                for (int k = j; k < Math.min(j + parityIndex + 1, m + r); k++) {
                    onesCount += hammingCode[k];
                }
            }
            hammingCode[parityIndex] = onesCount % 2;
        }

        return hammingCode;
    }

    public static void addError(int[] hammingCode, int errorPosition) {
        hammingCode[errorPosition] = 1 - hammingCode[errorPosition];
    }

    public static int detectError(int[] hammingCode) {
        int r = 0;
        while (Math.pow(2, r) < hammingCode.length) {
            r++;
        }
        int errorPosition = 0;
        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;
            int onesCount = 0;
            for (int j = parityIndex; j < hammingCode.length; j += 2 * (parityIndex + 1)) {
                for (int k = j; k < Math.min(j + parityIndex + 1, hammingCode.length); k++) {
                    onesCount += hammingCode[k];
                }
            }
            if (onesCount % 2 != 0) {
                errorPosition += parityIndex + 1;
            }
        }
        return errorPosition;
    }

    public static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int num : arr) {
            sb.append(num);
        }
        return sb.toString();
    }
}
