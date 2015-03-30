package com.petrows.mtcservice.appcontrol;

import com.petrows.mtcservice.R;

/**
 * Created by petro on 3/26/15.
 */
public class ControllerPcRadio extends ControllerBase {
	@Override
	public String getId() {
		return "pcradio";
	}

	@Override
	public String getName() {
		return ctx.getString(R.string.appcontrol_name_pcradio);
	}
}
