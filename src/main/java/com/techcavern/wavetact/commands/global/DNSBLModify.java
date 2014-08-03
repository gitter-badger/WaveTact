/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.global;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GloCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.DNSBLUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
public class DNSBLModify extends GenericCommand {
    @CMD
    @GloCMD

    public DNSBLModify() {
        super(GeneralUtils.toArray("dnsblm dnsblmodify"), 20, "DNSBLModify [IRC DNSBL]","Adds/Removes Domains from Spam DNS Blacklists");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String Domain = GetUtils.getDNSBLbyDomain(args[0]);

        if (args[0] != null) {
            if (args[0].startsWith("-")) {
                if(Domain != null){
                    GeneralRegistry.DNSBLs.remove(Domain);
                    DNSBLUtils.saveDNSBLs();
                    IRCUtils.SendMessage(user, channel, "Spam DNSBL Removed", isPrivate);
                }else{
                    user.send().notice("Spam DNSBL does not exist on list");
                }
            } else {
                if(Domain == null){
                    GeneralRegistry.DNSBLs.add(args[0]);
                    DNSBLUtils.saveDNSBLs();
                    IRCUtils.SendMessage(user, channel, "Spam DNSBL Added", isPrivate);
                }else{
                    user.send().notice("Spam DNSBL already listed");
                }
            }
        } else {
            user.send().notice("Please Specifiy an Spam DNBL");
        }
    }
}
