/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.SimpleMessage;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jztech101
 */
public class DNSInfo extends GenericCommand {

    @CMD
    @TruCMD
    public DNSInfo() {
        super(GeneralUtils.toArray("dnsinfo dns"), 5, "dns [domain]" , "looks up a domain for information");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
       String domain = args[0];
        if(args[0].contains("http")){
            domain.replace("http://", "");
        }
        Lookup lookup = new Lookup(domain, Type.ANY);
        Resolver resolver = new SimpleResolver();
        lookup.setResolver(resolver);
        lookup.setCache(null);
        Record[] records = lookup.run();
        boolean isSuccesful = false;
        if(lookup.getResult() == lookup.SUCCESSFUL){
            for(Record  rec: records){
                if(rec instanceof  ARecord){
                    ARecord c = (ARecord) rec;
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((ARecord) rec).getAddress() , isPrivate );
                }else if(rec instanceof  NSRecord){
                    NSRecord c = (NSRecord) rec;
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((NSRecord) rec).getTarget() , isPrivate );
                }else if(rec instanceof  AAAARecord) {
                    AAAARecord c = (AAAARecord) rec;
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((AAAARecord) rec).getAddress(), isPrivate);
                } else if(rec instanceof  CNAMERecord){
                    CNAMERecord c = (CNAMERecord) rec;
                IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((CNAMERecord) rec).getTarget() , isPrivate );
            }else if(rec instanceof  TXTRecord){
                    TXTRecord c = (TXTRecord) rec;
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((TXTRecord) rec).toString() , isPrivate );
            }else if(rec instanceof  MXRecord){
                    MXRecord c = (MXRecord) rec;
            IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((MXRecord) rec).getTarget() , isPrivate );
        }
    }
            isSuccesful = true;
        }
        if(!isSuccesful){
            user.send().notice("Invalid Domain");
        }

    }

}

