package ru.ann.currencyrate.domain.type;

public enum BotCommand {
    START("/start"),
    HELP("/help");

    private final String command;

    BotCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
