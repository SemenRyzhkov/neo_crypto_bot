package com.neoflex.telegram.cache;


import com.neoflex.telegram.bot.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataCache {
    private Map<Long, BotState> botStates = new HashMap<>();
    private Map<Long, Double> currentValues = new HashMap<>();

    public void setBotState(long userId, BotState botState) {
        botStates.put(userId, botState);
    }

    public void setCurrent(long userId, Double indicator) {
        currentValues.put(userId, indicator);
    }

    public BotState getBotState(long userId) {
        BotState botState = botStates.get(userId);
        if (botState == null) {
            botState = BotState.SHOW_MAIN_MENU;
        }
        return botState;
    }

    public Double getCurrent(long userId) {
        return currentValues.get(userId);
    }

}
