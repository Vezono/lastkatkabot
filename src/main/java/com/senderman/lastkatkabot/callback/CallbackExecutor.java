package com.senderman.lastkatkabot.callback;

import com.senderman.lastkatkabot.service.TriggerHandler;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackExecutor extends TriggerHandler<CallbackQuery> {
}
