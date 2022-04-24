package com.mark.spring.shell.prompt;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomTerminalProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(
                "Mark Spring Shell>> "
        );
    }
}
