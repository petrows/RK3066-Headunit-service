package com.petrows.mtcservice.hal.c200;

import android.util.Log;

import com.petrows.mtcservice.hal.IHeadUnit;

public class HeadUnit extends IHeadUnit {
	@Override
	public boolean detectUnit() {
		return false;
	}

	@Override
	public HalType getType() {
		return HalType.HAL_C200;
	}

	@Override
	public String getName() {
		return "C200";
	}
}
