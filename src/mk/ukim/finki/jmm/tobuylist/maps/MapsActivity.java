package mk.ukim.finki.jmm.tobuylist.maps;

import mk.ukim.finki.jmm.tobuylist.R;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends Activity implements LocationListener {
	static final LatLng SKOPJE = new LatLng(41.996, 21.424);
	// static final LatLng KIEL = new LatLng(53.551, 9.993);
	private GoogleMap map;
	String provider;
	LocationManager locationManager;
	Location l;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);

		String svcName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(svcName);
		// String provider = LocationManager.GPS_PROVIDER;
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(true);
		provider = locationManager.getBestProvider(criteria, true);
		l = locationManager.getLastKnownLocation(provider);
		// Get the current location's latitude & longitude
		// double la = l.getLatitude();
		// double lo = l.getLongitude();
		// double la = 42.003858;
		// double lo = 21.4089955;

		// Initialize the location fields
		if (l != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(l);
		} else {
			Toast.makeText(MapsActivity.this, "Current location unavailable",
					Toast.LENGTH_LONG).show();
		}

		// Display the current location in the UI
		// map.addMarker(new MarkerOptions().position(new LatLng(la,
		// lo)).title("You are here"));
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		// map.addMarker(new MarkerOptions().position(new LatLng(la,
		// lo)).title("You are here"));
		//map.addMarker(new MarkerOptions().position(SKOPJE).title("Skopje"));

		// Move the camera instantly to hamburg with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(SKOPJE, 12));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void getStores(View view) {

		map.addMarker(new MarkerOptions()
				.position(new LatLng(41.987814,21.453396))
				.title("Jane Sandanski")
				.snippet("Discount"));
		
		map.addMarker(new MarkerOptions()
		.position(new LatLng(41.9388747,21.5144276))
		.title("Dracevo")
		.snippet("Discount"));
		
		map.addMarker(new MarkerOptions()
		.position(new LatLng(42.0004746,21.3984653))
		.title("Partizanski Odredi 18a")
		.snippet("Discount"));
		
		map.addMarker(new MarkerOptions()
		.position(new LatLng(41.989581,21.412143))
		.title("Pitu Guli bb")
		.snippet("Discount"));
		
		map.addMarker(new MarkerOptions()
		.position(new LatLng(41.9970362,21.4836735))
		.title("Pero nakov")
		.snippet("Super Market"));
		
		map.addMarker(new MarkerOptions()
		.position(new LatLng(42.0030843,21.4074836))
		.title("Shekspirova")
		.snippet("Discount"));

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double la = l.getLatitude();
		double lo = l.getLongitude();
		map.addMarker(new MarkerOptions().position(new LatLng(la, lo)).title(
				"You are here"));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}

}