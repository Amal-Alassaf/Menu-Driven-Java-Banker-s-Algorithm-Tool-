package bankeralgorithm_interfaca;

import java.util.*;

public class BankerSystem {

    static int P;
    static int R;

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        menu(scanner, null, null, null, null);
//
//    }


    public static int[][] AllocationMatrix(Scanner input, int Processes, int Resources, int[][] max) {
        int[][] allocation = new int[Processes][Resources];

        System.out.println("\nEnter initial ALLOCATION matrix");

        // Iterate through each process
        for (int i = 0; i < Processes; i++) {

            while (true) {
                try {
                    // Ask user to enter one full row (space-separated)
                    System.out.print("Allocated to Process P" + i + " (R0, R1, ...): ");

                    // Read entire line
                    String line = input.nextLine().trim();

                    // If empty (happens after nextInt), read again
                    if (line.isEmpty()) {
                        line = input.nextLine().trim();
                    }

                    // Split values by spaces
                    String[] parts = line.split(" ");

                    // Validate count
                    if (parts.length != Resources) {
                        System.out.println("Please enter exactly " + Resources + " values.");
                        continue;
                    }

                    // Convert each value
                    for (int j = 0; j < Resources; j++) {
                        int value = Integer.parseInt(parts[j]);

                        if (value < 0) {
                            throw new IllegalArgumentException("Number of resources can NOT be negative");
                        }

                        if (value > max[i][j]) {
                            throw new IllegalArgumentException(
                                    "Allocation can NOT exceed maximum demand (Max[" + i + "][" + j + "] is " + max[i][j] + ")."
                            );
                        }

                        allocation[i][j] = value;
                    }

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("Please enter whole numbers separated by spaces!");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return allocation;
    }// end allocation method 

    public static int[][] calculateNeedMatrix(int[][] max, int[][] allocation, int Processes, int Resources) {
        int[][] need = new int[Processes][Resources];

        for (int i = 0; i < Processes; i++) {
            for (int j = 0; j < Resources; j++) {
                need[i][j] = max[i][j] - allocation[i][j]; // calculate need matrix

                if (need[i][j] < 0) {
                    throw new IllegalArgumentException("Need value cannot be negative!");
                }
            }
        }

        return need;
    }//end need method

    public static int getNumberOfProcesses(Scanner scanner) {
        int numProcessorThread = 1;

        // loop until valid input is provided
        while (true) {
            try {
                System.out.print("Enter the number of Processes or Threads (N): ");
                numProcessorThread = scanner.nextInt();

                // Check: value must be > 0
                if (numProcessorThread <= 0) {
                    throw new IllegalArgumentException("Number of Processes/Threads should be greater than zero !");

                    // Check: value must be <= 5
                } else if (numProcessorThread > 5) {
                    throw new IllegalArgumentException("Number of Processes/Threads should be 5 or less");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                // Error: user entered non-integer values
                System.out.println("Try again \nTo verify using Banker`s Algorithm \nPlease enter \"a whole number\"");
                scanner.next();
            } catch (IllegalArgumentException e) {
                // Display custom validation messages
                System.out.println(e.getMessage());
            }
        }
        return numProcessorThread;
    }//end processes method

    public static int getNumberOfResources(Scanner scanner) {
        int numResources = 1; //to store user input

        // loop until valid input is received
        while (true) {
            try {
                System.out.print("Enter number of resource types (M): ");
                numResources = scanner.nextInt();

                // Check: must be > 0
                if (numResources <= 0) {
                    throw new IllegalArgumentException("The number of resource types must be more than 0");

                    // Check: value must be <= 3
                } else if (numResources > 3) {
                    throw new IllegalArgumentException("The number of resource types must be 3 or less");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                // Error: non-integer input
                System.out.println("Try again \nTo verify from Banker`s Algorithm \nPlease enter \"integer\"");
                scanner.next(); // clear invalid input
            } catch (IllegalArgumentException e) {
                // Display validation message
                System.out.println(e.getMessage());
            }
        }
        return numResources;
    }//end resources method

    public static int[] readAvailable(Scanner scanner, int numResources) {

        int[] available = new int[numResources];

        // Availble array
        System.out.println("Enter initial AVAILABLE resources: ");

        for (int i = 0; i < numResources; i++) {

            try {
                // Ask user to enter value for each resource
                System.out.print("Resource R" + i + " instances: ");
                available[i] = scanner.nextInt();

                // Check if value is negative
                if (available[i] < 0) {
                    throw new IllegalArgumentException("Number of instances can NOT be negative");
                }

            } catch (InputMismatchException e) {
                // User entered something not integer
                System.out.println("Try again, please enter a whole number.");
                scanner.next(); // clear invalid input

            } catch (IllegalArgumentException e) {
                // Handles negative values
                System.out.println(e.getMessage());
            }
        }

        return available;
    } //end available method 

    public static int[][] readMaxMatrix(Scanner scanner, int numProcessorThreads, int numResources) {

        int[][] max = new int[numProcessorThreads][numResources];

        // Max matrix
        System.out.println("\nEnter MAX demand matrix:");

        for (int i = 0; i < numProcessorThreads; i++) {

            while (true) {
                try {
                    // Ask user to enter one full row (space-separated)
                    System.out.print("Max demand for Process P" + i + " (R0, R1, R2 ,...): ");

                    // Read entire line
                    String line = scanner.nextLine().trim();

                    // If empty (happens after nextInt), read again
                    if (line.isEmpty()) {
                        line = scanner.nextLine().trim();
                    }

                    // Split values by spaces
                    String[] parts = line.split(" ");

                    // Validate count
                    if (parts.length != numResources) {
                        System.out.println("Please enter exactly " + numResources + " values.");
                        continue;
                    }

                    // Convert each value
                    for (int j = 0; j < numResources; j++) {
                        int value = Integer.parseInt(parts[j]);

                        if (value < 0) {
                            throw new IllegalArgumentException("Max value cannot be negative.");
                        }

                        max[i][j] = value;
                    }

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("Try again, please enter integers separated by spaces.");

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return max;
    } //end max method 

    //Method to print initial system state
    public static void printInitialState(int numProcesses, int numResources, int[] available, int[][] allocated, int[][] max, int[][] need) {

        // Print Available matrix
        System.out.print("Available: [ ");
        for (int i = 0; i < numResources; i++) {
            System.out.print("R" + i + ": " + available[i]);
            if (i < numResources - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" ]\n");

        // Print table headers exactly like the file
        System.out.println("                | Allocation |   Max    |   Need   |");
        System.out.println("Process         | R0 R1 R2  | R0 R1 R2 | R0 R1 R2 |");
        System.out.println("--------------- |-----------|----------|----------|");

        // Print each row (P0, P1, P2â€¦)
        for (int p = 0; p < numProcesses; p++) {

            // Print process label P#
            System.out.print("P" + p);

            System.out.print("             | ");

            // Allocation
            System.out.print(allocated[p][0] + "  " + allocated[p][1] + "  " + allocated[p][2] + "    | ");

            // Max
            System.out.print(max[p][0] + "  " + max[p][1] + "  " + max[p][2] + "   | ");

            // Need
            System.out.println(need[p][0] + "  " + need[p][1] + "  " + need[p][2] + "   |");
        }

        System.out.println("--------------- |-----------|----------|----------|");
    }//end orint method

    public static boolean saftyCheck(int available[], int max[][], int allocated[][], int need[][], int[] safeSequenceOut) {
        // Boolean array used to indicate which process has finished and which not 
        boolean[] finish = new boolean[P];
        // array used to store the final safe execution order if any
        int[] safeSeq = new int[P];

        // Copy the available array to a new one to work with
        int[] work = new int[available.length];
        System.arraycopy(available, 0, work, 0, available.length);

        int count = 0;

        // Loop runs until all processes are finished or no safe sequence can be found
        // a variable used to prevent infinite loop
        int iterations = 0;
        while (count < P && iterations < P * P + 1) {
            boolean found = false;
            for (int p = 0; p < P; p++) {
                // If process isn't finished, check if Need[p] <= Work
                if (finish[p] == false) {
                    int j;
                    for (j = 0; j < R; j++) {
                        // If need is more than the current available resources, break
                        if (need[p][j] > work[j]) {
                            break;
                        }
                    }

                    // when j == R, it means that all resources required by P are available (Need[P] < Work)
                    if (j == R) {
                        // Grant resources and deallocate them back to the system
                        for (int k = 0; k < R; k++) {
                            work[k] += allocated[p][k];
                        }

                        // Add to safeSeq and mark as finished
                        safeSeq[count++] = p;
                        finish[p] = true;
                        found = true;

                    }
                }
            }

            // If no process was found in this pass, the system is in an unsafe state
            if (found == false && count < P) {
                System.out.println("\nSTATE CHECK: Current state is UNSAFE");
                return false;
            }
            iterations++;
        }

        // If the loop finished and all processes completed 
        if (count == P) {
            // Copy safe sequence out
            for (int i = 0; i < P; i++) {
                safeSequenceOut[i] = safeSeq[i];
            }

            // Print safe result
            System.out.print("\nSTATE CHECK: Current state is SAFE. Safe Sequence: <");
            for (int i = 0; i < P; i++) {
                System.out.print("P" + safeSeq[i]);

                if (i < P - 1) {
                    System.out.print(", ");
                }
            }

            System.out.println(">");
            return true;
        }
        return false;
    }//end safty check method

    public static boolean requestResources(int processId, int[] request, int[] available, int[][] max, int[][] allocated, int[][] need, int[] safeSequenceOut) {

        System.out.println("\nProcessing Request for P" + processId);

        // Check: request <= need
        for (int i = 0; i < request.length; i++) {
            if (request[i] > need[processId][i]) {
                System.out.println("Error: Request exceeds maximum need.");
                return false;
            }
        }

        // Check: request <= available
        for (int i = 0; i < request.length; i++) {
            if (request[i] > available[i]) {
                System.out.println("DENIED: Insufficient available resources. " + "P" + processId + " must wait. (Because R" + i + " < " + request[i] + ")");
                return false;
            }
        }

        // Pretend allocation
        for (int i = 0; i < request.length; i++) {
            available[i] -= request[i];
            allocated[processId][i] += request[i];
            need[processId][i] -= request[i];
        }

        // Safety check 
        boolean safe = saftyCheck(available, max, allocated, need, safeSequenceOut);

        //if safe print granted message with the safe sequence
        if (safe) {
            System.out.println("GRANTED: Request by P" + processId + " approved.");
            System.out.print("New Safe Sequence: <");
            for (int i = 0; i < P; i++) {
                System.out.print("P" + safeSequenceOut[i] + (i == P - 1 ? "" : ", "));
            }
            System.out.println(">");
            return true;

        } else {
            //Undo pretend allocation (rollback) to restore the system's previous safe state
            for (int i = 0; i < request.length; i++) {
                available[i] += request[i];
                allocated[processId][i] -= request[i];
                need[processId][i] += request[i];
            }
            System.out.println("Request REJECTED (Unsafe State). Rolling back.");
            return false;
        }
    }//end request resources method

    public static void handleRequest(Scanner scanner, int[] available, int[][] max, int[][] allocated, int[][] need) {

        System.out.print("\nEnter process ID : P");
        int pID;
        // Check if value is negative
        try {
            pID = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("REQUEST DENIED: Invalid process ID.");
            scanner.nextLine();
            return;
        }

        // check processes between 0 and " + (P - 1)
        if (pID < 0 || pID >= P) {
            System.out.println("REQUEST DENIED: Process ID must be between 0 and " + (P - 1));
            return;
        }

        int R = available.length;
        int[] request = new int[R];

        // Read the new resource request values entered by the user for this process
        System.out.print("Enter request vector for P" + pID + " (R0, R1, ...): ");
        scanner.nextLine();
        String line = scanner.nextLine().trim();

        String[] parts = line.split(",");
        //if is larger than the number of resources
        if (parts.length != R) {
            System.out.println("REQUEST DENIED: You must enter exactly " + R + " values.");
            return;
        }

        //checks every number in the vector
        for (int i = 0; i < R; i++) {

            try {
                request[i] = Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException e) {
                System.out.println("REQUEST DENIED: All request values must be integers.");
                return;
            }

            if (request[i] < 0) {
                System.out.println("REQUEST DENIED: Request values cannot be negative.");
                return;
            }
        }
        // Create array to receive the safe sequence from safetyCheck
        int[] safeSequenceOut = new int[P];

        // Call updated requestResources()
        boolean approved = requestResources(
                pID, request, available, max, allocated, need, safeSequenceOut
        );

        if (approved) {
            System.out.println("\nRequest completed successfully.");
        } else {
            System.out.println("\nRequest denied.");
        }
    }//emd handleRequest method

    public static void releaseResources(int processId, int[] release,
            int[] available, int[][] allocated, int[][] need) {

        // Check that the process is not releasing more than it currently holds
        for (int i = 0; i < release.length; i++) {
            if (release[i] > allocated[processId][i]) {
                System.out.println("Error: Process cannot release more resources than it has allocated.");
                return;
            }
        }

        // Update system matrices after releasing resources
        for (int i = 0; i < release.length; i++) {
            available[i] += release[i];            // Add released resources back to the system
            allocated[processId][i] -= release[i]; // Reduce allocated resources of the process
            need[processId][i] += release[i];      // Increase need since process gave back resources
        }

        // Confirm release completed
        System.out.println("Resources released successfully.");
    }//end releaseResources method

    public static void handleRelease(Scanner scanner, int[] available, int[][] max, int[][] allocated, int[][] need) {

        // Ask user which process will release resources
        System.out.print("\nEnter process ID to release resources: P");
        int pID;

        // Check if value is negative
        try {
            pID = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("RELEASE DENIED: Invalid process ID.");
            scanner.nextLine();
            return;
        }

        // check processes between 0 and " + (P - 1)
        if (pID < 0 || pID >= P) {
            System.out.println("RELEASE DENIED: Process ID must be between 0 and " + (P - 1));
            return;
        }

        int resourceCount = available.length;
        int[] release = new int[resourceCount];

        // Take the release vector from the user 
        System.out.print("Enter release vector for P" + pID + " (R0, R1, ...): ");
        scanner.nextLine();  // consume leftover newline
        String line = scanner.nextLine().trim();

        String[] parts = line.split(",");
        if (parts.length != R) {
            System.out.println("RELEASE DENIED: You must enter exactly " + resourceCount + " values.");
            return;
        }

        for (int i = 0; i < resourceCount; i++) {
            try {
                release[i] =Integer.parseInt(parts[i].trim());
            } catch (InputMismatchException e) {
                System.out.println("RELEASE DENIED: All release values must be integers.");
                scanner.nextLine();
                return;
            }

            if (release[i] < 0) {
                System.out.println("RELEASE DENIED: Release values cannot be negative.");
                return;
            }

            //Don't release more than own
            if (release[i] > allocated[pID][i]) {
                System.out.println("RELEASE DENIED: P" + pID + " is trying to release more than allocated.");
                return;
            }

        }

        // Call the main releaseResources method to update system matrices
        releaseResources(pID, release, available, allocated, need);

        System.out.println("\nRelease completed.");
    }//end handleRelease method

    public static void menu(Scanner scanner, int[] available, int[][] max, int[][] allocated, int[][] need) {

        System.out.println("Welcome to the Banker's Algorithm Deadlock Avoidance Simulator.");

        boolean initialized = false; //becomes true only after SYSTEM INITIALIZATION

        int choice;
        int[] safeOut;

        while (true) {

            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1.  Initialize System State (Must be run first)");
            System.out.println("2.  Display Current State and Safety Status");
            System.out.println("3.  Request Resources");
            System.out.println("4.  Release Resources");
            System.out.println("5.  Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\n1- SYSTEM INITIALIZATION");

                    // Read processes
                    P = getNumberOfProcesses(scanner);

                    // Read resources
                    R = getNumberOfResources(scanner);

                    // Read available
                    available = readAvailable(scanner, R);

                    // Read max
                    max = readMaxMatrix(scanner, P, R);

                    // Read allocation
                    allocated = AllocationMatrix(scanner, P, R, max);

                    // Compute need
                    need = calculateNeedMatrix(max, allocated, P, R);

                    initialized = true; // Mark system as initialized

                    System.out.println("\nInitialization successful. Current state calculated.\n");
                    System.out.println("\n2- Current State and Safety Status ");
                    printInitialState(P, R, available, allocated, max, need);
                    // Create array for safe sequence 
                    safeOut = new int[P];

                    // Call the safety check
                    saftyCheck(available, max, allocated, need, safeOut);

                    break;

                case 2:
                    if (initialized == false) {
                        System.out.println("\nERROR: System not initialized! Please run option 1 first.");
                        break;
                    }

                    System.out.println("\n2- CURRENT STATE AND SAFETY STATUS");
                    printInitialState(P, R, available, allocated, max, need);
                    // Create array for safe sequence (

                    safeOut = new int[P];

                    // Call the safety check
                    saftyCheck(available, max, allocated, need, safeOut);
                    break;

                case 3:
                    if (initialized == false) {
                        System.out.println("\nERROR: System not initialized! Please run option 1 first.");
                        break;
                    }

                    System.out.println("\n3- RESOURCE REQUEST");
                    handleRequest(scanner, available, max, allocated, need);
                    break;

                case 4:
                    if (initialized == false) {
                        System.out.println("\nERROR: System not initialized! Please run option 1 first.");
                        break;
                    }

                    System.out.println("\n4- RELEASE RESOURCES");

                    handleRelease(scanner, available, max, allocated, need);
                    break;

                case 5:
                    System.out.println("\nExiting the simulator... Goodbye!");
                    return;

                default:
                    System.out.println("\nInvalid choice! Try again.");
            }
        }
    }//end menu method

}//end of class

