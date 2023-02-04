package com.niliusjulius.lolinfo.component;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Messages {
    private static MessageSource messageSource;
    private static Locale locale;

    public Messages(MessageSource messageSource, Locale locale) {
        Messages.messageSource = messageSource;
        Messages.locale = locale;
    }

    public static String getMessage(@NotBlank String code) {
        return messageSource.getMessage(code, null, locale);
    }

    public static String getMessage(@NotBlank String code, @Nullable Object... objects) {
        return messageSource.getMessage(code, objects, locale);
    }

    public static String getMessage(@NotBlank String code, @Nullable Object[] objects, @NotNull Locale locale) {
        return messageSource.getMessage(code, objects, locale);
    }
}
