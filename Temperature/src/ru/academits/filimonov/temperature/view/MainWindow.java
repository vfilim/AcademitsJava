package ru.academits.filimonov.temperature.view;

import ru.academits.filimonov.temperature.model.GradeType;
import ru.academits.filimonov.temperature.model.Model;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    public void run() {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();

            JFrame mainFrame = new JFrame("Temperature");

            mainFrame.setVisible(true);
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setSize(500, 150);
            mainFrame.setLocationRelativeTo(null);

            JPanel inputGradePanel = new JPanel();
            mainFrame.add(inputGradePanel, BorderLayout.PAGE_START);

            JButton kalvinGradeInput = new JButton("Kelvin");
            kalvinGradeInput.addActionListener((e) -> model.setInputGradeType(GradeType.KELVIN));

            JButton celsiusGradeInput = new JButton("Celsius");
            celsiusGradeInput.addActionListener((e) -> model.setInputGradeType(GradeType.CELSIUS));

            JButton fahrenheitGradeInput = new JButton("Fahrenheit");
            fahrenheitGradeInput.addActionListener((e) -> model.setInputGradeType(GradeType.FAHRENHEIT));

            inputGradePanel.add(kalvinGradeInput);
            inputGradePanel.add(celsiusGradeInput);
            inputGradePanel.add(fahrenheitGradeInput);

            JPanel outputGradePanel = new JPanel();
            mainFrame.add(outputGradePanel, BorderLayout.PAGE_END);

            JButton kalvinGradeOutput = new JButton("Kelvin");
            kalvinGradeOutput.addActionListener((e) -> model.setOutGradeType(GradeType.KELVIN));

            JButton celsiusGradeOutput = new JButton("Celsius");
            celsiusGradeOutput.addActionListener((e) -> model.setOutGradeType(GradeType.CELSIUS));

            JButton fahrenheitGradeOutput = new JButton("Fahrenheit");
            fahrenheitGradeOutput.addActionListener((e) -> model.setOutGradeType(GradeType.FAHRENHEIT));

            outputGradePanel.add(kalvinGradeOutput);
            outputGradePanel.add(celsiusGradeOutput);
            outputGradePanel.add(fahrenheitGradeOutput);

            JPanel inputTextPanel = new JPanel();
            mainFrame.add(inputTextPanel, BorderLayout.LINE_START);

            JTextField inputText = new JTextField(10);

            inputTextPanel.add(inputText);

            JPanel outputTextPanel = new JPanel();
            mainFrame.add(outputTextPanel, BorderLayout.LINE_END);

            JTextField outputText = new JTextField(10);
            outputText.setEditable(false);

            outputTextPanel.add(outputText);

            JPanel convertPanel = new JPanel();
            mainFrame.add(convertPanel);

            JButton convertButton = new JButton("convert");
            convertPanel.add(convertButton);

            convertButton.addActionListener((e) -> {
                try {
                    double output = model.convert(Double.parseDouble(inputText.getText()));

                    outputText.setText(String.format("%.2f", output));
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(mainFrame, "The input data must be number!");
                }
            });
        });
    }
}