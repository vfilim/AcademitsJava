package ru.academits.filimonov.temperature.main;

import ru.academits.filimonov.temperature.model.*;
import ru.academits.filimonov.temperature.view.MainWindow;
import ru.academits.filimonov.temperature.view.MyMainWindow;

public class Main {
    public static void main(String[] args) {
        ScaleConverterModel model = new MyScaleConverterModel(
                new CelsiusScale(),
                new FahrenheitScale(),
                new KelvinScale()
        );

        MainWindow mainWindow = new MyMainWindow(model);

        mainWindow.run();
    }
}