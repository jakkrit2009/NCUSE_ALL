package ncu.se.athg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*import ncu.se.athg.CheckDistance.SearchStatus;
 import ncu.se.athg.CheckDistance.SearchStatus.SearchHistory;
 import ncu.se.athg.Selection.SearchUsers;
 */
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends FragmentActivity implements OnMapLongClickListener,
		OnInfoWindowClickListener, OnMapClickListener {

	final int RQS_GooglePlayServices = 1;
	private GoogleMap myMap;
	private LocationManager locationManager;
	private String nameLoca, latitude, longitude, idUsers, urlUsers, idStatus,
			urlStatu, nameStatus;
	private String idLoca, sumExp, team;
	private ProgressDialog pDialog;
	Double lat;
	Double lng;
	private GraphUser user;
	int[] markerArray;
	private TextView exp, statuName;

	final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	final ArrayList<HashMap<String, String>> MyArrList1 = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> users;
	final ArrayList<HashMap<String, String>> MyArrList2 = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> statu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		markerArray = new int[100];
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		// แสดงค่า EXP และยศ
		exp = (TextView) findViewById(R.id.exp);
		statuName = (TextView) findViewById(R.id.nameStatu);

		FragmentManager myFragmentManager = getSupportFragmentManager();
		SupportMapFragment mySupportMapFragment = (SupportMapFragment) myFragmentManager
				.findFragmentById(R.id.mapCanvas);
		myMap = mySupportMapFragment.getMap();
		myMap.setMyLocationEnabled(true);
		myMap.setOnMapClickListener(this);
		myMap.setOnMapLongClickListener(this);
		myMap.setOnInfoWindowClickListener(this);

		// ตั้งค่ารูปแบบการแสดงแผนที่ ซึ่งมีให้ใช้ 5 รูปแบบ
		// myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		// myMap.setMapType(GoogleMap.MAP_TYPE_NONE);

		// ซูมเข้าไปในMAP ที่ละติจูด ลองจิจูด ที่กำหนด
		myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				18.772415, 98.942642), 10));
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state,
					Exception exception) {

				if (state == SessionState.OPENED) {
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback() {

								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									Log.i("w", "r");
									processUserInfo(user);

								}
							});
				}
			}
		});

	}// end onCreate

	// แสดงDialogบอกว่าLoading..
	public void onPageStarted() {
		pDialog = new ProgressDialog(this);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage("Loading..");
		pDialog.setTitle("Attractions Hunter Game");
		pDialog.setCancelable(false);
		pDialog.show();
	}

	private void processUserInfo(GraphUser user) {
		if (user != null) {
			this.user = user;

			idUsers = user.getId();
			TextView tv = (TextView) findViewById(R.id.userName);
			tv.setText(user.getName());

			ProfilePictureView profilePictureView = (ProfilePictureView) findViewById(R.id.profilePic);
			profilePictureView.setProfileId(user.getId());

		}
		onPageStarted();
		showMarker();
		new SearchUsers().execute();
	}

	@Override
	protected void onResume() {
		super.onResume();

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		if (resultCode == ConnectionResult.SUCCESS) {
			/*
			 * Toast.makeText(getApplicationContext(),
			 * "GooglePlayServices is Available", Toast.LENGTH_LONG) .show();
			 */
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		}
	}

	// ถ้ากดMarkerแล้วจะไปหน้าประวัติของสถานที่
	@Override
	public void onInfoWindowClick(Marker marker) {

		String markerId = String.valueOf(Integer.parseInt(marker.getId()
				.replace("m", "")));
		int id = markerArray[Integer.parseInt(markerId)];
		String locationId = String.valueOf(id);

		marker.hideInfoWindow();
		Intent it = new Intent(Map.this, History.class);
		it.putExtra("idLoca", locationId);
		it.putExtra("idUsers", idUsers);
		startActivity(it);

	}

	public void addMarker() {

	}

	public void showMarker() {

		new SerchLocation().execute();
	}

	// แสดงสถานที่ตามที่มีในฐานข้อมูล
	class SerchLocation extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			String url = "http://110.164.78.161/~b531610005/jsonMap.php";
			return getJSONUrl(url);
		}

		public String getJSONUrl(String url) {
			StringBuilder str = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						str.append(line);
					}
				} else {
					Log.e("Log", "Failed to download file..");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str.toString();
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				JSONArray data = new JSONArray(result);

				for (int i = 0; i < data.length(); i++) {
					JSONObject c = data.getJSONObject(i);

					map = new HashMap<String, String>();
					map.put("idLoca", c.getString("idLoca"));
					map.put("nameLoca", c.getString("nameLoca"));
					map.put("latitude", c.getString("latitude"));
					map.put("longitude", c.getString("longitude"));

					MyArrList.add(map);

					idLoca = map.get("idLoca");
					nameLoca = map.get("nameLoca");
					latitude = map.get("latitude");
					longitude = map.get("longitude");
					lat = Double.parseDouble(latitude);
					lng = Double.parseDouble(longitude);
					try {
						// นำละติจูด ลองจิจูด เข้าไปใส่ในmap
						Marker marker = myMap.addMarker(new MarkerOptions()
								.position(new LatLng(lat, lng))
								.title(nameLoca)
								.snippet("ดูความเป็นมาสถานที่")
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.marker)));
						markerArray[Integer.parseInt(marker.getId().replace(
								"m", ""))] = Integer.parseInt(idLoca);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void onMapClick(LatLng point) {

		myMap.animateCamera(CameraUpdateFactory.newLatLng(point));
	}

	@Override
	public void onMapLongClick(LatLng point) {
		myMap.animateCamera(CameraUpdateFactory.newLatLng(point));

	}

	// ถ้ากดปุ้มกลับจะออกจะระบบ
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Handle the back button
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setMessage("คุณต้องการออกจากโปรแกรม ?")
					.setCancelable(false)
					.setPositiveButton("ใช่",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Intent intent = new Intent(
											Intent.ACTION_MAIN);
									intent.addCategory(Intent.CATEGORY_HOME);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(intent);
								}
							}).setNegativeButton("ไม่ใช่", null).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	// กดไปดูRanking
	public void openRank(View v) {

		Intent rankIntent = new Intent(this, Rank.class);
		rankIntent.putExtra("team", team);
		startActivity(rankIntent);
	}

	// ตรวจสอบผู้ใช้ว่ามียศอะไร EXPเท่าไหร่
	public class SearchUsers extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub

			// Paste Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idUsers", idUsers));
			try {
				urlUsers = "http://110.164.78.161/~b531610005/jsonUsers.php?idUsers="
						+ idUsers;

				JSONArray data = new JSONArray(getJSONUrl(urlUsers, params));
				for (int i = 0; i < data.length(); i++) {
					JSONObject c = data.getJSONObject(i);

					// JSONObject c = json.getJSONObject(idUsers);
					users = new HashMap<String, String>();
					users.put("idUsers", c.getString("idUsers"));
					users.put("tema", c.getString("tema"));
					users.put("sumExp", c.getString("sumExp"));
					users.put("idStatus", c.getString("idStatus"));
					sumExp = c.getString("sumExp");
					idStatus = c.getString("idStatus");

					team = c.getString("tema");
					MyArrList1.add(users);
					new SearchStatus().execute();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							exp.setText("Exp\t" + sumExp);

						}
					});

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		// นำไดียศของผู้ใช้เข้ามาตรวจสอบ ว่ายศอะไร
		public class SearchStatus extends AsyncTask<String, Void, Void> {

			@Override
			protected Void doInBackground(String... arg) {
				// TODO Auto-generated method stub

				// Paste Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("idStatus", idStatus));

				try {
					urlStatu = "http://110.164.78.161/~b531610005/jsonStatus.php?idStatus="
							+ idStatus;

					JSONArray data = new JSONArray(getJSONUrl(urlStatu, params));
					for (int i = 0; i < data.length(); i++) {
						JSONObject c = data.getJSONObject(i);

						statu = new HashMap<String, String>();
						statu.put("idStatus", c.getString("idStatus"));
						statu.put("nameStatus", c.getString("nameStatus"));
						statu.put("exp", c.getString("exp"));
						statu.put("authority", c.getString("authority"));

						nameStatus = c.getString("nameStatus");
						MyArrList2.add(statu);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								statuName.setText(nameStatus);

							}
						});
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				pDialog.cancel();
				return null;
			}

		}
	}

	// Get JSON code from URL
	public String getJSONUrl(String url, List<NameValuePair> params) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Download OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download result..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

}