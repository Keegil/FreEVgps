package com.example.freespot;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.freespot.database.Logging;

public class CustomListViewAdapter extends ArrayAdapter<Logging> {

	Context context;

	public CustomListViewAdapter(Context context, int resourceId,
			List<Logging> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		TextView li_id;
		TextView li_type;
		TextView li_date;
		TextView li_cost;
		TextView li_time;
		TextView li_totalcost;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Logging rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			
			holder.li_id = (TextView) convertView.findViewById(R.id.li_id);
			holder.li_type = (TextView) convertView.findViewById(R.id.li_type);
			holder.li_date = (TextView) convertView.findViewById(R.id.li_date);
			holder.li_cost = (TextView) convertView.findViewById(R.id.li_cost);
			holder.li_time = (TextView) convertView.findViewById(R.id.li_time);
			holder.li_totalcost = (TextView) convertView.findViewById(R.id.li_totalcost);
			
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.li_id.setText(""+rowItem.getId());
		holder.li_type.setText(rowItem.getType());
		holder.li_date.setText(""+rowItem.getDate());
		holder.li_cost.setText(""+rowItem.getCosts());
		holder.li_time.setText(""+rowItem.getTime());
		holder.li_totalcost.setText(""+rowItem.getTotalCosts()+" NOK");

		return convertView;
	}
}