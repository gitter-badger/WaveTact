package com.techcavern.wavetact.commands.Utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class Help extends Command {
    public Help() {
        super("help", 0, "help (command)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("permissions")) {
                event.respond("0 = Everyone, 5 = Voiced & Half-opped, 10 = Opped & Protected, 15 = Ownered, 9001 = Controller ");
            } else {
                event.respond(IRCUtils.getCommand(args[0]).getDesc());
            }
        } else {
            event.respond("help [command] - Generally a + before something means editing it, and a - means removing it. None means adding it. - Time is in [time](s/m/h/d) format, IRCd is specified with c for Charybdis, u for Unreal, and i for inspircd.\n");


        }
    }
}
