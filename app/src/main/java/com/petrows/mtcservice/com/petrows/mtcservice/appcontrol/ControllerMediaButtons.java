package com.petrows.mtcservice.com.petrows.mtcservice.appcontrol;

public class ControllerMediaButtons extends ControllerBase {
	@Override
	public String getId() {
		return "media";
	}

	@Override
	public String getName() {
		return "Media buttons";
	}

	@Override
	public boolean init() {
		return true;
	}
}
