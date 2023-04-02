package ru.ann.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ann.controller.TelegramController;

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
        if (text.equals("/start")){
            sendMessageToChat(chatId,controller.сommandRules());
        }else{
            controller.listenCommandFromConsole(text);
            sendMessageToChat(chatId,controller.printRate());
        }
    }


    private void sendMessageToChat( String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
