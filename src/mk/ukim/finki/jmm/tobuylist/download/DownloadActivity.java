package mk.ukim.finki.jmm.tobuylist.download;

import mk.ukim.finki.jmm.tobuylist.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadActivity extends Activity {

	  private TextView textView;
	  private BroadcastReceiver receiver = new BroadcastReceiver() {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	      Bundle bundle = intent.getExtras();
	      if (bundle != null) {
	        String string = bundle.getString(DownloadService.FILEPATH);
	        int resultCode = bundle.getInt(DownloadService.RESULT);
	        if (resultCode == RESULT_OK) {
	          Toast.makeText(DownloadActivity.this,
	              "Download complete. Download URI: " + string,
	              Toast.LENGTH_LONG).show();
	          textView.setText("Download done");
	        } else {
	          Toast.makeText(DownloadActivity.this, "Download failed",
	              Toast.LENGTH_LONG).show();
	          textView.setText("Download failed");
	        }
	      }
	    }
	  };

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.download);
	    Toast.makeText(DownloadActivity.this, "onCreate called",
	              Toast.LENGTH_LONG).show();
	    textView = (TextView) findViewById(R.id.status);
	    

	  }

	  @Override
	  protected void onResume() {
	    super.onResume();
	    Toast.makeText(DownloadActivity.this, "onResume called",
	              Toast.LENGTH_LONG).show();
	    registerReceiver(receiver, new IntentFilter(DownloadService.NOTIFICATION));
	  }
	  @Override
	  protected void onPause() {
	    super.onPause();
	    Toast.makeText(DownloadActivity.this, "onPause called",
	              Toast.LENGTH_LONG).show();
	    unregisterReceiver(receiver);
	  }

	  public void download(View view) {

	    Intent intent = new Intent(this, DownloadService.class);
	    // add info for the service which file to download and where to store
	    intent.putExtra(DownloadService.FILENAME, "MAK_products.html");
	    intent.putExtra(DownloadService.URL,
	        "http://bit.ly/QJHVpw");
	    startService(intent);
	    textView.setText("Service started");
	  }

}
