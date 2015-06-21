package com.petrows.mtcservice.hal;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public abstract class ISwc {
	protected static final String TAG = "ISwc";

	public enum Key {
		KEY_NONE,
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

	public void init ()	{}

	public interface KeyHandler {
		void onKeyPressMapped(Key key);
		void onKeyPressRaw(String keyId);
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

	protected void sendPressRaw(String id)
	{
		for (KeyHandler h : keyHandlers) { h.onKeyPressRaw(id); }
	}

	protected boolean blockMapped = false;
	public void setBlockMapped(boolean block)
	{
		Log.d(TAG, "Mapping keys disable status: " + block);
		blockMapped = block;
	}
	protected abstract Key mapKeyDefault(String id); // If not defined in settings - current HAL should return default key
	protected Key mapKey(String id)
	{
		return Key.KEY_NONE;
	}

	protected void sendPressMapped(Key key)
	{
		if (blockMapped)
		{
			Log.d(TAG, "Mapped keys are disabled!");
			return;
		}

		for (KeyHandler h : keyHandlers) { h.onKeyPressMapped(key); }
	}
}
