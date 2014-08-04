package com.techcavern.wavetact.commands.auth;

import com.techcavern.wavetact.annot.AuthCMD;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.AccountUtils;
import com.techcavern.wavetact.utils.objects.Account;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


public class SetPass extends GenericCommand {
    @CMD
    @AuthCMD
    public SetPass() {
        super(GeneralUtils.toArray("setpassword setpass changepassword changepass"), 0, "setpass [oldpass] [newpass]", "Sets Password");
    }
    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if(!PermUtils.checkIfAccountEnabled(Bot)){
            user.send().notice("This network is set to " + GetUtils.getAuthType(Bot) + " Authentication");
            return;
        }
        String AuthUser = PermUtils.getAuthedAccount(Bot, user.getNick());
        if(AuthUser != null){
            Account acc = AccountUtils.getAccount(AuthUser);
            if(GeneralRegistry.encryptor.checkPassword(args[0], acc.getAuthPassword())){
                acc.setAuthPassword(GeneralRegistry.encryptor.encryptPassword(args[1]));
                AccountUtils.saveAccounts();
                IRCUtils.SendMessage(user, channel, "Password Changed Successfully", isPrivate);
            }else{
                user.send().notice("Incorrect password");
            }
        }else{
            user.send().notice("Error - You must be identified to use this command");
        }
        }
    }

