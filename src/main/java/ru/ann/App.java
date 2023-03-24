package ru.ann;

import ru.ann.algorithm.AverageRate;
import ru.ann.controller.ConsoleController;
import ru.ann.service.CommandService;
import ru.ann.service.CurrencyImportService;

public class App {

    public static void main(String[] args) {
        App.start();
    }

    private static void start(){
        CurrencyImportService.importCurrencyMap();
        new ConsoleController().listenCommandFromConsole(new AverageRate());
    }

}
