package mk.ukim.finki.jmm.tobuylist;

import mk.ukim.finki.jmm.tobuylist.download.DownloadActivity;
import mk.ukim.finki.jmm.tobuylist.maps.MapsActivity;
import mk.ukim.finki.jmm.tobuylist.ratings.RatingsOverviewActivity;
//import mk.ukim.finki.jmm.tobuylist.wifi.WiFiScanActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	public void openToBuyList(View view) {
		Intent intent = new Intent(this, ToBuyListActivity.class);
		this.startActivity(intent);
	}
	
	public void loadCatalog(View view) {
		Intent intent = new Intent(this, CatalogLoaderActivity.class);
		this.startActivity(intent);
	}
	public void rateStore(View view) {
		Intent intent = new Intent(this, RatingsOverviewActivity.class);
		this.startActivity(intent);
	}
	
	public void downloadCatalog(View view){
		Intent intent = new Intent(this, DownloadActivity.class);
		this.startActivity(intent);
	}
	
	public void viewMap(View view){
		Intent intent = new Intent(this, MapsActivity.class);
		this.startActivity(intent);
	}
	
//	public void scanWifi(View view){
//		Intent intent = new Intent(this, WiFiScanActivity.class);
//		this.startActivity(intent);
//	}

}
