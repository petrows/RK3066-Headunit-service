package com.petrows.mtcservice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

	private final static String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		TextView txtVersion = (TextView) findViewById(R.id.txtVersion);
		txtVersion.setText("Version: " + Settings.get(this).getVersion());

		Switch chService = (Switch) findViewById(R.id.swServiceEnable);
		chService.setChecked(Settings.get(this).getServiceEnable());
		chService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				setServicesEnable(b);
			}
		});

		Settings.get(this).startMyServices();
	}

	protected void setServicesEnable(boolean b) {
		Settings.get(this).setServiceEnable(b);
		Settings.get(this).startMyServices();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnSettings:
				// Run advanced settings
				Intent intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				break;
			case R.id.btnContact:
				// Run advanced settings
				Intent intentMail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
						"mailto", "petro@petro.ws", null));
				intentMail.putExtra(Intent.EXTRA_SUBJECT, "Microntek headunit app");
				startActivity(Intent.createChooser(intentMail, getString(R.string.ui_contact_title)));

				break;
		}
	}
}
