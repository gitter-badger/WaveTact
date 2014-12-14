/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanowner;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanOWNCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@CMD
@ChanOWNCMD
public class Owner extends GenericCommand {

    public Owner() {
        super(GeneralUtils.toArray("owner own oop"), 15, "Owner (-)(User)", "sets owner mode if it exists on a user", true);
    }

    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (network.getServerInfo().getPrefixes().contains("q")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("-")) {
                    channel.send().deOwner(user);
                } else if (args[0].startsWith("-")) {
                    channel.send().deOwner(GetUtils.getUserByNick(network, args[0].replaceFirst("-", "")));
                } else {
                    channel.send().owner(GetUtils.getUserByNick(network, args[0]));
                }
            } else {
                channel.send().owner(user);
            }
        } else {
            IRCUtils.sendError(user, "This server does not support Owners");
        }
    }
}

