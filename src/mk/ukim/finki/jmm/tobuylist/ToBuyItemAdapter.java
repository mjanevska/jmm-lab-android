package mk.ukim.finki.jmm.tobuylist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mk.ukim.finki.jmm.tobuylist.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;



public class ToBuyItemAdapter extends ArrayAdapter<ToBuyItem> {
	int resource;

	public ToBuyItemAdapter(Context context, int resource, List<ToBuyItem> items) {
		super(context, resource, items);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout todoView;
		ToBuyItem item = getItem(position);
		String taskString = item.getTask();
		Date createdDate = item.getCreated();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String dateString = sdf.format(createdDate);
		if (convertView == null) {
			todoView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li;
			li = (LayoutInflater) getContext().getSystemService(inflater);
			li.inflate(resource, todoView, true);
		} else {
			todoView = (LinearLayout) convertView;
		}
		TextView dateView = (TextView) todoView.findViewById(R.id.rowDate);
		TextView taskView = (TextView) todoView.findViewById(R.id.row);
		dateView.setText(dateString);
		taskView.setText(taskString);
		return todoView;
	}
}
