package com.senderman.lastkatkabot.command.admin;

import com.annimon.tgbotsmodule.services.CommonAbsSender;
import com.senderman.lastkatkabot.ApiRequests;
import com.senderman.lastkatkabot.Role;
import com.senderman.lastkatkabot.command.CommandExecutor;
import com.senderman.lastkatkabot.dbservice.UserManager;
import com.senderman.lastkatkabot.model.BlacklistedUser;
import com.senderman.lastkatkabot.util.Html;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.EnumSet;

@Component
public class GoodNeko implements CommandExecutor {

    private final UserManager<BlacklistedUser> blackUsers;

    public GoodNeko(
            @Qualifier("blacklistManager") UserManager<BlacklistedUser> blackUsers
    ) {
        this.blackUsers = blackUsers;
    }

    @Override
    public EnumSet<Role> getRoles() {
        return EnumSet.of(Role.ADMIN, Role.MAIN_ADMIN);
    }

    @Override
    public String getTrigger() {
        return "/goodneko";
    }

    @Override
    public String getDescription() {
        return "повышение до хорошей кисы. реплаем.";
    }

    @Override
    public void execute(Message message, CommonAbsSender telegram) {
        if (!message.isReply() || message.isUserMessage()) {
            ApiRequests.answerMessage(message, "Позвышать до хороших кис нужно в группе и реплаем!")
                    .callAsync(telegram);
            return;
        }
        var user = message.getReplyToMessage().getFrom();
        if (user.getIsBot()) {
            ApiRequests.answerMessage(message, "Но это же просто бот, имитация человека! " +
                                               "Разве может бот написать симфонию, иметь статистику, участвовать в дуэлях, быть хорошей кисой?")
                    .callAsync(telegram);
            return;
        }
        var userLink = Html.getUserLink(user);
        if (blackUsers.deleteById(user.getId()))
            ApiRequests.answerMessage(message, "Теперь " + userLink + " -  хорошая киса!").callAsync(telegram);
        else
            ApiRequests.answerMessage(message, userLink + " уже хорошая киса!").callAsync(telegram);

    }
}




