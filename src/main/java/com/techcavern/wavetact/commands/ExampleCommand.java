package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.objects.Command;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * For Future Reference
 */
@SuppressWarnings("ALL")
public class ExampleCommand extends Command {
    public ExampleCommand() {
        super("blah", 0, "description");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.respond("hi");
    }
}