package com.techcavern.wavetact.commands.auth;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.AccountUtils;
import com.techcavern.wavetact.utils.objects.Account;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class SetPass extends GenericCommand {

    public SetPass() {
        super(GeneralUtils.toArray("setpassword setpass changepassword changepass"), 0, "setpass [oldpass] [newpass]", "Sets Password", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.checkIfAccountEnabled(network)) {
            IRCUtils.sendError(user, "This network is set to " + GetUtils.getAuthType(network) + " Authentication");
            return;
        }
        String AuthUser = PermUtils.authUser(network, user.getNick());
        if (AuthUser != null) {
            Account acc = AccountUtils.getAccount(AuthUser);
            if (GeneralRegistry.encryptor.checkPassword(args[0], acc.getAuthPassword())) {
                acc.setAuthPassword(GeneralRegistry.encryptor.encryptPassword(args[1]));
                AccountUtils.saveAccounts();
                IRCUtils.sendMessage(user, network, channel, "Password Changed Successfully", prefix);
            } else {
                IRCUtils.sendError(user, "Incorrect password");
            }
        } else {
            IRCUtils.sendError(user, "Error - You must be identified to use this command");
        }
    }
}

