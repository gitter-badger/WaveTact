package com.techcavern.wavetact.commands.controller;


import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ConCMD
public class IRCRaw extends GenericCommand {

    public IRCRaw() {
        super(GeneralUtils.toArray("ircraw raw"), 9001, "raw [to be sent to server]", "sends a raw msg to the server", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        network.sendRaw().rawLine(GeneralUtils.buildMessage(0, args.length, args));
    }
}

