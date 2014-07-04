package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.hooks.events.MessageEvent;


public class FindIP extends Command {
    @CMD
    public FindIP() {
        super(GeneralUtils.toArray("findip locate find loc"), 0, "findip [ipv4]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        JsonObject objectJson = GeneralUtils.getJsonObject("http://freegeoip.net/json/" + args[0]);
        String x = objectJson.get("city").getAsString() + ", " + objectJson.get("region_name").getAsString() + ", " + objectJson.get("zipcode").getAsString();
        if (x.equalsIgnoreCase(", , ")) {
            event.getChannel().send().message("IP is Protected");
        } else {
            event.getChannel().send().message(x);
        }

    }
}
