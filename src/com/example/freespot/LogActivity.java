package com.example.freespot;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.freespot.database.Logging;
import com.example.freespot.database.LoggingDataSource;

public class LogActivity extends Activity {

	private LoggingDataSource datasource;
	private static final String LOG_TAG = "FreEV_LogActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content
		setContentView(R.layout.activity_log);

		Bundle extras = getIntent().getExtras();
		int position = extras.getInt("pos");

		datasource = new LoggingDataSource(this);
		datasource.open();

		Logging log = datasource.getLog(position);

		TextView tdate = (TextView) findViewById(R.id.tdate);
		TextView tcost = (TextView) findViewById(R.id.ttcost);
		TextView ttotal = (TextView) findViewById(R.id.ttotalc);
		TextView ttime = (TextView) findViewById(R.id.ttime);
		TextView theader = (TextView) findViewById(R.id.headerlogact);

		theader.setText(log.getType() + " Ticket");
		tdate.setText(log.getDate());
		tcost.setText(log.getType() + " Costs: " + log.getCosts());
		if (log.getType().equals("Parking")) {
			ttime.setVisibility(View.VISIBLE);
			ttime.setText(log.getType() + " Time: " + log.getTime());
		}
		ttotal.setText("Total " + log.getType() + " Costs: "
				+ log.getTotalCosts());

		Log.d(LOG_TAG, "Position: " + position + "ID: " + log.getId()
				+ " Name: " + log.getType() + " Date: " + log.getDate()
				+ " Cost: " + log.getCosts() + " Time: " + log.getTime()
				+ " Total costs: " + log.getTotalCosts());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return false;
	}
}
