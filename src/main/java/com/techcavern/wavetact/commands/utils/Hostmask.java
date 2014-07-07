package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;


public class Hostmask extends GenericCommand {
    @CMD
    public Hostmask() {
        super(GeneralUtils.toArray("hostmask host mask"), 0, "Hostmask [nick]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args) throws Exception {
        IRCUtils.SendMessage(user, channel, GetUtils.getUserByNick(channel, args[0]).getHostmask(), isPrivate);

    }
}
