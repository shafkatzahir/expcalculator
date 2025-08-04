package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Custom exception class for exponentiation-related errors.
 */
class ExponentiationException extends Exception {
    public ExponentiationException(String message) {
        super(message);
    }
}

/**
 * Custom exception class for overflow errors during calculation.
 */
class OverflowException extends ExponentiationException {
    public OverflowException(String message) {
        super(message);
    }
}

/**
 * An accessible JavaFX application that calculates integer exponentiation (x^y).
 *
 * @author Gemini
 * @version 1.0.0
 */
public class HelloApplication extends Application {

    private TextField baseField;
    private TextField exponentField;
    private Label resultLabel;
    private TextArea logArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Integer Exponentiation Calculator (x^y)");

        // Create the main layout container
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Title
        Label titleLabel = new Label("Integer Exponentiation Function");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Input section using a GridPane for proper alignment
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setAlignment(Pos.CENTER);

        // --- UI & ACCESSIBILITY IMPROVEMENTS ---
        Label baseLabel = new Label("Base (x):");
        baseLabel.setAlignment(Pos.CENTER_RIGHT); // UI PRINCIPLE: Right-align for proximity
        baseField = new TextField();
        baseField.setPromptText("Enter integer base");
        baseField.setPrefWidth(150);
        baseField.setTooltip(new Tooltip("The base integer 'x'")); // ACCESSIBILITY: Tooltip
        baseLabel.setLabelFor(baseField); // ACCESSIBILITY: Link label to input

        Label exponentLabel = new Label("Exponent (y):");
        exponentLabel.setAlignment(Pos.CENTER_RIGHT); // UI PRINCIPLE: Right-align for proximity
        exponentField = new TextField();
        exponentField.setPromptText("Enter integer exponent");
        exponentField.setPrefWidth(150);
        exponentField.setTooltip(new Tooltip("The exponent integer 'y'")); // ACCESSIBILITY: Tooltip
        exponentLabel.setLabelFor(exponentField); // ACCESSIBILITY: Link label to input

        inputGrid.add(baseLabel, 0, 0);
        inputGrid.add(baseField, 1, 0);
        inputGrid.add(exponentLabel, 0, 1);
        inputGrid.add(exponentField, 1, 1);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button calculateButton = new Button("_Calculate x^y"); // ACCESSIBILITY: Mnemonic (Alt+C)
        calculateButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        calculateButton.setOnAction(e -> performCalculation());
        calculateButton.setTooltip(new Tooltip("Calculate the result (Alt+C)"));
        calculateButton.setDefaultButton(true); // Allow Enter key to trigger calculation globally

        Button clearButton = new Button("C_lear"); // ACCESSIBILITY: Mnemonic (Alt+L)
        clearButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        clearButton.setOnAction(e -> clearFields());
        clearButton.setTooltip(new Tooltip("Clear all fields and logs (Alt+L)"));
        clearButton.setCancelButton(true); // Allow Esc key to trigger clear

        buttonBox.getChildren().addAll(calculateButton, clearButton);

        // Result display
        resultLabel = new Label("Result will appear here");
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2E8B57;");
        resultLabel.setWrapText(true);

        // Log area for detailed information
        Label logLabel = new Label("Calculation Log:");
        logArea = new TextArea();
        logArea.setPrefRowCount(6);
        logArea.setEditable(false);
        logArea.setStyle("-fx-font-family: monospace;");

        // Add all components to root
        root.getChildren().addAll(titleLabel, new Separator(), inputGrid, buttonBox,
                new Separator(), resultLabel, logLabel, logArea);

