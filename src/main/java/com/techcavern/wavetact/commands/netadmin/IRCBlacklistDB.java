/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.netadmin;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.NAdmCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.IRCBLUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@NAdmCMD
public class IRCBlacklistDB extends GenericCommand {


    public IRCBlacklistDB() {
        super(GeneralUtils.toArray("ircblacklistdb ircbldb"), 20, "ircblacklistdb (-)[IRC DNSBL]", "Adds/Removes Domains from IRCBL");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (args.length > 0) {
            if (args[0].startsWith("-")) {
                String Domain = GetUtils.getIRCDNSBLbyDomain(args[0].replaceFirst("-", ""));
                if (Domain != null) {
                    GeneralRegistry.IRCBLs.remove(Domain);
                    IRCBLUtils.saveIRCBLs();
                    IRCUtils.sendMessage(user, channel, "IRC DNSBL Removed", isPrivate);
                } else {
                    IRCUtils.sendError(user, "IRC DNSBL does not exist on list");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (!GeneralRegistry.IRCBLs.isEmpty()) {
                    IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.IRCBLs, ", "), isPrivate);
                } else {
                    IRCUtils.sendError(user, "IRC DNS Blacklist is Empty");
                }
            } else {
                String Domain = GetUtils.getIRCDNSBLbyDomain(args[0]);
                if (Domain == null) {
                    GeneralRegistry.IRCBLs.add(args[0]);
                    IRCBLUtils.saveIRCBLs();
                    IRCUtils.sendMessage(user, channel, "IRC DNSBL Added", isPrivate);
                } else {
                    IRCUtils.sendError(user, "IRC DNSBL already listed");
                }
            }
        } else {
            IRCUtils.sendError(user, "Please Specifiy an IRC DNBL");
        }
    }
}