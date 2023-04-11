package ru.ann.currencyrate.controller;

import lombok.Getter;
import ru.ann.currencyrate.domain.Command;
import ru.ann.currencyrate.service.CommandParserService;
import ru.ann.currencyrate.service.CommandService;
import ru.ann.currencyrate.service.OutputService;
import ru.ann.currencyrate.service.ValidationCommandService;

@Getter
public class TelegramController {
    private final CommandService commandService = new CommandService();
    private final ValidationCommandService validationService = new ValidationCommandService();
    private final OutputService outputService = new OutputService();
    private final CommandParserService commandParserService = new CommandParserService();

    public void setCommand(Command command) {
        commandService.setCommand(command);
    }

}
