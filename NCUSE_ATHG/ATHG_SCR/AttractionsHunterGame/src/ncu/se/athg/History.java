package ncu.se.athg;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
//import ncu.se.athg.CheckDistance.SearchStatus;

public class History extends FragmentActivity{
	
	LocationManager lm;
	private String texLoca,texIdUsers,history,imgHis,urlUsers,idStatus,tema,urlStatu,urlHistor,id;
	double latitude,longitude,lat_desc, lon_desc;
	final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	final ArrayList<HashMap<String, String>> MyArrList1 = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> users;
	final ArrayList<HashMap<String, String>> MyArrList2 = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> statu;
	final ArrayList<HashMap<String, String>> MyArrList3 = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> check;
	TextView scorEeagle,scorLandgo ,textHistory;
	ImageView win,img;
	private ProgressDialog pDialog;
	int landgon, eagle,sumExp,arrogate,authority,sumEagle,sumLandgon,score,expnum;
	static double total ;
	JSONParser jsonParser = new JSONParser();
	 AlertDialog alertDialog ;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		
		
		
	//setupActionBar();
		 lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

	        Criteria criteria = new Criteria();
	        criteria.setAccuracy(Criteria.ACCURACY_FINE);
	        criteria.setAltitudeRequired(false);
	        criteria.setBearingRequired(false);
		
		Intent myInteger = getIntent();
		texLoca = myInteger.getStringExtra("idLoca");
		texIdUsers = myInteger.getStringExtra("idUsers");
		new SearchHistory().execute();
		onPageStarted();
		scorLandgo = (TextView)findViewById(R.id.scorLandgo);
        
        scorEeagle = (TextView)findViewById(R.id.scorEeagle);
        textHistory = (TextView)findViewById(R.id.textHistory);
		//scorEeagle.setText("ÊË¾Ñ¹¸ÃÑ° Eagle"+eagle+"¤Ðá¹¹");*/
        img = (ImageView) findViewById(R.id.image);
        win = (ImageView) findViewById(R.id.win);
        
       
        