        // Create scene and show stage
        Scene scene = new Scene(root, 450, 520);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Handles the primary calculation logic when the button is clicked.
     * Parses input, calls the exponentiation function, and handles exceptions.
     */
    private void performCalculation() {
        try {
            resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2E8B57;");

            String baseText = baseField.getText().trim();
            String exponentText = exponentField.getText().trim();

            if (baseText.isEmpty() || exponentText.isEmpty()) {
                showError("Please enter both base and exponent values.");
                return;
            }

            int base = Integer.parseInt(baseText);
            int exponent = Integer.parseInt(exponentText);

            logArea.appendText("Calculating: " + base + "^" + exponent + "\n");
            long result = exponentiation(base, exponent);

            resultLabel.setText("Result: " + base + "^" + exponent + " = " + result);
            logArea.appendText("Success: " + result + "\n");

        } catch (NumberFormatException e) {
            showError("Invalid input: Please enter valid integers only.");
            logArea.appendText("Error: Invalid number format\n");
        } catch (OverflowException e) {
            showError("Overflow Error: " + e.getMessage());
            logArea.appendText("Error: " + e.getMessage() + "\n");
        } catch (ExponentiationException e) {
            showError("Math Error: " + e.getMessage());
            logArea.appendText("Error: " + e.getMessage() + "\n");
        } catch (Exception e) {
            showError("Unexpected error occurred: " + e.getMessage());
            logArea.appendText("Unexpected error: " + e.getMessage() + "\n");
        } finally {
            logArea.appendText("-------------------\n");
        }
    }

    /**
     * Displays an error message in the result label.
     * @param message The error message to display.
     */
    private void showError(String message) {
        resultLabel.setText(message);
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #DC143C;");
    }

    /**
     * Clears all input fields, the result label, and the log area.
     */
    private void clearFields() {
        baseField.clear();
        exponentField.clear();
        resultLabel.setText("Result will appear here");
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2E8B57;");
        logArea.clear();
        baseField.requestFocus(); // Set focus back to the first input field
    }

    /**
     * Computes x raised to the power of y (x^y) for integer inputs.
     *
     * @param base The base value (x), an integer.
     * @param exponent The exponent value (y), an integer.
     * @return The result of x^y as a long.
     * @throws ExponentiationException for invalid operations like 0^-1.
     * @throws OverflowException when the result exceeds the range of a long.
     */
    public static long exponentiation(int base, int exponent) throws ExponentiationException {
        if (exponent == 0) {
            return 1; // REQ-EXP-004
        }
        if (exponent == 1) {
            return base; // REQ-EXP-005
        }
        if (base == 1) {
            return 1; // REQ-EXP-006
        }
        if (base == 0) {
            if (exponent > 0) {
                return 0; // REQ-EXP-007
            } else {
                // REQ-EXP-008: 0 to negative power
                throw new ExponentiationException("Division by zero: 0^y where y < 0 is undefined.");
            }
        }

        if (exponent < 0) {
            if (base == -1) {
                return (exponent % 2 == 0) ? 1 : -1;
            }
            // For any other integer base, a negative exponent results in a fraction.
            // In integer arithmetic, this truncates to 0.
            return 0;
        }

        return fastPowerWithOverflowCheck(base, exponent);
    }

    /**
     * Efficiently calculates power using exponentiation by squaring with overflow detection.
     *
     * @param base The base value.
     * @param exponent The positive exponent value.
     * @return The calculated power as a long.
     * @throws OverflowException if an intermediate or final calculation exceeds Long.MAX_VALUE.
     */
    private static long fastPowerWithOverflowCheck(long base, int exponent) throws OverflowException {
        long result = 1;
        long currentBase = base;
        int currentExponent = exponent;

        while (currentExponent > 0) {
            if (currentExponent % 2 == 1) { // If exponent is odd
                if (willMultiplyOverflow(result, currentBase)) {
                    throw new OverflowException("Result exceeds maximum value for long.");
                }
                result *= currentBase;
            }

            currentExponent /= 2;
            if (currentExponent > 0) {
                if (willMultiplyOverflow(currentBase, currentBase)) {
                    throw new OverflowException("Intermediate value exceeds maximum value for long.");
                }
                currentBase *= currentBase;
            }
        }
        return result;
    }

    /**
     * Checks if multiplying two long values would cause an overflow.
     *
     * @param a The first long value.
     * @param b The second long value.
     * @return True if `a * b` would overflow, false otherwise.
     */
    private static boolean willMultiplyOverflow(long a, long b) {
        if (a == 0 || b == 0) {
            return false;
        }
        // Using Math.abs() per SonarLint suggestion for clarity
        long absA = Math.abs(a);
        long absB = Math.abs(b);
        if (absA > Long.MAX_VALUE / absB) {
            return true;
        }
        // Specific check for Long.MIN_VALUE case
        if (a == Long.MIN_VALUE || b == Long.MIN_VALUE) {
            return (a == -1 && b == Long.MIN_VALUE) || (b == -1 && a == Long.MIN_VALUE);
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}