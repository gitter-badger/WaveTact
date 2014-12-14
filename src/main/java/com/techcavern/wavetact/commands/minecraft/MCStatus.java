package com.techcavern.wavetact.commands.minecraft;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@CMD
@GenCMD
public class MCStatus extends GenericCommand {

    public MCStatus() {
        super(GeneralUtils.toArray("mcstatus"), 0, null, "checks status of MC servers", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String result;
        URL url = new URL("https://status.mojang.com/check");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        result = reader.readLine().replace("\"},{\"", " | ").replace("\":\"", ": ").replace("green", "Online").replace("red", "Offline").replace("[{\"", "").replace("\"}]", "").replace(".minecraft.net", "").replace(".mojang.com", "").replace("server", " Server").replace(".net", "");
        result = WordUtils.capitalizeFully(result);
        if (result != null) {
            IRCUtils.sendMessage(user, network, channel, result, prefix);
        } else {
            IRCUtils.sendError(user, "MC Status Currently Unavailable");
        }
    }

}

