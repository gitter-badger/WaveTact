/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.global;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
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
public class Disconnect extends GenericCommand {
    @CMD
    public Disconnect() {
        super(GeneralUtils.toArray("disconnect dc"), 20, "disconnect (r)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {
        if (args.length > 1 && args[0].equalsIgnoreCase("r")) {
            PircBotX botObject = Bot;
            IRCUtils.SendMessage(user, channel, "Reconnecting", isPrivate);
            botObject.stopBotReconnect();
            botObject.sendIRC().quitServer();
            Thread.sleep(20000);
            GeneralRegistry.WaveTact.addBot(botObject);
        }else {
            Bot.stopBotReconnect();
            Bot.sendIRC().quitServer();
        }
    }
}
