package moe.yiheng.bot;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BotManager {
    public static MyBot bot = new MyBot();

    public static void start() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        // Register our bot
        try {
            botsApi.registerBot(bot);
            System.out.println("botStarted");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
