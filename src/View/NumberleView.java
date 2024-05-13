package View;// View.NumberleView.java

import Controller.NumberleController;

import Model.INumberleModel;
import Model.NumberleModel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * The NumberleView class represents the view of the Numberle game.
 * It implements the Observer interface to receive updates from the model.
 */
public class NumberleView implements Observer {
    private final INumberleModel model;
    private final NumberleController controller;
    private final JFrame frame = new JFrame("Numberle");
    private final JTextField inputTextField = new JTextField(3);
    ;
    //    private final JLabel attemptsLabel = new JLabel("Attempts remaining: ");
    private final StringBuilder input = new StringBuilder();
    private final JTextField[][] fields = new JTextField[INumberleModel.MAX_ATTEMPTS][7];
    private final JButton[][] buttons = new JButton[2][10];

    private int currentLine;
    private int currentPosition = 0;
    String[] numberKeys = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    String[] operationKeys = {"Back", "+", "-", "*", "/", "=", "Enter", "New Game"};
    JLabel target = new JLabel();

    /**
     * Constructs a NumberleView with the specified model and controller.
     * and sets up the GUI frame.
     * @param model the data model of the Numberle game
     * @param controller the controller handling business logic for the Numberle game
     * @requires model != null && controller != null
     * @ensures the GUI components are initialized and the initial state of the model is displayed
     */
    public NumberleView(INumberleModel model, NumberleController controller) {
        this.controller = controller;
        this.model = model;
        this.controller.startNewGame();
        ((NumberleModel) this.model).addObserver(this);
        initializeFrame();
        this.controller.setView(this);
        update((NumberleModel) this.model, null);
    }

