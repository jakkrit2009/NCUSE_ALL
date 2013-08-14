/*package ncu.se.athg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.os.AsyncTask;
import android.util.Log;

public class CheckDistance {
	double latitude, longitude, lat_desc, lon_desc;
	final ArrayList<HashMap<String, String>> MyArrList1 = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> users;
	final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> statu;
	final ArrayList<HashMap<String, String>> MyArrList2 = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	private String urlUsers, idUsers, urlStatu, urlHistor, idLoca, idStatus,
			tema, eagle, landgon,id;
	private static int authority, sumEagle, sumLandgon, sumExp, score,
			arrogate, exp,expnum;
	JSONParser jsonParser = new JSONParser();
	static double total ;

	public void doFirst(String texLoca, String texIdUsers, double latit,
			double longit) {
		latitude = latit;
		longitude = longit;
		idLoca = texLoca;
		idUsers = texIdUsers;
		Log.e("AAA", "aaaa" + texLoca + texIdUsers + latit + longit);
		
		new SearchUsers().execute();
	}

	}

	

		

	

	
	
	
	public void dialog(){
		
		//new AlertDialog.Builder(History.class).setTitle("อยู่ห่างจากสถานที่เกินไป").setMessage("กรุณาอยู่ภายในระยะ 5 เมตร").setNeutralButton("Close", null).show();
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

}*/