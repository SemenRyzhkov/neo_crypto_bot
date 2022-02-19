package com.neoflex.telegram.config;


import com.neoflex.telegram.bot.MessageSender;
import com.neoflex.telegram.bot.NeoBot;
import com.neoflex.telegram.bot.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Getter
@Setter
@Configuration
@Slf4j
public class BotConfig {
    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.webHookPath")
    private String webHookPath;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(getWebHookPath()).build();
    }

    @Bean
    public NeoBot springNeoBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        NeoBot bot = new NeoBot(telegramFacade, setWebhook);
        bot.setBotToken(getBotToken());
        bot.setBotUsername(getBotUsername());
        bot.setBotPath(getWebHookPath());

        return bot;
    }
//
//    @Bean
//    public MessageSender messageSender(SetWebhook setWebhook, TelegramFacade telegramFacade) {
//        NeoBot bot = new NeoBot(telegramFacade, setWebhook);
//        bot.setBotToken(getBotToken());
//        bot.setBotUsername(getBotUsername());
//        bot.setBotPath(getWebHookPath());
//
//        return bot;
//    }
//
//    @Bean(name = "new")
//    public NeoBot newNeoBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
//        NeoBot bot = new NeoBot(telegramFacade, setWebhook);
//        bot.setBotToken(getBotToken());
//        bot.setBotUsername(getBotUsername());
//        bot.setBotPath(getWebHookPath());
//
//        return bot;
//    }
}
