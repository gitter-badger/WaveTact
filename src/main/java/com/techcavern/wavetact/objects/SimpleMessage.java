/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.objects;

import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class SimpleMessage extends Command {

    private String message;
    private boolean locked;

    public SimpleMessage(String i, int p, String m, boolean b) {
        super(i, p, "A Basic Command that takes no Arguments");
        this.message = m;
        this.locked = b;
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.respond(message);
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean getLockedStatus() {
        return locked;
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }
}