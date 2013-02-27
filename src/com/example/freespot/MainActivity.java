package com.example.freespot;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freespot.AlertDialogRadio.AlertPositiveListener;
import com.example.freespot.EditNameDialog.EditNameDialogListener;
import com.example.freespot.database.ProductDataSource;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener, EditNameDialogListener, AlertPositiveListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "Navigation_item_selected";
	public final static String EXTRA_MESSAGE = "com.example.test.MESSAGE";

	private static final String LOG_TAG = "freespot_OverView";
	private String names = "";
	Button selectB;
	TextView tv;
	TextView tv2;

	private String productname = "";

	private ProductDataSource prosource;

	private ProgressBar pb;

	// Dialogradio - store position
	int position = 0;

	int productPrice;

	ImageView toll_arrow;

	ListView listView;

	// Intent intent = new Intent(this, Excersice.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setting the layout
		setContentView(R.layout.activity_main);

		// Creating tabs when the program starts
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding new tabs
		actionBar.addTab(actionBar.newTab().setText("Overview")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Savings Log")
				.setTabListener(this));

		prosource = new ProductDataSource(this);
		prosource.open();

	}

	public static void setTabColor(TabHost tabhost) {
		for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
			tabhost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#FFFFFF")); // unselected
		}
		tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#FFFFFF")); // selected
	}

	public String getProductName() {

		return productname;
	}

	public void startDialogRadio() {

		FragmentManager manager = getFragmentManager();

		/** Instantiating the DialogFragment class */
		AlertDialogRadio alert = new AlertDialogRadio();

		/** Creating a bundle object to store the selected item's index */
		Bundle b = new Bundle();

		/** Storing the selected item's index in the bundle object */
		b.putInt("position", position);

		/** Setting the bundle object to the dialog fragment object */
		alert.setArguments(b);

		/**
		 * Creating the dialog fragment object, which will in turn open the
		 * alert dialog window
		 */
		alert.show(manager, "alert_dialog_radio");
	}

	// Saving the tab instance
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putString("saveNames", names);
		// etc.
	}

	// To ensure the program stays on the same tab after restore
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		// i.e. String myString = savedInstanceState.getString("MyString");
		setProduct(savedInstanceState.getString("saveNames"));

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	public void refreshOVerView() {
		OverView ov = new OverView();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, ov).commit();
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// Switch to the right tab
		switch (tab.getPosition()) {
		case 0:
			OverView ov = new OverView();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, ov).commit();
			// Toast.makeText(this, tab.getText().toString(),
			// Toast.LENGTH_SHORT).show();
			break;
		case 1:
			LogList ex = new LogList();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, ex).commit();
			// Toast.makeText(this, tab.getText().toString(),
			// Toast.LENGTH_SHORT).show();
			break;
		}

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	// Called when dialog is finished
	@Override
	public void onFinishEditDialog(String inputText) {
		Log.d(LOG_TAG, "Called:	onFinishEditDialog");

		// Fragments don't extend context. You have to get the activity to pass
		// as the context. (this -> OverView.this.getActivity())
		Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
		setProduct(inputText);
	}

	public void setProduct(String n) {
		names = n;
		if (!names.contentEquals("")) {
			tv = (TextView) findViewById(R.id.savingitem);
			tv.setVisibility(View.VISIBLE);
			tv.setText(n);
			// tv2 = (TextView) findViewById(R.id.savingfor);
			// tv2.setVisibility(View.VISIBLE);
			selectB = (Button) findViewById(R.id.selectb);
			selectB.setVisibility(View.GONE);
		}
	}

	public void changeArrow(int r_id) {
		toll_arrow = (ImageView) findViewById(r_id);
		toll_arrow.setImageResource(R.drawable.arrowup);
	}

	public void changeArrowD(int r_id) {
		toll_arrow = (ImageView) findViewById(r_id);
		toll_arrow.setImageResource(R.drawable.arrowdown);
	}

	/**
	 * Defining button click listener for the OK button of the alert dialog
	 * window
	 */
	@Override
	public void onPositiveClick(int position) {
		this.position = position;

		/** Getting the reference of the textview from the main layout */
		TextView tv = (TextView) findViewById(R.id.savingitem);

		/** Setting the selected android version in the textview */
		tv.setText("You are saving for: "
				+ ProductSelection.code[this.position]);

		productname = ProductSelection.code[this.position];

		switch (position) {
		case 0:
			productPrice = 9000;
			break;
		case 1:
			productPrice = 4000;
			break;
		case 2:
			productPrice = 5000;
			break;
		case 3:
			productPrice = 3000;
			break;
		case 4:
			productPrice = 3500;
			break;
		case 5:
			productPrice = 15000;
			break;
		}

		Log.d(LOG_TAG, "ProductPrice position: " + position);

		prosource.createProduct(productname, productPrice);

		// finding progressbar
		pb = (ProgressBar) findViewById(R.id.pgbAwardProgress);
		pb.setMax(productPrice);

	}

}
