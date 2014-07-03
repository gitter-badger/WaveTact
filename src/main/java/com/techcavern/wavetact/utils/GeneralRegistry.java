package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.*;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class GeneralRegistry {

    public static final List<String> Controllers = Arrays.asList("jztech101", "archtikz", "kaendfinger", "deathcrazyuberlironman", "leah");
    public static final List<String> ControllerHostmasks = Arrays.asList("*!jztech101@techcavern.com");
    public static final List<Command> Commands = new LoggingArrayList<Command>("Command");
    public static final List<SimpleMessage> SimpleMessages = new LoggingArrayList<SimpleMessage>("SimpleMessages");
    public static final List<SimpleAction> SimpleActions = new LoggingArrayList<SimpleAction>("SimpleActions");
    public static final List<String> HighFives = new ArrayList<String>();
    public static final MultiBotManager<PircBotX> WaveTact = new MultiBotManager<PircBotX>();
    public static final ForkJoinPool TASKS = new ForkJoinPool();
    public static final List<Command> COMMANDS = new LinkedList<Command>();
    public static Map<String, Configuration> configs = new HashMap<String, Configuration>();
    public static final List<UTime> BanTimes = new LoggingArrayList<UTime>("BanTimes");
    public static final List<UTime> QuietTimes = new LoggingArrayList<UTime>("QuietTimes");
    public static final List<CommandChar> CommandChars= new LoggingArrayList<CommandChar>("CommandChar");
    public static final List<CommandLine> CommandLines = new LoggingArrayList<CommandLine>("CommandLine");


    //Development Variables
    /**
    public static final String CommandChar = "@";
    public static final String DevServer = "irc.esper.net";
    public static final List<String> DevChannels = Arrays.asList("#TechCavern");
    public static final String DevNick = "WaveTactDev";
    **/
}
