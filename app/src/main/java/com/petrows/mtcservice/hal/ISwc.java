package com.petrows.mtcservice.hal;

import android.content.Context;

import java.util.ArrayList;

public abstract class ISwc {
	public enum Key {
		KEY_STOP,
		KEY_PAUSE,
		KEY_NEXT,
		KEY_PREV
	}

	protected Context ctx;

	public ISwc (Context context)
	{
		ctx = context;
	}

	public interface KeyHandler {
		void onKeyPress(Key key);
	}

	private ArrayList<KeyHandler> keyHandlers = new ArrayList<>();
	public void setHandler(KeyHandler cb)
	{
		keyHandlers.add(cb);
	}
	public void removeHandler(KeyHandler cb)
	{
		keyHandlers.remove(cb);
	}

	private void sendPress(Key key)
	{
		for (KeyHandler h : keyHandlers) { h.onKeyPress(key); }
	}
}
