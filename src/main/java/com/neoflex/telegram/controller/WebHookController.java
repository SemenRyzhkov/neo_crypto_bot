package com.neoflex.telegram.controller;

import com.neoflex.telegram.bot.NeoBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {
    private final NeoBot neoBot;

    public WebHookController(NeoBot neoBot) {
        this.neoBot = neoBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return neoBot.onWebhookUpdateReceived(update);
    }
}
