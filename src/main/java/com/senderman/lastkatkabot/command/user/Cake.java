package com.senderman.lastkatkabot.command.user;

import com.annimon.tgbotsmodule.api.methods.Methods;
import com.annimon.tgbotsmodule.services.CommonAbsSender;
import com.senderman.lastkatkabot.callback.Callbacks;
import com.senderman.lastkatkabot.command.CommandExecutor;
import com.senderman.lastkatkabot.util.Html;
import com.senderman.lastkatkabot.util.callback.ButtonBuilder;
import com.senderman.lastkatkabot.util.callback.MarkupBuilder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class Cake implements CommandExecutor {

    public Cake() {
    }

    @Override
    public String getTrigger() {
        return "/cake";
    }

    @Override
    public String getDescription() {
        return "(reply) подарить тортик. Можно указать начинку, напр. /cake с вишней";
    }

    @Override
    public void execute(Message message, CommonAbsSender telegram) {
        if (!message.isReply() || message.isUserMessage()) return;


        var subjectName = Html.htmlSafe(message.getFrom().getFirstName());
        var target = message.getReplyToMessage().getFrom();
        var targetName = Html.htmlSafe(target.getFirstName());
        var text = String.format("\uD83C\uDF82 %s, пользователь %s подарил вам тортик %s",
                targetName, subjectName, message.getText().replaceAll("/@\\S*\\s?|/\\S*\\s?", ""));

        var markup = new MarkupBuilder()
                .addButton(ButtonBuilder.callbackButton()
                        .text("Принять")
                        .payload(Callbacks.CAKE + " accept " + target.getId()))
                .addButton(ButtonBuilder.callbackButton()
                        .text("Отказаться")
                        .payload(Callbacks.CAKE + " decline " + target.getId()))
                .build();

        Methods.sendMessage(message.getChatId(), text)
                .setReplyToMessageId(message.getReplyToMessage().getMessageId())
                .setReplyMarkup(markup)
                .callAsync(telegram);

    }

}
