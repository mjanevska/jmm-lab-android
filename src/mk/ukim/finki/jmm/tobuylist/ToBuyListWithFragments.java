package mk.ukim.finki.jmm.tobuylist;

//import java.io.IOException;
import java.util.ArrayList;

import mk.ukim.finki.jmm.tobuylist.fragments.ToBuyListFragment;
import mk.ukim.finki.jmm.tobuylist.listeners.OnNewItemAddedListener;
//import mk.ukim.finki.jmm.tobuylist.objectserialize.ObjectSerializer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

public class ToBuyListWithFragments extends FragmentActivity implements
		OnNewItemAddedListener {

	private ToBuyItemAdapter mAdapter;
	private ArrayList<ToBuyItem> mTodoItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Inflate your view
		setContentView(R.layout.activity_tobuy_list_with_fragments);

		// Get references to the Fragments
		// for API versions >= 11 use getFragmentManager()
		FragmentManager fm = getSupportFragmentManager();

		ToBuyListFragment todoListFragment = (ToBuyListFragment) fm
				.findFragmentById(R.id.TodoListFragment);

		// Create the array list of to do items
		//mTodoItems = new ArrayList<ToBuyItem>();
		if (null == mTodoItems) {
			// Create the array list of to do items
			mTodoItems = new ArrayList<ToBuyItem>();
		}

		//		load tasks from preference
		SharedPreferences prefs = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
		int size=prefs.getInt("size",0);

		for(int j=0;j<size;j++)
		{
		          mTodoItems.add(new ToBuyItem(prefs.getString("val"+j, "")));
		}
		
		int resID = R.layout.tobuylist_item;
		mAdapter = new ToBuyItemAdapter(this, resID, mTodoItems);
		
		// Create the array adapter to bind the array to the listview
		/*mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mTodoItems);*/

		// Bind the array adapter to the listview.
		todoListFragment.setListAdapter(mAdapter);
	}

	@Override
	public void onNewItemAdded(String newItem) {
		
		assert(null != newItem);
		if (null == mTodoItems) {
			mTodoItems = new ArrayList<ToBuyItem>();
		}
		ToBuyItem am =  new ToBuyItem(newItem);
		mTodoItems.add(am);

		//save the task list to preference
		SharedPreferences prefs = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();

		for(int i=0;i<mTodoItems.size();i++)
		{
		         editor.putString("val"+i,mTodoItems.get(i).getTask());
		}
		editor.putInt("size",mTodoItems.size());
		editor.commit();
		mAdapter.notifyDataSetChanged();
	}
	
	public void shareList(View view) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		String list =  "";
		for(int i=0;i<mTodoItems.size();i++)
		{
		         list += mTodoItems.get(i).getTask() + ",  ";
		}
		intent.setType("text/plain");
		intent.putExtra(android.content.Intent.EXTRA_TEXT, list);
		startActivity(intent);
	}
	
	public void addToPrev(View view){
		Intent returnIntent = new Intent();
		int m = mTodoItems.size();
		returnIntent.putExtra("result",String.valueOf(m));
		setResult(RESULT_OK,returnIntent);     
		finish();
	}
}
