package com.example.mapexample;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener{
	
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	private LocationClient mLocationClient;
	LocationRequest mLocationRequest;
	
	private GoogleMap mMap;
	
	private double lat = 0;
	private double lng = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mLocationClient = new LocationClient(this, this, this);
		
		//for updates
		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(5000);
	

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS) {
			Toast.makeText(this, "Install Google Play Services.",
					Toast.LENGTH_SHORT).show();
			
			finish();
		}else{
			Toast.makeText(this, "Services present.",
					Toast.LENGTH_SHORT).show();
			mLocationClient.connect();
			
		}
		
	}
	

	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
		Location mCurrentLocation = mLocationClient.getLastLocation();
		if(mCurrentLocation != null){
			lat = mCurrentLocation.getLatitude();
			lng = mCurrentLocation.getLongitude();
		}
		
		
		mMap = ((com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();

		mMap.addMarker(new MarkerOptions()

		// Set the Marker's position
		.position(new LatLng(lat, lng))

		// Set the title of the Marker's information window
		.title("You are here."));

		mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
				lat, lng)));
		mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) ); 
		
	}
	


	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {
		if(location != null){
			lat = location.getLatitude();
			lng = location.getLongitude();
		}
		
		
		mMap = ((com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		
		mMap.clear();
		mMap.addMarker(new MarkerOptions()

		// Set the Marker's position
		.position(new LatLng(lat, lng))

		// Set the title of the Marker's information window
		.title("You are here."));

		mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
				lat, lng)));
		 
		
	}

	

	

}









		
	
		