    /**
     * Initializes the frame for the Numberle game.
     * @ensures frame is set to be visible and contains all necessary components properly configured
     */
    public void initializeFrame() {
        // Frame setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 900);
        frame.setLayout(new BorderLayout());
        JPanel displayPanel = new JPanel();// Display panel setup
        displayPanel.setLayout(new GridLayout(7, 7, 20, 20));
        displayPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 0, 80));
        for (int i = 0; i < INumberleModel.MAX_ATTEMPTS; i++) {
            for (int j = 0; j < 7; j++) {
                fields[i][j] = new JTextField();
                fields[i][j].setHorizontalAlignment(JTextField.CENTER);
                fields[i][j].setEditable(false);
                fields[i][j].setBackground(Color.white);
                fields[i][j].setFont(new Font("Arial", Font.BOLD, 36));
                displayPanel.add(fields[i][j]);
            }
        }
        target.setText("Target: " + controller.getTargetNumber());
        target.setVisible(controller.getFlags()[1]);
        displayPanel.add(new JPanel());
        displayPanel.add(new JPanel());
        displayPanel.add(new JPanel());
        displayPanel.add(target);
        frame.add(displayPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 5, 5));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        JPanel numberPanel = new JPanel(new GridLayout(1, 10, 5, 5));

        for (int i = 0; i < 10; i++) {
            buttons[0][i] = new JButton(numberKeys[i]);
            int finalI = i;
            buttons[0][i].addActionListener(e -> {
                if (currentPosition < 7) {
                    fields[currentLine][currentPosition].setText(numberKeys[finalI]);
                    currentPosition++;
                }
            });
            buttons[0][i].setPreferredSize(new Dimension(50, 40));
            buttons[0][i].setFont(new Font("Arial", Font.BOLD, 20));
            numberPanel.add(buttons[0][i]);
        }

        JPanel operationPanel = new JPanel(new GridLayout(1, 5, 5, 5));

        for (int i = 0; i < 8; i++) {
            buttons[1][i] = new JButton(operationKeys[i]);
            int finalI = i;
            buttons[1][i].addActionListener(e -> {
                switch (operationKeys[finalI]) {
                    case "Back":
                        if (currentPosition > 0) {
                            fields[currentLine][currentPosition - 1].setText("");
                            currentPosition--;
                        }
                        break;
                    case "Enter":
                        for (int j = 0; j < currentPosition; j++) {
                            input.append(fields[currentLine][j].getText());
                        }
                        controller.processInput(input.toString());
                        break;
                    case "New Game":
                        for (int n = 0; n < INumberleModel.MAX_ATTEMPTS; n++) {
                            for (int j = 0; j < 7; j++) {
                                fields[n][j].setText("");
                                fields[n][j].setBackground(Color.white);
                            }
                        }
                        for (int n = 0; n < 2; n++) {
                            for (int j = 0; j < 10; j++) {
                                if (n == 0 || (n == 1 && j < 6)) {
                                    buttons[n][j].setBackground(null);
                                }
                            }
                        }

                        currentPosition = 0;
                        currentLine = 0;
                        input.setLength(0);
                        buttons[1][7].setEnabled(false);
                        controller.startNewGame();
                        target.setText("Target: " + controller.getTargetNumber());
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "=":
                        if (currentPosition < 7) {
                            fields[currentLine][currentPosition].setText(operationKeys[finalI]);
                            currentPosition++;
                        }
                }
            });
            buttons[1][i].setBackground(null);
            buttons[1][i].setPreferredSize(new Dimension(50, 40));
            buttons[1][i].setFont(new Font("Arial", Font.BOLD, 13));

            operationPanel.add(buttons[1][i]);
        }
        buttons[1][7].setEnabled(false);
        frame.setLayout(new BorderLayout());
        frame.add(displayPanel, BorderLayout.CENTER);
        buttonPanel.add(numberPanel, BorderLayout.NORTH);
        buttonPanel.add(operationPanel, BorderLayout.SOUTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

    }


    @Override
    public void update(Observable o, Object arg) {
        Color green = new Color(0, 200, 150);
        Color orange = new Color(255, 150, 0);
        Color gray = new Color(150, 150, 160);
        if (arg instanceof String) {
            String message = (String) arg;
            switch (message) {
                case "Invalid Input":
                    JOptionPane.showMessageDialog(frame, message, "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
                    currentPosition = input.length();
                    input.setLength(0);
                    break;
                case "Without Equals":
                    JOptionPane.showMessageDialog(frame, "Invalid equation! The equation must contains equals sign.", message, JOptionPane.INFORMATION_MESSAGE);
                    currentPosition = input.length();
                    input.setLength(0);
                    break;
                case "Invalid Equals Count":
                    JOptionPane.showMessageDialog(null, "The equation must contain exactly one equals sign. Please correct your input.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    currentPosition = input.length();
                    input.setLength(0);
                    break;
                case "Without Operator":
                    JOptionPane.showMessageDialog(frame, "Invalid equation! The equation must contains at least one operator.", message, JOptionPane.INFORMATION_MESSAGE);
                    currentPosition = input.length();
                    input.setLength(0);
                    break;
                case "Without Number":
                    JOptionPane.showMessageDialog(frame, "Invalid equation! The equation must contains numbers.", message, JOptionPane.INFORMATION_MESSAGE);
                    currentPosition = input.length();
                    input.setLength(0);
                    break;


                case "Unbalanced Equation":
                    JOptionPane.showMessageDialog(null, "The equation is unbalanced. Please check and try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case "Try Again":// Handle try again scenario
                    for (int i = 0; i < 7; i++) {
                        if (controller.getTopcolor()[i] == 1) {
                            fields[currentLine][i].setBackground(green);
                        } else if (controller.getTopcolor()[i] == 2) {
                            fields[currentLine][i].setBackground(orange);
                        } else if (controller.getTopcolor()[i] == 3) {
                            fields[currentLine][i].setBackground(gray);
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        if (controller.getButtonStatus().get(numberKeys[i].charAt(0)) == 1) {
                            buttons[0][i].setBackground(green);
                        } else if (controller.getButtonStatus().get(numberKeys[i].charAt(0)) == 2) {
                            buttons[0][i].setBackground(orange);
                        } else if (controller.getButtonStatus().get(numberKeys[i].charAt(0)) == 3) {
                            buttons[0][i].setBackground(gray);
                        }
                    }
                    for (int i = 1; i < 6; i++) {
                        if (controller.getButtonStatus().get(operationKeys[i].charAt(0)) == 1) {
                            buttons[1][i].setBackground(green);
                        } else if (controller.getButtonStatus().get(operationKeys[i].charAt(0)) == 2) {
                            buttons[1][i].setBackground(orange);
                        } else if (controller.getButtonStatus().get(operationKeys[i].charAt(0)) == 3) {
                            buttons[1][i].setBackground(gray);
                        }
                    }
                    currentPosition = 0;
                    currentLine = INumberleModel.MAX_ATTEMPTS - model.getRemainingAttempts();
                    input.setLength(0);
                    buttons[1][7].setEnabled(true);
                    break;
                case "Game Won":
                    for (int i = 0; i < 7; i++) {
                        if (controller.getTopcolor()[i] == 1) {
                            fields[currentLine][i].setBackground(green);
                        } else if (controller.getTopcolor()[i] == 2) {
                            fields[currentLine][i].setBackground(orange);
                        } else if (controller.getTopcolor()[i] == 3) {
                            fields[currentLine][i].setBackground(gray);
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        if (controller.getButtonStatus().get(numberKeys[i].charAt(0)) == 1) {
                            buttons[0][i].setBackground(green);
                        } else if (controller.getButtonStatus().get(numberKeys[i].charAt(0)) == 2) {
                            buttons[0][i].setBackground(orange);
                        } else if (controller.getButtonStatus().get(numberKeys[i].charAt(0)) == 3) {
                            buttons[0][i].setBackground(gray);
                        }
                    }
                    for (int i = 1; i < 6; i++) {
                        if (controller.getButtonStatus().get(operationKeys[i].charAt(0)) == 1) {
                            buttons[1][i].setBackground(green);
                        } else if (controller.getButtonStatus().get(operationKeys[i].charAt(0)) == 2) {
                            buttons[1][i].setBackground(orange);
                        } else if (controller.getButtonStatus().get(operationKeys[i].charAt(0)) == 3) {
                            buttons[1][i].setBackground(gray);
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "Congratulations! You won the game!", "Game Won", JOptionPane.INFORMATION_MESSAGE);
                    for (int i = 0; i < INumberleModel.MAX_ATTEMPTS; i++) {
                        for (int j = 0; j < 7; j++) {
                            fields[i][j].setText("");
                            fields[i][j].setBackground(Color.white);
                        }
                    }
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (i == 0 || (i == 1 && j < 6)) {
                                buttons[i][j].setBackground(null);
                            }
                        }
                    }
                    currentPosition = 0;
                    currentLine = 0;
                    input.setLength(0);
                    buttons[1][7].setEnabled(false);
                    controller.startNewGame();
                    target.setText("Target: " + controller.getTargetNumber());
                    break;
                case "Game Over":
                    for (int i = 0; i < 7; i++) {
                        if (controller.getTopcolor()[i] == 1) {
                            fields[currentLine][i].setBackground(green);
                        } else if (controller.getTopcolor()[i] == 2) {
                            fields[currentLine][i].setBackground(orange);
                        } else if (controller.getTopcolor()[i] == 3) {
                            fields[currentLine][i].setBackground(gray);
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        if (controller.getButtonStatus().get(numberKeys[i].charAt(0)) == 1) {
                            buttons[0][i].setBackground(green);
                        } else if (controller.getButtonStatus().get(numberKeys[i].charAt(0)) == 2) {
                            buttons[0][i].setBackground(orange);
                        } else if (controller.getButtonStatus().get(numberKeys[i].charAt(0)) == 3) {
                            buttons[0][i].setBackground(gray);
                        }
                    }
                    for (int i = 1; i < 6; i++) {
                        if (controller.getButtonStatus().get(operationKeys[i].charAt(0)) == 1) {
                            buttons[1][i].setBackground(green);
                        } else if (controller.getButtonStatus().get(operationKeys[i].charAt(0)) == 2) {
                            buttons[1][i].setBackground(orange);
                        } else if (controller.getButtonStatus().get(operationKeys[i].charAt(0)) == 3) {
                            buttons[1][i].setBackground(gray);
                        }
                    }
                    JOptionPane.showMessageDialog(frame, message + "! No Attempts, The correct equation was: " + controller.getTargetNumber(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    for (int i = 0; i < INumberleModel.MAX_ATTEMPTS; i++) {
                        for (int j = 0; j < 7; j++) {
                            fields[i][j].setText("");
                            fields[i][j].setBackground(Color.white);
                        }
                    }
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (i == 0 || (i == 1 && j < 6)) {
                                buttons[i][j].setBackground(null);
                            }
                        }
                    }
                    currentPosition = 0;
                    currentLine = 0;
                    input.setLength(0);
                    buttons[1][7].setEnabled(false);
                    controller.startNewGame();
                    target.setText("Target: " + controller.getTargetNumber());
                    break;
            }
        }
    }
}