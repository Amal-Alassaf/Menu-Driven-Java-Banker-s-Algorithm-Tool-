package banker_cli1;

import java.util.Scanner;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "banker", description = "Banker Algorithm CLI", version = "banker 1.0", mixinStandardHelpOptions = true)
public class Banker_CLI1 implements Runnable {

    @Option(names = "--p", description = "Number of processes", required = true)
    int numOfProcess;

    @Option(names = "--r", description = "Number of resources", required = true)
    int numOfResources;

    @Option(names = "--max", description = "Max matrix rows separated by ';' and columns by ','")
    String maxString;

    @Option(names = "--alloc", description = "Allocation matrix rows separated by ';'")
    String allocString;

    @Option(names = "--avail", description = "Available resources comma-separated")
    String availString;

    public static void main(String[] args) {

        int exitCode = new CommandLine(new Banker_CLI1()).execute(args);
        System.exit(exitCode);
    }//main

    public void run() {

        //validate process
        if (numOfProcess <= 0) {
            System.out.println("ERROR: Number of processes cannot be negative or zero.");
            return;
        }

        if (numOfProcess > 5) {
            System.out.println("ERROR: Maximum allowed processes is 5.");
            return;
        }

        //validate resources
        if (numOfResources < 0) {
            System.out.println("ERROR: Number of resources cannot be negative.");
            return;
        }

        if (numOfResources > 3) {
            System.out.println("ERROR: Maximum allowed resources is 3.");
            return;
        }

        int[][] max;
        int[][] alloc;
        int[] avail;

        //throw an error if the input is negative
        try {
            max = parseMatrix(maxString, numOfProcess, numOfResources);
            alloc = parseMatrix(allocString, numOfProcess, numOfResources);
            avail = parseVector(availString, numOfResources);
            
        } catch (Exception e) {
            System.out.println("\nERROR: Invalid input format! Please check matrices and vectors.");
            return;   // stop run
        }

        BankerSystem1 s = new BankerSystem1();

        BankerSystem1.P = numOfProcess;
        BankerSystem1.R = numOfResources;

        //calculate need matrix
        int[][] need = s.calculateNeedMatrix(max, alloc, numOfProcess, numOfResources);

        printTable(numOfProcess, numOfResources, avail, max, alloc, need);

        int[] safeOut = new int[numOfProcess];
        boolean safe = s.saftyCheck(avail, max, alloc, need, safeOut);

        if (!safe) {
            System.out.println("UNSAFE STATE — No safe sequence exists.");
        } else {
            System.out.print("SAFE STATE. Safe Sequence: < ");
            for (int i = 0; i < numOfProcess; i++) {
                System.out.print("P" + safeOut[i]);
                if (i < numOfProcess - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" >");

        }

        //request
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nDo you want to request resources? (y/n): ");

        //if you enter Y or y it will accepted
        if (scanner.next().equalsIgnoreCase("y")) {
            scanner.nextLine();
            BankerSystem1.handleRequest(scanner, avail, max, alloc, need);
            printTable(numOfProcess, numOfResources, avail, max, alloc, need);
        }

        //releas
        System.out.print("\nDo you want to RELEASE resources? (y/n): ");
        if (scanner.next().equalsIgnoreCase("y")) {
            scanner.nextLine();
            BankerSystem1.handleRelease(scanner, avail, max, alloc, need);
            printTable(numOfProcess, numOfResources, avail, max, alloc, need);
        }

    }//end run

    //method for read [][] matrix
    public int[][] parseMatrix(String input, int rows, int cols) {

        String[] rowStrings = input.split(";");
        if (rowStrings.length != rows) {
            throw new IllegalArgumentException("Matrix rows mismatch");
        }

        int[][] matrix = new int[rows][cols];

        //Devide the rows into columns
        for (int i = 0; i < rows; i++) {
            String[] colStrings = rowStrings[i].split(",");

            if (colStrings.length != cols) {
                throw new IllegalArgumentException("Matrix columns mismatch");
            }

            //It takes the column values, removes the spaces, and stores the values ​​in their correct location
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Integer.parseInt(colStrings[j].trim());
            }
        }

        return matrix;
    }//parseMatrix

    //method for read 1D array
    public int[] parseVector(String s, int size) {
        String[] parts = s.split(",");
        if (parts.length != size) {
            throw new IllegalArgumentException("Available vector size mismatch");
        }

        //stores the values 
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }
        return arr;
    }//parseVector

    //method for printing the table
    public void printTable(int P, int R, int[] avail, int[][] max, int[][] alloc, int[][] need) {

        System.out.print("\nAvailable: [ ");
        for (int i = 0; i < R; i++) {
            System.out.print("R" + i + "=" + avail[i]);
            if (i < R - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" ]\n");

        System.out.println("Process  | Allocation       | Max             | Need");
        System.out.println("--------------------------------------------------------------");

        for (int p = 0; p < P; p++) {
            System.out.print("P" + p + "       | ");

            // Allocation
            for (int j = 0; j < R; j++) {
                System.out.print(alloc[p][j] + " ");
            }

            System.out.print("           | ");

            // Max
            for (int j = 0; j < R; j++) {
                System.out.print(max[p][j] + " ");
            }

            System.out.print("           | ");

            // Need
            for (int j = 0; j < R; j++) {
                System.out.print(need[p][j] + " ");
            }

            System.out.println();
        }
    }

}//class
