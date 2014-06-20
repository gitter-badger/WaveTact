package com.techcavern.wavetact.commands;

import java.util.concurrent.TimeUnit;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.IRCUtils;


public class Ban extends ListenerAdapter<PircBotX> {
	public void onMessage(MessageEvent<PircBotX> event) throws Exception{
		String[] messageParts = event.getMessage().split(" ");
			if (messageParts[0].equalsIgnoreCase((GeneralRegistry.CommandChar + "ban"))){
			if(10 <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())){
				if (messageParts.length == 3 && messageParts[1].startsWith("-") == false){
					if(messageParts[2].endsWith("s") || messageParts[2].endsWith("h") || messageParts[2].endsWith("m") || messageParts[2].endsWith("d")){
                                        bantime time = new bantime();
					time.run(messageParts[2],IRCUtils.getUserByNick(event.getChannel(), messageParts[1]), event.getChannel(), event.getBot());
                                        } else {
                                            IRCUtils.SendNotice(event.getBot(), event.getUser(), " Ensure you have specified a valid time (30s = 30 Seconds, 30m = 30 minutes, up to days)");
                                        }
				}else if(messageParts.length < 3 && messageParts[1].startsWith("-") == false){                                        
					ban(IRCUtils.getUserByNick(event.getChannel(), messageParts[1]), event.getChannel(), event.getBot());
				}else if(messageParts[1].startsWith("-")){
					unban(IRCUtils.getUserByNick(event.getChannel(), messageParts[1].replace("-", "")), event.getChannel(), event.getBot());

				}
				else{
				IRCUtils.SendNotice(event.getBot(), event.getUser(), "Syntax: @quiet [ircd code] (-)[User to Quiet] (time in seconds)");
				}
				
			}else {
            			event.getChannel().send().message("Permission Denied"); 

			}
			}
	}
		public class bantime extends Thread{
			public void run(String i, User u, Channel c, PircBotX b) throws InterruptedException{
				ban(u,c,b);
                                if(i.endsWith("s")){
                                    int e = Integer.parseInt(i.replace("s", ""));
                                    TimeUnit.SECONDS.sleep(e);
                                } else if(i.endsWith("m")){
                                    int e = Integer.parseInt(i.replace("m", ""));
                                    TimeUnit.MINUTES.sleep(e);
                                }else  if(i.endsWith("h")){
                                    int e = Integer.parseInt(i.replace("h", ""));
                                    TimeUnit.HOURS.sleep(e);
                                }else  if(i.endsWith("d")){
                                    int e = Integer.parseInt(i.replace("d", ""));
                                    TimeUnit.DAYS.sleep(e);
                                }
                                    

				unban(u,c,b);
		}
		}
		
		public void ban(User u, Channel c, PircBotX b){

				IRCUtils.setMode(c, b, "+b ", u);
		
			
	
}
		public void unban(User u, Channel c, PircBotX b){
				IRCUtils.setMode(c, b, "-b ", u);
}
}