       // alert();
		
		
	}
	public void alert(){
		alertDialog= new AlertDialog.Builder(this).create();
		 alertDialog.setTitle("Title");
	        alertDialog.setMessage("Message");
	        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int which) {
	              // TODO Add your code for the button here.
	           }
	        });
	        alertDialog.show();
		
	        
		Log.v("FFFFF", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
	}
	
	public void onPageStarted() {            	            
        //Log.e("Load",url);
		 pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Loading..");
        pDialog.setTitle("Attractions Hunter Game");
        pDialog.setCancelable(false);            
        pDialog.show();
    }       

	public void onResume() {
        super.onResume();
        setup();
    }
    
    public void onStart() {
        super.onStart();
        boolean gpsEnabled, networkEnabled;
        gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!gpsEnabled) {
            networkEnabled = 
                    lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(!networkEnabled) {
                Intent intent = 
                        new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
    }
    
    public void onStop() {
        super.onStop();
        lm.removeUpdates(listener);
    }
 
    public void setup() {
        lm.removeUpdates(listener);
        String latit = "Unknown";
        String longit = "Unknown";
        
        Location networkLocation = requestUpdatesFromProvider(
                LocationManager.NETWORK_PROVIDER, "Network not supported");
        if(networkLocation != null) {
            latit = String.format("%.6f", networkLocation.getLatitude());
            longit = String.format("%.6f", networkLocation.getLongitude());
        }
        
        Location gpsLocation = requestUpdatesFromProvider(
                LocationManager.GPS_PROVIDER, "GPS not supported");
        
        if(gpsLocation != null) {
            latit = String.format("%.6f", gpsLocation.getLatitude());
            longit = String.format("%.6f", gpsLocation.getLongitude());
        }
        
        Log.i("latitude", latit);
        Log.i("longitude", longit);
        latitude = Double.parseDouble(latit);
        longitude = Double.parseDouble(longit);
        
        
    }
    
    public Location requestUpdatesFromProvider(final String provider
            , String error) {
        Location location = null;
        if (lm.isProviderEnabled(provider)) {
            lm.requestLocationUpdates(provider, 1000, 10, listener);
            location = lm.getLastKnownLocation(provider);
        } else {
            //Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
        return location;
    }
    
    public final LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location location) {
        	latitude= Double.parseDouble(String.format("%.6f", location.getLatitude()));
        	longitude = Double.parseDouble(String.format("%.6f"
                    ,location.getLongitude()));
            
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider
                , int status, Bundle extras) { }
    };
	public class SearchHistory extends AsyncTask<String, Void, Void> {
    	

        @Override
        protected Void doInBackground(String... arg) {
            // TODO Auto-generated method stub

    		
        	
    		// Paste Parameters
    		List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idUsers", texLoca));
           Log.i("aaaaaaaa", texLoca);
            
            //JSONObject json = jsonParser.makeHttpRequest(urlUsers,"GET", params);
            
            try {
            	String urlHistory = "http://110.164.78.161/~b531610005/jsonHistory.php?idLoca="+texLoca;
            	
            	            	
    			Log.i("ssss", urlHistory);
    			JSONArray data = new JSONArray(getJSONUrl(urlHistory,params));
    			for(int i = 0; i < data.length(); i++){
                    JSONObject c = data.getJSONObject(i);
                    Log.i("aa", "a");
    			
    			
                   // JSONObject c = json.getJSONObject(idUsers);
                    map = new HashMap<String, String>();
                    map.put("idUsers", c.getString("idLoca"));
                    map.put("nameLoca", c.getString("nameLoca"));
                    map.put("latitude", c.getString("latitude"));
                    map.put("longitude", c.getString("longitude"));
                    map.put("historyLoca", c.getString("historyLoca"));
                    map.put("imageLoca", c.getString("imageLoca"));
                    map.put("score", c.getString("score"));
                    map.put("eagle", c.getString("eagle"));
                    map.put("landgon", c.getString("landgon"));
                    Log.i("dfsdfsdfsdf", c.getString("nameLoca"));
                   
        			MyArrList.add(map);
        			
        			landgon = Integer.parseInt(c.getString("landgon"));
        			eagle = Integer.parseInt(c.getString("eagle"));
        			history= c.getString("historyLoca");
        			imgHis= c.getString("imageLoca");
        			 
        			
        			
        			
        			//loadImage(i1);
        			//exp.setText("Exp\t"+sumExp);
        			
        			Log.i("AAAAA", "AA" + eagle);
        			
        			//TextView scorEeagle = (TextView)findViewById(R.id.scorEeagle);
        			runOnUiThread(new Runnable()
        			{
        				@Override
        				public void run()
        				{
        					scorEeagle.setText("ÊË¾Ñ¹¸ÃÑ° Eagle\t"+eagle+"\t¤Ðá¹¹");
        					scorLandgo.setText("¨Ñ¡ÃÇÃÃ´Ôì landgon\t"+landgon+"¤Ðá¹¹");
        					textHistory.setText(history);
        					if(landgon>eagle)
                			{
                            	Log.i("t", "f");
                            	
                				win.setImageResource(R.drawable.landgon);
                			}
                			else{
                				Log.i("l", "w");
                				win.setImageResource(R.drawable.eagle);
                			}
        					img.setTag(imgHis);


        					new DownloadImagesTask().execute(img);
        					
        					//loadImage(i1);
        					//loadImage(i1);
                			
        				}        				
        			});

        			
        			//ImageView win = (ImageView) findViewById(R.id.win);
        			
                    
    			}
    			

    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

			
            
            
            pDialog.cancel();
    		return null;
        }

    }
	
	public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

	    ImageView imageView = null;

	    @Override
	    protected Bitmap doInBackground(ImageView... imageViews) {
	        this.imageView = imageViews[0];
	        return download_Image((String)imageView.getTag());
	    }

	    @Override
	    protected void onPostExecute(Bitmap result) {
	        imageView.setImageBitmap(result);
	    }

	    private Bitmap download_Image(String url) {

	        Bitmap bmp =null;
	        try{
	            URL ulrn = new URL(url);
	            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
	            InputStream is = con.getInputStream();
	            bmp = BitmapFactory.decodeStream(is);
	            if (null != bmp)
	                return bmp;

	            }catch(Exception e){}
	        
	        return bmp;
	    }
	}

    
    // Get JSON code from URL
	public String getJSONUrl(String url,List<NameValuePair> params) {
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
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
		Log.e("aa3333333", str.toString());
		return str.toString();
	}
	
	public class SearchUsers extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub

			// Paste Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idUsers", texIdUsers));
			Log.i("aaaaaaaa", texIdUsers);

			// JSONObject json = jsonParser.makeHttpRequest(urlUsers,"GET",
			// params);

			try {
				urlUsers = "http://110.164.78.161/~b531610005/jsonUsers.php?idUsers="
						+ texIdUsers;

				Log.i("ssss", urlUsers);
				JSONArray data = new JSONArray(getJSONUrl(urlUsers, params));
				for (int i = 0; i < data.length(); i++) {
					JSONObject c = data.getJSONObject(i);
					Log.i("aa", "a");

					// JSONObject c = json.getJSONObject(idUsers);
					users = new HashMap<String, String>();
					users.put("idUsers", c.getString("idUsers"));
					users.put("tema", c.getString("tema"));
					users.put("tema", c.getString("sumExp"));
					users.put("tema", c.getString("idStatus"));
					Log.i("aa", c.getString("sumExp"));
					sumExp = Integer.parseInt(c.getString("sumExp"));
					arrogate = Integer.parseInt(c.getString("arrogate"));
					idStatus = c.getString("idStatus");
					tema = c.getString("tema");

					MyArrList1.add(users);
					Log.i("aa3333333", idStatus);
					// Log.i("tema", tema);
					new SearchStatus().execute();

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// exp.setText("Exp\t"+sumExp);
			return null;
		}
	}
	public class SearchStatus extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub

			// Paste Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idStatus", idStatus));
			Log.i("aaaaaaaa", idStatus);

			// JSONObject json = jsonParser.makeHttpRequest(urlUsers,"GET",
			// params);

			try {
				urlStatu = "http://110.164.78.161/~b531610005/jsonStatus.php?idStatus="
						+ idStatus;

				Log.i("ssss", idStatus);
				JSONArray data = new JSONArray(getJSONUrl(urlStatu, params));
				for (int i = 0; i < data.length(); i++) {
					JSONObject c = data.getJSONObject(i);
					Log.i("aa", "a");

					// JSONObject c = json.getJSONObject(idUsers);
					statu = new HashMap<String, String>();
					statu.put("idStatus", c.getString("idStatus"));
					statu.put("nameStatus", c.getString("nameStatus"));
					statu.put("exp", c.getString("exp"));
					statu.put("authority", c.getString("authority"));
					// Log.i("aa", c.getString("sumExp"));
					// sumExp = c.getString("sumExp");

					authority = Integer.parseInt(c.getString("authority"));
					Log.e("aa", "SS" + authority);

					// tema=c.getString("tema");
					Log.e("aa", "a" + authority);
					// Log.i("tema", tema);
					MyArrList2.add(statu);
					// Log.i("tema", tema);
					new CheckHistory().execute();
					// exp.setText("Exp\t"+sumExp);
				}
				;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// exp.setText("Exp\t"+sumExp);
			return null;
		}
	}
	
	public class CheckHistory extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub

			// Paste Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idLoca", texLoca));
			Log.i("aaaaaaaa", texLoca);

			// JSONObject json = jsonParser.makeHttpRequest(urlUsers,"GET",
			// params);

			try {
				String urlUsers = "http://110.164.78.161/~b531610005/jsonHistory.php?idLoca="
						+ texLoca;

				Log.i("ssss", urlUsers);
				JSONArray data = new JSONArray(getJSONUrl(urlUsers, params));
				for (int i = 0; i < data.length(); i++) {
					JSONObject c = data.getJSONObject(i);
					Log.i("aa", "a");

					// JSONObject c = json.getJSONObject(idUsers);
					check = new HashMap<String, String>();
					check.put("idLoca", c.getString("idLoca"));
					check.put("nameLoca", c.getString("nameLoca"));
					check.put("latitude", c.getString("latitude"));
					check.put("longitude", c.getString("longitude"));
					check.put("historyLoca", c.getString("historyLoca"));
					check.put("imageLoca", c.getString("imageLoca"));
					check.put("score", c.getString("score"));
					check.put("eagle", c.getString("eagle"));
					check.put("landgon", c.getString("landgon"));
					Log.i("dfsdfsdfsdf", c.getString("nameLoca"));

					MyArrList3.add(check);
					sumEagle = Integer.parseInt(c.getString("eagle"));
					sumLandgon = Integer.parseInt(c.getString("landgon"));
					lat_desc = Double.parseDouble(c.getString("latitude"));
					lon_desc = Double.parseDouble(c.getString("longitude"));
					score = Integer.parseInt(c.getString("score"));
					Log.i("score", String.valueOf(score));

					kilometers(latitude, longitude, lat_desc, lon_desc);
					Log.i("ff", tema);
					if (5 >= total) {
						Log.w("GGG", "FFF");
						
						Update();

					}
					else {
						
						  
					}
					Log.i("AAAAAAAAAAA", "AAAAA" + sumEagle);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
	}


	public static double kilometers(double lat1, double long1, double lat2,
			double long2) {
		Log.i("OOOOOOOO", "AA" + lat2 + "," + long2);
		double degToRad = Math.PI / 180.0;
		double phi1 = lat1 * degToRad;
		double phi2 = lat2 * degToRad;
		double lam1 = long1 * degToRad;
		double lam2 = long2 * degToRad;
		total = (6371.01 * Math.acos(Math.sin(phi1) * Math.sin(phi2)
				+ Math.cos(phi1) * Math.cos(phi2) * Math.cos(lam2 - lam1)) * 1000);

		Log.i("ss", "aaaaaaaaaaaaaaaaaaaaaa" + total);

		return total;
	}
	
	public void Update() {
		Log.i("Update", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		new UpdateHistory().execute();
		new SearchExps().execute();
		
		
	}
	
	public class UpdateUsers extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub



			Log.i("arrogate", String.valueOf(arrogate));
			
			arrogate = arrogate + 1;

			
			
			// Paste Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idUsers", texIdUsers));
			params.add(new BasicNameValuePair("sumExp", String.valueOf(sumExp)));
			params.add(new BasicNameValuePair("arrogate", String.valueOf(arrogate)));
			params.add(new BasicNameValuePair("idStatus", String.valueOf(idStatus)));

			

			// JSONObject json = jsonParser.makeHttpRequest(urlUsers,"GET",
			// params);

			urlUsers = "http://110.164.78.161/~b531610005/updateUsers.php";

			Log.i("ssss", urlUsers);

			jsonParser.makeHttpRequest(urlUsers, "GET", params);

			return null;
		}

	}
	public class UpdateHistory extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub
			Log.e("tema", tema);
			

			if (tema.equals("Eagle")) {
				sumEagle = sumEagle + authority;

			} else {
				sumLandgon = sumLandgon + authority;
				Log.e("sumLandgon", ""+sumLandgon);
			}
			Log.e("sumExp", "" + sumExp);
			Log.e("arrogate", "" + arrogate);
			
			
			// Paste Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idLoca", texLoca));
			params.add(new BasicNameValuePair("eagle", String.valueOf(sumEagle)));
			params.add(new BasicNameValuePair("landgon", String.valueOf(sumLandgon)));

			Log.i("idLoca", texLoca);
			Log.e("sumEagle", String.valueOf(sumEagle));
			Log.i("landgon", String.valueOf(sumLandgon));

			// JSONObject json = jsonParser.makeHttpRequest(urlUsers,"GET",
			// params);

			urlHistor = "http://110.164.78.161/~b531610005/updateHistory.php";

			Log.i("urlHistor", urlHistor);

			jsonParser.makeHttpRequest(urlHistor, "GET", params);

			return null;
		}

	}
	
	class SearchExps extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			Log.i("score", String.valueOf(score));
			sumExp = sumExp + score;
			Log.e("sumExp", String.valueOf(sumExp));
			

			String url = "http://110.164.78.161/~b531610005/status.php";
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
					map.put("idStatus", c.getString("idStatus"));
					map.put("nameStatus", c.getString("nameStatus"));
					map.put("exp", c.getString("exp"));
					map.put("authority", c.getString("authority"));
					
					id = c.getString("idStatus");
					expnum = Integer.parseInt(c.getString("exp"));

					MyArrList.add(map);
					Log.e("sumExp", ""+sumExp);
					Log.v("expnum", ""+expnum);
					if(sumExp<=expnum){
						idStatus=id;
						Log.i("idStatus", idStatus);
						break;
					}
					else if(sumExp>=350){
						idStatus=id;

					}

					
					Log.v("ss", id+expnum);
					
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			new UpdateUsers().execute();
		}
	}

	
	public void checkDistance(View v) {
		/*
		Intent checkIntent = new Intent(this, CheckDistance.class);
		checkIntent.putExtra("idLoca", texLoca);
		checkIntent.putExtra("idUsers", texIdUsers);
		startActivity(checkIntent);
		*/
		
		/*CheckDistance  check = new CheckDistance ();
		check.doFirst(texLoca,texIdUsers,latitude,longitude);
		finish();*/
		
		Log.i("GGGGG", "GGG1111111111111111111111111111111111111111111111111111111");
		new SearchUsers().execute();
		Intent it = new Intent(History.this, History.class);
		it.putExtra("idUsers", texIdUsers);
		it.putExtra("idLoca", texLoca);
		startActivity(it);
		
		
		
	}
	

	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	Intent it = new Intent(History.this, Map.class);
			startActivity(it);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
