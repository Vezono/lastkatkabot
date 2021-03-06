package com.senderman.lastkatkabot.command.admin;

import com.annimon.tgbotsmodule.api.methods.Methods;
import com.annimon.tgbotsmodule.services.CommonAbsSender;
import com.senderman.lastkatkabot.Role;
import com.senderman.lastkatkabot.command.CommandExecutor;
import com.senderman.lastkatkabot.dbservice.FeedbackService;
import com.senderman.lastkatkabot.model.Feedback;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.EnumSet;

@Component
public class ShowFeedbacks implements CommandExecutor {

    private static final String feedbackSeparator = "\n\n<code>====================================</code>\n\n";
    private final FeedbackService feedbackService;

    public ShowFeedbacks(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public String getTrigger() {
        return "/feedbacks";
    }

    @Override
    public String getDescription() {
        return "показать первые n фидбеков. Без параметра - первые 10. Напр. " + getTrigger() + " 5";
    }

    @Override
    public EnumSet<Role> getRoles() {
        return EnumSet.of(Role.ADMIN, Role.MAIN_ADMIN);
    }

    @Override
    public void execute(Message message, CommonAbsSender telegram) {
        var chatId = message.getChatId();
        Methods.sendMessage(chatId, "Собираем фидбеки...").callAsync(telegram);
        if (feedbackService.count() == 0) {
            Methods.sendMessage(chatId, "Фидбеков нет!").callAsync(telegram);
            return;
        }

        var text = new StringBuilder("<b>Фидбеки от даунов не умеющих юзать бота</b>");
        for (Feedback feedback : feedbackService.findAll()) {
            String formattedFeedback = formatFeedback(feedback);
            // if maximum text length reached
            if (text.length() + feedbackSeparator.length() + formattedFeedback.length() >= 4096) {
                Methods.sendMessage(chatId, text.toString()).callAsync(telegram);
                text.setLength(0);
            }
            text.append(feedbackSeparator).append(formattedFeedback);
        }
        // send remaining feedbacks
        if (text.length() != 0) {
            Methods.sendMessage(chatId, text.toString()).callAsync(telegram);
        }
    }

    private String formatFeedback(Feedback feedback) {
        return """
                <code>#%d</code>
                От %s (id<code>%d</code>)
                Отвечен: %s

                %s"""
                .formatted(
                        feedback.getId(),
                        feedback.getUserName(),
                        feedback.getUserId(),
                        feedback.isReplied() ? "✅" : "❌",
                        feedback.getMessage()
                );
    }
}
