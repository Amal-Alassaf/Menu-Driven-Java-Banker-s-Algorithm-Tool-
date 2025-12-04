package bankeralgorithm_interfaca;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class BankerAlgorithm_Interfaca extends Application {

    GridPane gridMax = null;
    GridPane gridAllocation = null;
    GridPane gridAvailable = null;

    BankerSystem s = new BankerSystem();

    @Override
    public void start(Stage primaryStage) {//start page method

        Label titel = new Label("Banker Algorithm");
        titel.setTextFill(Color.ORANGERED);
        titel.setAlignment(Pos.CENTER);

        Button bt1 = new Button("Start");

        BorderPane border = new BorderPane();
        border.setTop(titel);
        BorderPane.setAlignment(titel, Pos.CENTER);
        border.setCenter(bt1);
        BorderPane.setAlignment(bt1, Pos.CENTER);

        StackPane stack = new StackPane(border);

        Scene scene = new Scene(stack, 300, 300);

        bt1.setOnAction(e -> {
            primaryStage.close();
            secondPage(primaryStage);
        });

        primaryStage.setTitle("Banker Algorithm");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void secondPage(Stage primaryStage) {//the main interface

        //Creating and arranging labels, buttons, and text fields in Vbox, Hbox, etc.
        Label titel = new Label("Banker Algorithm");
        titel.setTextFill(Color.ORANGERED);
        titel.setAlignment(Pos.CENTER);

        Label lblprocess = new Label("Number of Processes");
        TextField txtpros = new TextField();

        Label lblresourses = new Label("Number of Resources");
        TextField txtreso = new TextField();

        Label result = new Label("");
        TextArea table = new TextArea();
        table.setEditable(false);
        table.setPrefColumnCount(60);
        table.setPrefRowCount(10);

        VBox vbox1 = new VBox(5, lblprocess, txtpros);
        VBox vbox2 = new VBox(5, lblresourses, txtreso);
        HBox topInput = new HBox(80, vbox1, vbox2);
        topInput.setAlignment(Pos.CENTER);

        Label lblMax = new Label("Max");
        Label lblAllocation = new Label("Allocation");
        Label lblAvailable = new Label("Available");
        HBox hbox2 = new HBox(400, lblMax, lblAllocation, lblAvailable);
        hbox2.setAlignment(Pos.CENTER);

        HBox matrix = new HBox(50);
        matrix.setAlignment(Pos.CENTER);

        Label lblPID = new Label("Process ID:");
        lblPID.setVisible(false);
        TextField txtPID = new TextField();
        txtPID.setVisible(false);

        Label lblVector = new Label("Vector (comma separated):");
        lblVector.setVisible(false);
        TextField txtVector = new TextField();
        txtVector.setVisible(false);

        Button btGenerate = new Button("Generate");

        btGenerate.setOnAction(e -> {//action on generate button to generate the matrices
            matrix.getChildren().clear();//The matrix is ​​cleaned before it starts creating a new one.
            int processes = Integer.parseInt(txtpros.getText());
            int resources = Integer.parseInt(txtreso.getText());
            
            //validate process number to not be grater than 5
            if (processes > 5) {
                result.setText("Error: The number of processes can not exceed 5");
                result.setTextFill(Color.RED);
                return;
            }

            //validate resource number to not be grater than 3
            if (resources > 3) {
                result.setText("Error: The number of resources can not exceed 3");
                result.setTextFill(Color.RED);
                return;
            }
            
            //validate process number to not be negateive or zero
            if (processes < 1) {
                result.setText("Error: The number of processes can not be less than 1 or negative");
                result.setTextFill(Color.RED);
                return;
            }

            //validate resource number to not be negateive 
            if (resources < 0) {
                result.setText("Error: The number of resources can not be negative");
                result.setTextFill(Color.RED);
                return;
            }

            gridMax = creatMatrix(processes, resources);
            gridAllocation = creatMatrix(processes, resources);
            gridAvailable = creatMatrix(1, resources);

            matrix.getChildren().addAll(gridMax, gridAllocation, gridAvailable);

        });

        Button processing = new Button("Process");
        Button reset = new Button("Reset");
        Button back = new Button("Back");
        Button auto = new Button("Auto");
        Button request = new Button("Request");
        Button release = new Button("Release");

        auto.setOnAction(e -> {//this button auto fill the matrix
            txtpros.setText("3");
            txtreso.setText("3");

            int processes = 3;
            int resources = 3;

            gridMax = creatMatrix(processes, resources);
            gridAllocation = creatMatrix(processes, resources);
            gridAvailable = creatMatrix(1, resources);

            matrix.getChildren().addAll(gridMax, gridAllocation, gridAvailable);

            int[][] max = {
                {7, 5, 3},
                {3, 2, 2},
                {9, 0, 2}
            };

            int[][] allocation = {
                {0, 1, 0},
                {2, 0, 0},
                {3, 0, 2}
            };

            int[][] available = {
                {3, 3, 2}
            };

            fillGride(gridMax, max);
            fillGride(gridAllocation, allocation);
            fillGride(gridAvailable, available);

        });

        reset.setOnAction(e -> {//this button cleans the interface
            txtpros.clear();
            txtreso.clear();

            matrix.getChildren().clear();

            result.setText("");

            table.setText("");

            lblPID.setVisible(false);
            txtPID.setVisible(false);
            lblVector.setVisible(false);
            txtVector.setVisible(false);

            txtPID.setText("");
            txtVector.setText("");

        });

        processing.setOnAction(e -> {//processing the unsafe, safe state,printing the safe sequense, table with need, and shows the error messages

            int processes = Integer.parseInt(txtpros.getText());
            int resources = Integer.parseInt(txtreso.getText());

            int[] safeOut;

            //chicking if the p <=5 and the r <=3
            if (processes > 5) {
                result.setText("Error: The number of processes can not exceed 5");
                result.setTextFill(Color.RED);
                return;
            }

            if (resources > 3) {
                result.setText("Error: The number of resources can not exceed 3");
                result.setTextFill(Color.RED);
                return;
            }

            if (processes < 1) {
                result.setText("Error: The number of processes can not exceed 5");
                result.setTextFill(Color.RED);
                return;
            }

            if (resources < 0) {
                result.setText("Error: The number of resources can not exceed 3");
                result.setTextFill(Color.RED);
                return;
            }

            //chicking if the user filled all the cells
            for (Node n : gridMax.getChildren()) {
                TextField tf = (TextField) n;
                if (tf.getText().trim().isEmpty()) {
                    result.setText("Error: Please fill ALL Max cells");
                    result.setTextFill(Color.RED);
                    return;
                }
                try {
                    int value = Integer.parseInt(tf.getText().trim());
                    if (value < 0) {
                        result.setText("Error: Max values cannot be negative");
                        result.setTextFill(Color.RED);
                        return;
                    }
                } catch (Exception ex) {
                    result.setText("Error: Max must contain NUMBERS only");
                    result.setTextFill(Color.RED);
                    return;
                }

            }//max

            for (Node n : gridAllocation.getChildren()) {
                TextField tf = (TextField) n;
                if (tf.getText().trim().isEmpty()) {
                    result.setText("Error: Please fill all Allocation cells");
                    result.setTextFill(Color.RED);
                    return;
                }
                try {
                    int value = Integer.parseInt(tf.getText().trim());
                    if (value < 0) {
                        result.setText("Error: Allocation values cannot be negative");
                        result.setTextFill(Color.RED);
                        return;
                    }
                } catch (Exception ex) {
                    result.setText("Error: Allocation must contain NUMBERS only");
                    result.setTextFill(Color.RED);
                    return;
                }

            }//Allocation

            for (Node n : gridAvailable.getChildren()) {
                TextField tf = (TextField) n;
                if (tf.getText().trim().isEmpty()) {
                    result.setText("Error: Please fill all Available cells");
                    result.setTextFill(Color.RED);
                    return;
                }
                try {
                int value = Integer.parseInt(tf.getText().trim());
                if (value < 0) {
                    result.setText("Error: Available values cannot be negative");
                    result.setTextFill(Color.RED);
                    return;
                }
            } catch (Exception ex) {
                result.setText("Error: Available must contain NUMBERS only");
                result.setTextFill(Color.RED);
                return;
            }
        
            }//Available

            int[][] max = readMatrix(gridMax, processes, resources);
            int[][] alloc = readMatrix(gridAllocation, processes, resources);
            int[] avail = readArray(gridAvailable, resources);
            int[] safeSeq = new int[processes];
            int[][] need = s.calculateNeedMatrix(max, alloc, processes, resources);

            BankerSystem.P = processes;
            BankerSystem.R = resources;

            boolean safe = s.saftyCheck(avail, max, alloc, need, safeSeq);

            if (!safe) {
                result.setText("UNSAFE STATE – No Safe Sequence");
                result.setTextFill(Color.RED);
            } else {
                String seq = "";
                for (int i = 0; i < processes; i++) {
                    seq += "P" + safeSeq[i];
                    if (i < processes - 1) {
                        seq += " , ";
                    }
                }
                result.setText("SAFE STATE\nSafe Sequence: < " + seq + " >");
                result.setTextFill(Color.GREEN);
            }

            String tablePrint = printTable(processes, resources, avail, max, alloc, need);
            table.setText(tablePrint);

        }
        );

        request.setOnAction(e-> {//handel the process request

            lblPID.setVisible(true);
            txtPID.setVisible(true);
            lblVector.setVisible(true);
            txtVector.setVisible(true);

            //if the field empty show error message
            if (txtPID.getText().trim().isEmpty() || txtVector.getText().trim().isEmpty()) {
                table.setText("Please enter Process ID and Request vector (e.g. 1,0,2) then click Request again.");
                return;
            }

            int processes = Integer.parseInt(txtpros.getText());
            int resources = Integer.parseInt(txtreso.getText());

            BankerSystem.P = processes;
            BankerSystem.R = resources;

            int[][] max = readMatrix(gridMax, processes, resources);
            int[][] alloc = readMatrix(gridAllocation, processes, resources);
            int[] avail = readArray(gridAvailable, resources);
            int[][] need = s.calculateNeedMatrix(max, alloc, processes, resources);

            int[] safeSeq = new int[processes];

            int pid;
            try {
                //if the process id is not a number
                pid = Integer.parseInt(txtPID.getText().trim());
            } catch (Exception ex) {
                table.setText("Error: Process ID must be a number");
                return;
            }

            //if procees id is negative or grater than the number of resources
            if (pid < 0 || pid >= processes) {
                table.setText("Error: Process ID must be between 0 and " + (processes - 1));
                return;
            }

            //if the resourse number entered is grater than the number of resources
            String[] parts = txtVector.getText().split(",");
            if (parts.length != resources) {
                table.setText("Error: Request vector size must be " + resources + " values.");
                return;
            }

            int[] requestVec = new int[resources];
            
            //if the vector entered is not a number
            try {
                for (int i = 0; i < resources; i++) {
                    requestVec[i] = Integer.parseInt(parts[i].trim());
                }
            } catch (Exception ex) {
                table.setText("Error: Vector must contain numbers only");
                return;
            }

            boolean granted = s.requestResources(pid, requestVec, avail, max, alloc, need, safeSeq);

            if (!granted) {
                table.setText("REQUEST DENIED\nSystem would become UNSAFE or exceeds need/available.");
            } else {
                String seq = "";
                for (int i = 0; i < processes; i++) {
                    seq += "P" + safeSeq[i];
                    if (i < processes - 1) {
                        seq += ", ";
                    }
                }

                String tablePrint = printTable(processes, resources, avail, max, alloc, need);

                table.setText("REQUEST GRANTED\nSafe Sequence: <" + seq + ">\n\n" + tablePrint);
            }

        }
        );

        release.setOnAction(e-> { //handel the process release

            lblPID.setVisible(true);
            txtPID.setVisible(true);
            lblVector.setVisible(true);
            txtVector.setVisible(true);

            //if the field empty show error message
            if (txtPID.getText().trim().isEmpty() || txtVector.getText().trim().isEmpty()) {
                table.setText("Please enter Process ID and Request vector (e.g. 1,0,2) then click Request again.");
                return;
            }

            int processes = Integer.parseInt(txtpros.getText());
            int resources = Integer.parseInt(txtreso.getText());

            int[][] max = readMatrix(gridMax, processes, resources);
            int[][] alloc = readMatrix(gridAllocation, processes, resources);
            int[] avail = readArray(gridAvailable, resources);

            int[][] need = s.calculateNeedMatrix(max, alloc, processes, resources);

            BankerSystem.P = processes;
            BankerSystem.R = resources;

            int pid;
            try {
                //if the process id is not a number
                pid = Integer.parseInt(txtPID.getText());
            } catch (Exception ex) {
                table.setText("Error: Process ID must be a number");
                return;
            }

            //if the vector entered not matches resource number
            String[] parts = txtVector.getText().split(",");
            if (parts.length != resources) {
                table.setText("Error: Vector size must match number of resources");
                return;
            }

            //if the input is not a number
            int[] releaseVec = new int[resources];
            for (int i = 0; i < resources; i++) {
                try {
                    releaseVec[i] = Integer.parseInt(parts[i].trim());
                } catch (Exception ex) {
                    table.setText("Error: Vector must contain numbers");
                    return;
                }
            }

            boolean valid = true;
            for (int i = 0; i < resources; i++) {
                if (releaseVec[i] > alloc[pid][i]) {
                    valid = false;
                }
            }

            if (!valid) {
                table.setText("RELEASE DENIED\nProcess cannot release more than allocated.");
                return;
            }

            for (int i = 0; i < resources; i++) {
                avail[i] += releaseVec[i];     // Add released resources back to the system
                alloc[pid][i] -= releaseVec[i];// Reduce allocated resources of the process
                need[pid][i] += releaseVec[i]; // Increase need since process gave back resources
            }

            String tablePrint = printTable(processes, resources, avail, max, alloc, need);

            table.setText("RELEASE COMPLETED\nUpdated System State:\n\n" + tablePrint);

        }
        );

        back.setOnAction(e
                -> {
            primaryStage.close();
            start(primaryStage);

        }
        );

        HBox reqRel = new HBox(20, lblPID, txtPID, lblVector, txtVector);
        reqRel.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(30, processing, request, release, auto, reset);
        buttons.setAlignment(Pos.CENTER);

        BorderPane.setAlignment(back, Pos.BOTTOM_RIGHT);

        VBox centerContent = new VBox(30, topInput, btGenerate, hbox2, matrix, reqRel, buttons, result, table);
        centerContent.setAlignment(Pos.TOP_CENTER);

        BorderPane pane = new BorderPane();
        pane.setTop(titel);
        BorderPane.setAlignment(titel, Pos.CENTER);
        pane.setCenter(centerContent);
        pane.setBottom(back);
        
        StackPane stack = new StackPane(pane);
        
        Scene scene = new Scene(stack, 1000, 690);
        primaryStage.setTitle("Banker Algorithm");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }//main

    GridPane creatMatrix(int row, int col) {//dynamiclly generate the matrix
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                TextField tx = new TextField();
                grid.add(tx, j, i);
            }
        }

        grid.setAlignment(Pos.CENTER);
        return grid;
    }//creatMatrix

    void fillGride(GridPane grid, int[][] arr) {//for auto fill the matrix

        int count = 0;

        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {

                Node node = grid.getChildren().get(count);

                if (node instanceof TextField) {//checking the type
                    TextField tf = (TextField) node;
                    tf.setText(String.valueOf(arr[r][c]));
                }

                count++;
            }
        }

    }//fillGride

    int[][] readMatrix(GridPane grid, int r, int c) {//read the input in the matrix
        int[][] arr = new int[r][c];
        int index = 0;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {

                TextField tf = (TextField) grid.getChildren().get(index);
                arr[i][j] = Integer.parseInt(tf.getText());

                index++;
            }
        }
        return arr;
    }//readMatrix

    int[] readArray(GridPane grid, int size) { //read the input in the array
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            TextField tf = (TextField) grid.getChildren().get(i);
            arr[i] = Integer.parseInt(tf.getText());
        }

        return arr;

    }//readArray

    public String printTable(int P, int R, int[] avail, int[][] max, int[][] alloc, int[][] need) {//printinf a complete table

        StringBuilder sb = new StringBuilder();

        sb.append("Available:\n");
        for (int i = 0; i < P; i++) {
            sb.append("R" + i + ": " + avail[i] + "   ");
        }
        sb.append("\n\n");

        sb.append("Process | Allocation | Max | Need\n");
        sb.append("------------------------------------------\n");

        for (int p = 0; p < P; p++) {

            String allocStr = "";
            String maxStr = "";
            String needStr = "";

            for (int j = 0; j < R; j++) {
                allocStr += alloc[p][j] + " ";
                maxStr += max[p][j] + " ";
                needStr += need[p][j] + " ";
            }

            sb.append("P" + p + "     | ");
            sb.append(allocStr + " | ");
            sb.append(maxStr + " | ");
            sb.append(needStr + "\n");
        }

        return sb.toString();

    }//printTable

}//class
