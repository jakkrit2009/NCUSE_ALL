package ncu.se.athg;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

public class Selection extends Fragment {

	private String tema, idUsers, nameUsers;
	private String urlInsert = "http://110.164.78.161/~b531610005/response.php";
	private String urlSearch = "http://110.164.78.161/~b531610005/jsonUsers.php";
	private HttpClient httpclient = new DefaultHttpClient();
	private GraphUser user;
	private HttpResponse response;
	private ProgressDialog pDialog;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	JSONParser jsonParser = new JSONParser();
	// private JSONParser jsonParser = new JSONParser();
	final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> users;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.selection, container, false);

		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);

		}

		// แสดงปุ่มกด
		Button buttonLandgon = (Button) view.findViewById(R.id.buttonLandgon);
		Button buttonEagle = (Button) view.findViewById(R.id.buttonEagle);

		// เมื่อกดbuttonLandgonจะทำการบันทึกข้อมูลผู้ใช้แล้วเข้าไปหน้าMAP
		buttonLandgon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				tema = "Landgon";
				new CreateNewUsers().execute();
				Intent intent1 = new Intent(getActivity(), Map.class);
				startActivity(intent1);

			}
		});

		// เมื่อกดbuttonEagleจะทำการบันทึกข้อมูลผู้ใช้แล้วเข้าไปหน้าMAP
		buttonEagle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tema = "Eagle";
				new CreateNewUsers().execute();
				Intent intent1 = new Intent(getActivity(), Map.class);
				startActivity(intent1);

			}
		});

		return view;
	}

	// แสดงDialogบอกว่าLoading..
	public void onPageStarted() {

		pDialog = new ProgressDialog(getActivity());
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setTitle("Attractions Hunter Game");
		pDialog.setMessage("Loading..");

		pDialog.setCancelable(false);
		pDialog.show();
	}

	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a
		// new callback to handle the response.
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// If the response is successful
						if (session == Session.getActiveSession()) {
							if (user != null) {

								Log.i("aa", "qq");
								idUsers = user.getId();
								nameUsers = user.getName();
								onPageStarted();
								new SearchUsers().execute();

							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}
				});

		request.executeAsync();
	}

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			// Get the user's data.
			makeMeRequest(session);
		}
	}

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	private static final int REAUTH_ACTIVITY_CODE = 100;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	// บันถึงข้อมูลผู้ใช้ลงไปในฐานข้อมูล
	class CreateNewUsers extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idUsers", idUsers));
			params.add(new BasicNameValuePair("nameUsers", nameUsers));
			params.add(new BasicNameValuePair("tema", tema));
			params.add(new BasicNameValuePair("idStatus", "1"));

			// getting JSON Object
			// Note that create product url accepts POST method
			// ส่งค่าเป็นPOSTเข้าไปในjsonParser
			jsonParser.makeHttpRequest(urlInsert, "POST", params);

			return null;
		}

	}

	// ตรวจสอบว่ามีผู้ใช้เคยลงทะเบียนหรือยัง
	public class SearchUsers extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idUsers", idUsers));

			try {
				urlSearch = "http://110.164.78.161/~b531610005/jsonUsers.php?idUsers="
						+ idUsers;

				Log.i("idUsers", urlSearch);
				JSONArray data = new JSONArray(getJSONUrl(urlSearch, params));
				for (int i = 0; i < data.length(); i++) {
					JSONObject c = data.getJSONObject(i);

					users = new HashMap<String, String>();
					users.put("idUsers", c.getString("idUsers"));
					users.put("tema", c.getString("tema"));
					tema = users.get("tema");
					// ผู้ใช้เคยลงทะเบียนแล้วให้ไปหน้าMAP
					if (tema != null) {
						Intent intent1 = new Intent(getActivity(), Map.class);
						startActivity(intent1);
					}
					MyArrList.add(users);
					pDialog.dismiss();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pDialog.cancel();
			return null;
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
