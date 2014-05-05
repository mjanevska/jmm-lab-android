package mk.ukim.finki.jmm.tobuylist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ToBuyListActivity extends Activity {

	private EditText mNewTask = null;
	private ListView mTasks = null;
	private ToBuyItemAdapter mAdapter = null;
	private TextView textView = null;

	private List<ToBuyItem> items = new ArrayList<ToBuyItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tobuy_list);
		
		textView = (TextView) findViewById(R.id.numberItems);
		mNewTask = (EditText) findViewById(R.id.newTask);
		mTasks = (ListView) findViewById(R.id.todoItems);
		int resID = R.layout.tobuylist_item;
		mAdapter = new ToBuyItemAdapter(this, resID, items);
		/*mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);*/
		mTasks.setAdapter(mAdapter);

	}

	public void openFragmentActivity(View view) {
		Intent intent = new Intent(this, ToBuyListWithFragments.class);
		this.startActivityForResult(intent, 1);
	}

	public void addTask(View view) {

		String text = mNewTask.getText().toString();
		ToBuyItem nn = new ToBuyItem(text);
		items.add(nn);
		mNewTask.setText("");
		mAdapter.notifyDataSetChanged();

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {
			 if(resultCode == RESULT_OK){      
			         String result=data.getStringExtra("result"); 
			         textView.setText(result+ " important items");
			         Toast.makeText(ToBuyListActivity.this,"Important list edited", Toast.LENGTH_LONG).show();
			     }
		  }
		}//onActivityResult

}
