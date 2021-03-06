package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@ConCMD
public class Help extends ConsoleCommand {

    public Help() {
        super(GeneralUtils.toArray("help"), "help (command)", "gets help on a specific command");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        if (args.length > 0) {
            ConsoleCommand command = IRCUtils.getConsoleCommand(args[0]);
            if (command != null) {
                commandIO.getPrintStream().println("Aliases: " + StringUtils.join(Arrays.asList(command.getCommandID()), ", "));
                String syntax = command.getSyntax();
                if (!syntax.isEmpty())
                    commandIO.getPrintStream().println("Syntax: " + syntax);
                commandIO.getPrintStream().println(command.getDesc());
            } else {
                commandIO.getPrintStream().println("Command does not exist");
            }
        } else {
            commandIO.getPrintStream().println("help (command) - gets help on a specific command");
        }
    }

}
