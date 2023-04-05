package ru.ann;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ann.bot.TelegramBot;
import ru.ann.service.CurrencyImportService;

public class App {

    public static void main(String[] args) throws TelegramApiException {
        App.start();
    }

    private static void start() throws TelegramApiException {
        CurrencyImportService.importCurrencyMap();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TelegramBot());
    }



}
