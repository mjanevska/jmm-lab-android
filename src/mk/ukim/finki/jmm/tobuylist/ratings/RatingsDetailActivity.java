package mk.ukim.finki.jmm.tobuylist.ratings;

import mk.ukim.finki.jmm.tobuylist.R;
import mk.ukim.finki.jmm.tobuylist.ratings.contentprovider.RatingsContentProvider;
import mk.ukim.finki.jmm.tobuylist.ratings.database.RatingsTable;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/*
 * TodoDetailActivity allows to enter a new todo item 
 * or to change an existing
 */
public class RatingsDetailActivity extends Activity {
	private Spinner mCategory;
	private EditText mTitleText;
	private EditText mBodyText;

	private Uri todoUri;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.ratings_edit);

		mCategory = (Spinner) findViewById(R.id.category);
		mTitleText = (EditText) findViewById(R.id.rating_edit_summary);
		mBodyText = (EditText) findViewById(R.id.rating_edit_description);
		Button confirmButton = (Button) findViewById(R.id.rating_edit_button);

		Bundle extras = getIntent().getExtras();

		// check from the saved Instance
		todoUri = (bundle == null) ? null : (Uri) bundle
				.getParcelable(RatingsContentProvider.CONTENT_ITEM_TYPE);

		// Or passed from the other activity
		if (extras != null) {
			todoUri = extras
					.getParcelable(RatingsContentProvider.CONTENT_ITEM_TYPE);

			fillData(todoUri);
		}

		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (TextUtils.isEmpty(mTitleText.getText().toString())) {
					makeToast();
				} else {
					setResult(RESULT_OK);
					finish();
				}
			}

		});
	}

	private void fillData(Uri uri) {
		String[] projection = { RatingsTable.COLUMN_SUMMARY,
				RatingsTable.COLUMN_DESCRIPTION, RatingsTable.COLUMN_CATEGORY };
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				null);
		if (cursor != null) {
			cursor.moveToFirst();
			String category = cursor.getString(cursor
					.getColumnIndexOrThrow(RatingsTable.COLUMN_CATEGORY));

			for (int i = 0; i < mCategory.getCount(); i++) {

				String s = (String) mCategory.getItemAtPosition(i);
				if (s.equalsIgnoreCase(category)) {
					mCategory.setSelection(i);
				}
			}

			mTitleText.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(RatingsTable.COLUMN_SUMMARY)));
			mBodyText.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(RatingsTable.COLUMN_DESCRIPTION)));

			// always close the cursor
			cursor.close();
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putParcelable(RatingsContentProvider.CONTENT_ITEM_TYPE, todoUri);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	private void saveState() {
		String category = (String) mCategory.getSelectedItem();
		String summary = mTitleText.getText().toString();
		String description = mBodyText.getText().toString();

		// only save if either summary or description
		// is available

		if (description.length() == 0 && summary.length() == 0) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(RatingsTable.COLUMN_CATEGORY, category);
		values.put(RatingsTable.COLUMN_SUMMARY, summary);
		values.put(RatingsTable.COLUMN_DESCRIPTION, description);

		if (todoUri == null) {
			// New todo
			todoUri = getContentResolver().insert(
					RatingsContentProvider.CONTENT_URI, values);
		} else {
			// Update todo
			getContentResolver().update(todoUri, values, null, null);
		}
	}

	private void makeToast() {
		Toast.makeText(RatingsDetailActivity.this, "Please maintain a summary",
				Toast.LENGTH_LONG).show();
	}
}