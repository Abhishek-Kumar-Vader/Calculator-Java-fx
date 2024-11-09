package com.example.calc1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CalculatorApp extends Application {

    private TextField display;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Calculator");

        // Create the display (TextField)
        display = new TextField();
        display.setEditable(false);
        display.setStyle("-fx-font-size: 20px;");
        display.setPrefHeight(50);

        // Create buttons for numbers and operations
        Button[] numButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            numButtons[i] = new Button(String.valueOf(i));
            numButtons[i].setStyle("-fx-font-size: 20px;");
        }

        Button addButton = new Button("+");
        Button subButton = new Button("-");
        Button mulButton = new Button("*");
        Button divButton = new Button("/");
        Button equalsButton = new Button("=");
        Button clearButton = new Button("C");
        Button decimalButton = new Button(".");

        // Setting the style for buttons
        addButton.setStyle("-fx-font-size: 20px;");
        subButton.setStyle("-fx-font-size: 20px;");
        mulButton.setStyle("-fx-font-size: 20px;");
        divButton.setStyle("-fx-font-size: 20px;");
        equalsButton.setStyle("-fx-font-size: 20px;");
        clearButton.setStyle("-fx-font-size: 20px;");
        decimalButton.setStyle("-fx-font-size: 20px;");

        // Creating the GridPane for the calculator layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        // Adding buttons to the grid
        grid.add(display, 0, 0, 4, 1);
        grid.add(numButtons[1], 0, 1);
        grid.add(numButtons[2], 1, 1);
        grid.add(numButtons[3], 2, 1);
        grid.add(addButton, 3, 1);

        grid.add(numButtons[4], 0, 2);
        grid.add(numButtons[5], 1, 2);
        grid.add(numButtons[6], 2, 2);
        grid.add(subButton, 3, 2);

        grid.add(numButtons[7], 0, 3);
        grid.add(numButtons[8], 1, 3);
        grid.add(numButtons[9], 2, 3);
        grid.add(mulButton, 3, 3);

        grid.add(numButtons[0], 0, 4);
        grid.add(decimalButton, 1, 4);
        grid.add(equalsButton, 2, 4);
        grid.add(divButton, 3, 4);

        grid.add(clearButton, 0, 5, 4, 1);

        // Handling button clicks
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            numButtons[i].setOnAction(event -> appendToDisplay(String.valueOf(finalI)));
        }

        addButton.setOnAction(event -> appendToDisplay("+"));
        subButton.setOnAction(event -> appendToDisplay("-"));
        mulButton.setOnAction(event -> appendToDisplay("*"));
        divButton.setOnAction(event -> appendToDisplay("/"));
        decimalButton.setOnAction(event -> appendToDisplay("."));
        equalsButton.setOnAction(event -> calculateResult());
        clearButton.setOnAction(event -> clearDisplay());

        // Create scene and set it on the stage
        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Append text to the display
    private void appendToDisplay(String text) {
        display.appendText(text);
    }

    // Calculate the result of the expression
    private void calculateResult() {
        String expression = display.getText().trim();

        if (expression.isEmpty()) {
            display.setText("Error: Empty expression");
            return;
        }

        try {
            double result = evaluateExpression(expression);
            display.setText(String.valueOf(result));
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    // Evaluate the mathematical expression using a basic algorithm
    private double evaluateExpression(String expression) {
        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        // Parse numbers and operators
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                currentNumber.append(ch);
            } else {
                numbers.add(Double.parseDouble(currentNumber.toString()));
                currentNumber.setLength(0);
                operators.add(ch);
            }
        }
        // Add the last number
        numbers.add(Double.parseDouble(currentNumber.toString()));

        // Perform multiplication and division first
        for (int i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            if (op == '*' || op == '/') {
                double left = numbers.remove(i);
                double right = numbers.remove(i);
                double result = (op == '*') ? left * right : left / right;
                numbers.add(i, result);
                operators.remove(i);
                i--;
            }
        }

        // Perform addition and subtraction
        while (!operators.isEmpty()) {
            char op = operators.remove(0);
            double left = numbers.remove(0);
            double right = numbers.remove(0);
            double result = (op == '+') ? left + right : left - right;
            numbers.add(0, result);
        }

        return numbers.get(0);
    }

    // Clear the display
    private void clearDisplay() {
        display.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
