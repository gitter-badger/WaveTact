/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.consoleCommands.anonymity;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@ConCMD
public class Act extends ConsoleCommand {

    public Act() {
        super(GeneralUtils.toArray("act do"), "act [network] [channel] [something]", "Makes the bot do something");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        PircBotX network = IRCUtils.getBotByNetworkName(args[0]);
        String prefix = IRCUtils.getPrefix(network, args[1]);
        Channel chan;
        if (!prefix.isEmpty())
            chan = IRCUtils.getChannelbyName(network, args[1].replace(prefix, ""));
        else
            chan = IRCUtils.getChannelbyName(network, args[1]);
        if(chan != null)
            IRCUtils.sendAction(network, chan, GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "), prefix);
    }
}

