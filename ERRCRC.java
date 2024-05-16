package computer_network;

import java.util.Arrays;
import java.util.Scanner;

public class ERRCRC {

    public static void printArray(int[] array, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(array[i]);
        }
    }

    public static int[] calculateRemainder(int[] frame, int[] generator) {
        int gensize = generator.length;
        int redsize = gensize - 1;
        int framesize = frame.length - redsize; // adjust frame size to original message length

        int[] temp = Arrays.copyOf(frame, frame.length);

        for (int i = 0; i < framesize; i++) {
            if (temp[i] == 1) { // perform XOR if leading bit is 1
                for (int j = 0; j < gensize; j++) {
                    temp[i + j] = temp[i + j] ^ generator[j];
                }
            }
        }

        int[] remainder = Arrays.copyOfRange(temp, framesize, framesize + redsize);
        return remainder;
    }

    public static void main(String[] args) {
        Scanner n = new Scanner(System.in);

        System.out.print("Enter the frame size: ");
        int framesize = n.nextInt();

        int[] f = new int[framesize];
        System.out.println("Enter the Frame:");
        for (int i = 0; i < framesize; i++) {
            f[i] = n.nextInt();
        }

        System.out.println("Enter the generator size: ");
        int gensize = n.nextInt();

        int[] g = new int[gensize];
        System.out.println("Enter the generator:");
        for (int i = 0; i < gensize; i++) {
            g[i] = n.nextInt();
        }

        System.out.println("Operations on sender's side");
        System.out.print("Frame: ");
        printArray(f, framesize);

        System.out.print("\nGenerator: ");
        printArray(g, gensize);

        int redsize = gensize - 1;
        System.out.println("\nNumber of 0's to be appended: " + redsize);

        int[] temp = new int[framesize + redsize];
        System.arraycopy(f, 0, temp, 0, framesize);

        System.out.println("\nMessage after appending 0's: ");
        printArray(temp, framesize + redsize);

        int[] crc = calculateRemainder(temp, g);

        System.out.print("\nCRC bits: ");
        printArray(crc, redsize);

        int[] trnframe = new int[framesize + redsize];
        System.arraycopy(f, 0, trnframe, 0, framesize);
        System.arraycopy(crc, 0, trnframe, framesize, redsize);

        System.out.print("\nTransmitted Frame: ");
        printArray(trnframe, framesize + redsize);

        System.out.println("\nEnter the Received Frame: ");
        int[] rcvframe = new int[framesize + redsize];
        for (int i = 0; i < framesize + redsize; i++) {
            rcvframe[i] = n.nextInt();
        }

        System.out.println("\nReceiver's side: ");
        System.out.print("Received Frame: ");
        printArray(rcvframe, framesize + redsize);

        int[] receivedRemainder = calculateRemainder(rcvframe, g);

        System.out.print("\nRemainder: ");
        printArray(receivedRemainder, redsize);

        boolean isError = false;
        for (int bit : receivedRemainder) {
            if (bit != 0) {
                isError = true;
                break;
            }
        }

        if (!isError) {
            System.out.println("\nSince Remainder Of Division is '0', No Corruption Occurs.");
        } else {
            System.out.println("\nCorruption or error detected during Transmission.");
        }
    }
}
