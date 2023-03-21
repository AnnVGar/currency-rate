package ru.ann;

import ru.ann.impl.AverageRate;
import ru.ann.impl.Command;

public class App {

    public static void main(String[] args) {
        Command.getCommand(new AverageRate()).execute();
    }


}
