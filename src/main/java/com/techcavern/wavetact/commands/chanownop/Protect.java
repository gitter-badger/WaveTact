/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanownop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanOwnOpCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@ChanOwnOpCMD
public class Protect extends GenericCommand {

    public Protect() {
        super(GeneralUtils.toArray("protect prot sop"), 15, "protect (-)(user)", "Sets protect mode if it exists on a user", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (network.getServerInfo().getPrefixes().contains("a")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("-")) {
                    channel.send().deSuperOp(user);
                } else if (args[0].startsWith("-")) {
                    channel.send().deSuperOp(GetUtils.getUserByNick(network, args[0].replaceFirst("-", "")));
                } else {
                    channel.send().superOp(GetUtils.getUserByNick(network, args[0]));

                }
            } else {
                channel.send().superOp(user);
            }
        } else {
            ErrorUtils.sendError(user, "This server does not support superops");
        }
    }
}
