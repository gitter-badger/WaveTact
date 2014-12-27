package com.techcavern.wavetact.console.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by roelf on 12/27/14.
 */
public class CommandVariables {
	private InputStream is;
	private OutputStream os;
	private PrintStream ps;

	public CommandVariables(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
		this.ps = new PrintStream(os, true);
	}

	public OutputStream getOutputStream()
	{
		return os;
	}

	public InputStream getInputStream()
	{
		return is;
	}

	public PrintStream getPrintStream()
	{
		return ps;
	}
}
