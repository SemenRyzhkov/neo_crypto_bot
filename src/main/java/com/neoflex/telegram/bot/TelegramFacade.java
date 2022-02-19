package com.neoflex.telegram.bot;

import com.neoflex.telegram.cache.DataCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final DataCache dataCache;

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return null;
        } else {
            Message message = update.getMessage();

            if (message != null && message.hasText()) {
                log.info("New message from User:{}, chatId: {},  with text: {}",
                        message.getFrom().getFirstName(), message.getChatId(), message.getText());
                replyMessage = handleInputMessage(message);
            }
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {

        String inputMessage = message.getText().toLowerCase();
        long userId = message.getFrom().getId();
        BotState botState;


        switch (inputMessage) {
            case "/start":
                botState = BotState.SHOW_MAIN_MENU;
                break;
            case "подписаться на биткоин":
            case "отписаться от биткоина":
//                botState = BotState.SHOW_MAIN_MENU;
                botState = BotState.SHOW_BITCOIN;
                break;
            case "мой профиль":
                botState = BotState.SHOW_PROFILE;
                break;
            default:
                botState = dataCache.getBotState(userId);
                break;
        }
        dataCache.setBotState(userId, botState);
        return botStateContext.processInputMessage(botState, message);
    }

}
