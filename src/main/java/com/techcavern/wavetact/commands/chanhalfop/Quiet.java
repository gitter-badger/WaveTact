package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.QuietTimeUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.UTime;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ChanHOPCMD
public class Quiet extends GenericCommand {

    public Quiet() {
        super(GeneralUtils.toArray("quiet mute"), 7, "Quiet (-)[User][hostmask] (-)(+)(time)", "quiets a user for the specified time or 24 hours", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {

        String ircd;
        if (network.getServerInfo().getChannelModes().contains("q")) {
            ircd = "c";
        } else if (network.getServerInfo().getExtBanPrefix().equalsIgnoreCase("~") && network.getServerInfo().getExtBanList() != null && network.getServerInfo().getExtBanList().contains("q")) {
            ircd = "u";
        } else if (network.getServerInfo().getExtBanPrefix().contains("m") && network.getServerInfo().getExtBanList() == null) {
            ircd = "i";
        } else {
            IRCUtils.sendError(user, "This networks ircd is not supported for quiets.");
            return;
        }
        String hostmask;
        if (args[0].contains("!") && args[0].contains("@")) {
            if (args[0].startsWith("-")) {
                hostmask = args[0].replaceFirst("-", "");
            } else if (args[0].startsWith("+")) {
                hostmask = args[0].replaceFirst("\\+", "");
            } else {
                hostmask = args[0];
            }
        } else {
            if (args[0].startsWith("+")) {
                hostmask = IRCUtils.getHostmask(network, args[0].replaceFirst("\\+", ""), true);
            } else if (args[0].startsWith("-")) {
                hostmask = IRCUtils.getHostmask(network, args[0].replaceFirst("-", ""), true);
            } else {
                hostmask = IRCUtils.getHostmask(network, args[0], true);

            }

        }
        if (args[0].startsWith("+")) {
            if (QuietTimeUtils.getQuietTime(hostmask) != null) {
                if (args[0].startsWith("+")) {
                    if (args[1].startsWith("+")) {
                        QuietTimeUtils.getQuietTime(hostmask).setTime(QuietTimeUtils.getQuietTime(hostmask).getTime() + GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                    } else if (args[1].startsWith("-")) {
                        QuietTimeUtils.getQuietTime(hostmask).setTime(QuietTimeUtils.getQuietTime(hostmask).getTime() - GeneralUtils.getMilliSeconds(args[1].replace("-", "")));

                    } else {
                        QuietTimeUtils.getQuietTime(hostmask).setTime(GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    }
                    IRCUtils.sendAction(user, network, channel, "Quiet Modified", prefix);
                    QuietTimeUtils.saveQuietTimes();
                }
            } else {
                IRCUtils.sendError(user, "Quiet does not exist!");
            }
        } else if (args[0].startsWith("-")) {
            if (QuietTimeUtils.getQuietTime(hostmask) != null) {
                QuietTimeUtils.getQuietTime(hostmask).setTime(0);
                QuietTimeUtils.saveQuietTimes();
            } else {
                IRCUtils.setMode(channel, network, "-" + GeneralRegistry.QuietBans.get(ircd), hostmask);
            }
        } else {

            if (QuietTimeUtils.getQuietTime(hostmask) == null) {
                if (args.length == 2) {
                    IRCUtils.setMode(channel, network, "+" + GeneralRegistry.QuietBans.get(ircd), hostmask);
                    UTime c = new UTime(hostmask, network.getServerInfo().getNetwork(), ircd, channel.getName(), GeneralUtils.getMilliSeconds(args[1]), System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                } else if (args.length < 2) {
                    IRCUtils.setMode(channel, network, "+" + GeneralRegistry.QuietBans.get(ircd), hostmask);
                    UTime c = new UTime(hostmask, network.getServerInfo().getNetwork(), ircd, channel.getName(), GeneralUtils.getMilliSeconds("24h"), System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                }
            } else {
                IRCUtils.sendError(user, "Quiet already exists!");
            }
        }
    }


}
