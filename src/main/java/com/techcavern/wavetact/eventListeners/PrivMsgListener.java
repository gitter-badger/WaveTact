/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * @author jztech101
 */
public class PrivMsgListener extends ListenerAdapter {

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                String[] message = StringUtils.split(Colors.removeFormattingAndColors(event.getMessage()), " ");
                String command = message[0].toLowerCase();
                IRCCommand Command = IRCUtils.getCommand(command, IRCUtils.getNetworkNameByNetwork(event.getBot()), null);
                if (Command == null) {
                    Command = IRCUtils.getCommand(StringUtils.replaceOnce(command, DatabaseUtils.getConfig("commandchar"), ""), IRCUtils.getNetworkNameByNetwork(event.getBot()), null);
                }
                message = ArrayUtils.remove(message, 0);
                if (Command != null) {
                    if (Command.getChannelRequired()) {
                        Channel channel = null;
                        String prefix = null;
                        if (message.length > 0) {
                            prefix = IRCUtils.getPrefix(event.getBot(), message[0]);
                            if (!prefix.isEmpty())
                                channel = IRCUtils.getChannelbyName(event.getBot(), message[0].replace(prefix, ""));
                            else
                                channel = IRCUtils.getChannelbyName(event.getBot(), message[0]);
                            message = ArrayUtils.remove(message, 0);
                        }
                        if (channel != null) {
                            int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), channel);
                            if (userPermLevel >= Command.getPermLevel()) {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), prefix + channel.getName(), channel, true, userPermLevel, message);
                                } catch (Exception e) {
                                    ErrorUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                                    e.printStackTrace();
                                }
                            } else {
                                ErrorUtils.sendError(event.getUser(), "Permission denied");
                            }
                        } else {
                            ErrorUtils.sendError(event.getUser(), "Please specify channel as argument #1 in front of all the other arguments");
                        }
                    } else {
                        int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), null);
                        if (Command.getPermLevel() <= 5) {
                            try {
                                Command.onCommand(event.getUser(), event.getBot(), null, null, true, userPermLevel, message);
                            } catch (Exception e) {
                                ErrorUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                                e.printStackTrace();
                            }
                        } else {
                            if (userPermLevel >= Command.getPermLevel()) {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), null, null, true, userPermLevel, message);
                                } catch (Exception e) {
                                    ErrorUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                                    e.printStackTrace();
                                }
                            } else {
                                ErrorUtils.sendError(event.getUser(), "Permission denied");
                            }
                        }
                    }
                }
            }

        }
        Registry.threadPool.execute(new process());
    }
}




