package ru.ann.currencyrate.bot;


import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ann.currencyrate.common.BotConstant;
import ru.ann.currencyrate.common.ResultConstant;
import ru.ann.currencyrate.controller.TelegramController;
import ru.ann.currencyrate.domain.type.BotCommand;
import ru.ann.currencyrate.domain.type.Output;

import java.io.File;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramController controller = new TelegramController();

    @Override
    public String getBotToken() {
        return BotConstant.BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BotConstant.BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        if (text.equals(BotCommand.START.getCommand()) || text.equals(BotCommand.HELP.getCommand())) {
            sendMessageToChat(chatId, controller.getOutputService().commandRules());
        } else {
            String rightCommand = controller.getValidationService().validateCommandFromLine(text);
            if (rightCommand == null) {
                sendMessageToChat(chatId, ResultConstant.BAD_COMMAND);
            } else {
                controller.setCommand(controller.getCommandParserService().parserCommandFromLine(rightCommand));
                replyToCommand(text, chatId);
            }
        }
    }

    private void replyToCommand(String text, String chatId) {
        if (text.toUpperCase().endsWith(Output.GRAPH.getName())) {
            try {
                String fileName = controller.getOutputService().saveCurrencyRateToGraph(controller.getCommandService().executeRate(), chatId);
                sendImageFromFileId(fileName, chatId);
            } catch (Exception e) {
                log.error(ResultConstant.ERROR, e.getMessage());
                sendMessageToChat(chatId, ResultConstant.ERROR);
            }
        } else {
            sendMessageToChat(chatId, controller.getOutputService().rateToString(controller.getCommandService().executeRate()));
        }
    }

    public void sendImageFromFileId(String fileId, String chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(new File(fileId)));
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            sendMessageToChat(chatId, "problem with graph");
            log.error("problem with graph", e);
        }
    }


    private void sendMessageToChat(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            sendMessageToChat(chatId, "problem with message");
            log.error("problem with message", e);
        }
    }
}
