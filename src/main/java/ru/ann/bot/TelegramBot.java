package ru.ann.bot;


import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ann.controller.TelegramController;

import java.io.File;
import java.io.IOException;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    TelegramController controller = new TelegramController();

    @Override
    public String getBotToken() {
        return "6080193367:AAGZnEQm7R-Fa_R6ZLdcGZLNp7LFvo3ePyI";
    }

    @Override
    public String getBotUsername() {
        return "currencyRateAnnBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        if (text.equals("/start") || text.equals("/help")) {
            sendMessageToChat(chatId, controller.сommandRules());
        } else {
           String resultParseCommand = controller.parseCommandFromLine(text);
            if (resultParseCommand.equals("OK")) {
                if (text.toUpperCase().endsWith("GRAPH")) {
                    controller.saveCurrencyRateToGraph();
                    sendImageFromFileId("Chart.jpeg", chatId);
                } else {
                    sendMessageToChat(chatId, controller.convertCurrencyRateToString());
                }
            } else {
                sendMessageToChat(chatId, resultParseCommand);
            }
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
            log.error("problem with graph",e);
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
            log.error("problem with message",e);
        }
    }
}
