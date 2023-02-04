package com.niliusjulius.lolinfo.shell.component;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class LolPromptProvider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("lol-info:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN));
    }
}
