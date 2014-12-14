/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.anonymonity;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@CMD
@TruCMD
public class Say extends GenericCommand {

    public Say() {
        super(GeneralUtils.toArray("say msg"), 5, "say [something]", "make the network say something", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!isPrivate) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(args, " ").replace("\n", " "), prefix);
        } else {
            Channel chan = GetUtils.getChannelbyName(network, args[0].replace(IRCUtils.getPrefix(args[0]), ""));
            if (chan == null) {
                IRCUtils.sendMessage(user, network, channel, StringUtils.join(args, " ").replace("\n", " "), IRCUtils.getPrefix(args[0]));
            } else {
                if (PermUtils.getPermLevel(network, user.getNick(), chan) >= 5) {
                    IRCUtils.sendMessage(user, network, chan, StringUtils.join(args, " ").replace("\n", " "), IRCUtils.getPrefix(args[0]));
                } else {
                    IRCUtils.sendError(user, "Permission Denied");
                }
            }
        }

    }
}
