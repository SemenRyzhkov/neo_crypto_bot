package com.neoflex.telegram.cache;


import com.neoflex.telegram.bot.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataCache {
    private Map<Long, BotState> botStates = new HashMap<>();

    public void setBotState(long userId, BotState botState) {
        botStates.put(userId, botState);
    }

    public BotState getBotState(long userId) {
        BotState botState = botStates.get(userId);
        if (botState == null) {
            botState = BotState.SHOW_MAIN_MENU;
        }
        return botState;
    }

}
