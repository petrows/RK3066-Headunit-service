package com.petrows.mtcservice.hal.mtc;

import android.content.Context;
import android.util.Log;

import com.petrows.mtcservice.hal.IHeadUnit;
import com.petrows.mtcservice.hal.ISwc;

public class HeadUnit extends IHeadUnit {
	@Override
	public boolean detectUnit() {
		return true;
	}

	@Override
	public HalType getType() {
		return HalType.HAL_MTC;
	}

	@Override
	public String getName() {
		return "Microntek";
	}

	@Override
	public boolean init(Context context) {
		super.init(context);
		return true;
	}

	@Override
	public ISwc getSwc() {
		return new Swc(ctx);
	}
}
