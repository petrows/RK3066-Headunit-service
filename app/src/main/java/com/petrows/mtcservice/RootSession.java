package com.petrows.mtcservice;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by petro on 06.04.2015.
 */
public class RootSession {
	private final static String TAG = "RootSession";
	private static RootSession instance = null;
	private Context ctx = null;
	private RootSession(Context context) {
		ctx = context;
	}
	public static RootSession get(Context context)
	{
		if (null == instance)
			instance = new RootSession(context);
		return (instance);
	}

	private Process suProcess;
	private DataOutputStream suWrite;
	private DataInputStream suRead;
	private boolean isOpen = false;

	public boolean open()
	{
		if (isOpened()) return true; // Already running
		isOpen = false;
		try {
			suProcess = Runtime.getRuntime().exec("su");

			suWrite = new DataOutputStream(suProcess.getOutputStream());
			suRead = new DataInputStream(suProcess.getInputStream());

			if (null != suWrite && null != suRead) {
				// Getting the id of the current user to check if this is root
				suWrite.writeBytes("id\n");
				suWrite.flush();

				String currUid = suRead.readLine();
				boolean exitSu = false;

				if (null == currUid) {
					isOpen = false;
					exitSu = false;
					Log.d(TAG, "Can't get root access or denied by user");
				} else if (true == currUid.contains("uid=0")) {
					isOpen = true;
				} else {
					isOpen = false;
					exitSu = true;
					Log.d(TAG, "Root access rejected: " + currUid);
				}

				if (exitSu) {
					suWrite.writeBytes("exit\n");
					suWrite.flush();
					suProcess = null;
				}
			}
		} catch (Exception e) {
			// Can't get root !
			// Probably broken pipe exception on trying to write to output stream (os) after su failed, meaning that the device is not rooted

			isOpen = false;
			Log.d("ROOT", "Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage());
		}

		return isOpen;
	}

	public boolean isOpened()
	{
		return isOpen;
	}

	public boolean exec(String[] cmd)
	{
		if (!isOpened()) return false;
		for (String currCommand : cmd) {
			if (!exec(currCommand)) return false; // Error in exec
		}
		return true;
	}

	public boolean exec(String command)
	{
		if (!isOpened()) return false;
		Log.d(TAG, "Try exec root: " + command);
		try {
			suWrite.writeBytes(command + "\n");
			suWrite.flush();
		} catch (IOException e) {
			Log.d(TAG, "Root write failed!");

			// Try to open again:
			isOpen = false;
			if (open())	{
				// Try again
				try {
					suWrite.writeBytes(command + "\n");
					suWrite.flush();
				} catch (IOException e2) {
					return false;
				}
				return true;
			}

			e.printStackTrace();
			return false;
		}

		return true;
	}
}
