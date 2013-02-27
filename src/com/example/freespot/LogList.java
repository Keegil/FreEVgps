package com.example.freespot;

import java.util.List;
import java.util.ListIterator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.freespot.database.Logging;
import com.example.freespot.database.LoggingDataSource;

public class LogList extends ListFragment {

	private int idd;

	private LoggingDataSource datasource;

	private static final String LOG_TAG = "FreEV_LogList";

	List<Logging> values;

	/*
	 * 
	 * SETTING UP TEST ARRAYS FOR CUSTOM LIST VIEW ADAPTER
	 * 
	 * 
	 * List<Logging> rowItems;
	 * 
	 * // Test set public static final long[] id_set = new long[] { 1, 2, 3, 4
	 * }; public static final String[] type_set = new String[] { "Parking",
	 * "Toll", "Parking", "Toll" }; public static final String[] date_set = new
	 * String[] { "01.01.2013", "02.02.2013", "03.03.2013", "04.04.2013" };
	 * public static final int[] time_set = new int[] { 10, 20, 30, 40 }; public
	 * static final int[] costs_set = new int[] { 15, 25, 35, 45 }; public
	 * static final int[] totalcosts_set = new int[] { 150, 500, 1050, 1800 };
	 * 
	 * /*
	 * 
	 * 
	 * END OF VARIABLES USED FOR TESTING CUSTOM LIST VIEW ADAPTER
	 * 
	 * 
	 * 
	 * *
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		datasource = new LoggingDataSource(getActivity());
		datasource.open();

		values = datasource.getAllLogs();

		// Get the id of the first element in values-list
		if (!values.isEmpty())
			idd = (int) values.get(0).getId();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_loglist, container, false);

		/*
		 * POPULATING TEST ARRAY ROWITEMS FOR CUSTOM LIST VIEW ADAPTER
		 * 
		 * rowItems = new ArrayList<Logging>(); for (int i = 0; i <
		 * id_set.length; i++) { Logging item = new Logging(id_set[i],
		 * type_set[i], date_set[i], time_set[i], costs_set[i],
		 * totalcosts_set[i]); rowItems.add(item); }
		 * 
		 * END OF TESTING CUSTOM LIST VIEW ADAPTER
		 */

		// Testing Custom LIST VIEW ADAPTER
		CustomListViewAdapter adapter = new CustomListViewAdapter(
				getActivity(), R.layout.list_item, values);
		setListAdapter(adapter);
		// END OF TESTING

		// finding button
		Button bselect = (Button) v.findViewById(R.id.delete);

		// setting onclicklistened
		bselect.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// delete the first item in the list
				/*
				 * ArrayAdapter<Logging> adapter = (ArrayAdapter<Logging>)
				 * getListAdapter(); Logging log = null; if
				 * (getListAdapter().getCount() > 0) { log = (Logging)
				 * getListAdapter().getItem(0); datasource.deleteLog(log);
				 * adapter.remove(log); }
				 */
				// delete the entire list
				Logging log = null;
				ArrayAdapter<Logging> adapter = (ArrayAdapter<Logging>) getListAdapter();

				for (int i = 0; i < adapter.getCount(); i++) {
					log = (Logging) getListAdapter().getItem(i);
					datasource.deleteLog(log);
					adapter.remove(log);
				}


			}
		});

		return v;

	}

	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {

		// diff idd(id database) and position(list click) (might differ if
		// deleted list - auto increment - new entry starts from previous last
		// item + 1)
		int newpos = position + idd;

		/**
		 * Toast message will be shown when you click any list element
		 */
		Toast.makeText(
				getActivity(),
				values.get(position).getType() + " ticket \n"
						+ values.get(position).getDate(), Toast.LENGTH_SHORT)
				.show();
		// Alternative method to get the first element in the clicked row, i.e.
		// parking/toll
		// getListView().getItemAtPosition(position).toString()

		Log.d(LOG_TAG, "position: " + position + "ID:" + idd);
		Intent i = new Intent(getActivity(), LogActivity.class);
		i.putExtra("pos", newpos);
		startActivity(i);

	}

}