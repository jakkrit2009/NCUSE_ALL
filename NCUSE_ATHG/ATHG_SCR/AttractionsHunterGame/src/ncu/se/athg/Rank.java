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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class Rank extends Activity {

	private ArrayList<Ranking> tRanking = new ArrayList<Ranking>();
	TabHost mTabHost;
	final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	final ArrayList<HashMap<String, String>> MyArrListUsers = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> rUsers;
	final ArrayList<HashMap<String, String>> MyArrListTeamUsers = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> rReam;
	int landgon, eagle, sumEagle, sumLandgon, cityLandgon, cityEagle;
	ListView list_ranking, list_top_tan, list_team;
	String nameUsers, arrogate, tema, nameStatus, teamUsers;
	private String[] nTeam = new String[2];
	private String[] sumCity = new String[2];
	private String[] sumAmount = new String[2];
	private int[] drawable = new int[2];
	private ProgressDialog pDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank);

		list_ranking = (ListView) findViewById(R.id.list_ranking);
		list_top_tan = (ListView) findViewById(R.id.list_top_ten);
		list_team = (ListView) findViewById(R.id.list_team);
		// ∑”TabHost„ÀÈ‡≈◊Õ°¥Ÿ
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mTabHost.addTab(mTabHost.newTabSpec("tab_test1")
				.setIndicator("RANKING").setContent(ranking())
				.setContent(R.id.tab1));
		mTabHost.addTab(mTabHost.newTabSpec("tab_test2")
				.setIndicator("RANKING TOP TEN").setContent(rankingTop())
				.setContent(R.id.tab2));
		mTabHost.addTab(mTabHost.newTabSpec("tab_test3")
				.setIndicator("RANKING TEAM").setContent(rankingTeam())
				.setContent(R.id.tab3));

		mTabHost.setCurrentTab(0);
		Intent myInteger = getIntent();
		teamUsers = myInteger.getStringExtra("team");

	}

	// · ¥ßDialog∫Õ°«Ë“Loading..
	public void onPageStarted() {
		pDialog = new ProgressDialog(this);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage("Loading..");
		pDialog.setTitle("Attractions Hunter Game");
		pDialog.setCancelable(false);
		pDialog.show();
	}

	// °¥rankingTeamµ√«® Õ∫«Ë“∑’¡‡√“∑’ËÕ–‰√
	private TabContentFactory rankingTeam() {
		new SerchUsersTeam().execute();
		onPageStarted();
		return null;
	}

	// °¥rankingTop®–· ¥ß10§π∑’Ë¡’§–·ππ¡“° ÿ¥
	private TabContentFactory rankingTop() {
		new SerchTop().execute();
		return null;
	}

	// °¥ranking· ¥ß§–·ππ¢Õß∑’¡
	private TabContentFactory ranking() {
		new SerchTeam().execute();
		return null;
	}

	// ·∫ËßList¢Õßranking
	private class ListViewRanking extends BaseAdapter {

		public ImageView imgTeam;
		public TextView teamName, city, amount;

		// ·∫∫°’Ë™ËÕß
		@Override
		public int getCount() {

			return 2;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// · ¥ß¢ÈÕ¡Ÿ≈„πList
			convertView = LayoutInflater.from(Rank.this).inflate(
					R.layout.ranking, null);
			imgTeam = (ImageView) convertView.findViewById(R.id.imgTeam);
			teamName = (TextView) convertView.findViewById(R.id.teamName);
			city = (TextView) convertView.findViewById(R.id.city);
			amount = (TextView) convertView.findViewById(R.id.amount);

			imgTeam.setImageDrawable(tRanking.get(position).getDrawable());
			teamName.setText(tRanking.get(position).getnTeam());
			city.setText("®”π«π‡¡◊ËÕß\t" + tRanking.get(position).getSumCity()
					+ "\t‡¡◊Õß");
			amount.setText("§√—Èß„π°“√¬÷¥§√Õß\t"
					+ tRanking.get(position).getSumAmount() + "\t§√—Èß");

			return convertView;
		}

	}

	// ·∫ËßList¢ÕßrankingTeam
	private class ListViewRankingTeam extends BaseAdapter {

		public TextView nameUsers, statusUsers, locationsUsers, num;

		// ·∫∫°’Ë™ËÕßπ—∫µ“¡®”π«πArrListTeamUsers
		@Override
		public int getCount() {
			return MyArrListTeamUsers.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// · ¥ß¢ÈÕ¡Ÿ≈„πList

			convertView = LayoutInflater.from(Rank.this).inflate(
					R.layout.ranking_team, null);
			nameUsers = (TextView) convertView.findViewById(R.id.nameUsers);
			statusUsers = (TextView) convertView.findViewById(R.id.statusUsers);
			locationsUsers = (TextView) convertView
					.findViewById(R.id.locationsUsers);
			num = (TextView) convertView.findViewById(R.id.num);

			num.setText(MyArrListUsers.get(position).get("num").toString());
			nameUsers.setText("™◊ËÕ :\t "
					+ MyArrListTeamUsers.get(position).get("nameUsers")
							.toString());
			statusUsers.setText("®”π«π ∂“π∑’Ë  :\t "
					+ MyArrListTeamUsers.get(position).get("arrogate")
							.toString() + "·ÀπËß");
			locationsUsers.setText("¬»   :\t "
					+ MyArrListTeamUsers.get(position).get("nameStatus")
							.toString());

			return convertView;
		}

	}

	// ·∫ËßList¢ÕßrankingTeam
	private class ListViewTopTen extends BaseAdapter {

		public TextView team, name, statusName, locations, num;

		// ·∫∫°’Ë™ËÕßπ—∫µ“¡®”π«πArrList
		@Override
		public int getCount() {

			return MyArrListUsers.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int positionUsers, View convertView,
				ViewGroup parent) {
			// · ¥ß¢ÈÕ¡Ÿ≈„πList
			convertView = LayoutInflater.from(Rank.this).inflate(
					R.layout.ranking_top_ten, null);
			name = (TextView) convertView.findViewById(R.id.name);
			team = (TextView) convertView.findViewById(R.id.team);
			statusName = (TextView) convertView.findViewById(R.id.statusName);
			locations = (TextView) convertView.findViewById(R.id.locations);
			num = (TextView) convertView.findViewById(R.id.num);

			name.setText("™◊ËÕ :\t "
					+ MyArrListUsers.get(positionUsers).get("nameUsers")
							.toString());
			locations.setText("®”π«π ∂“π∑’Ë  :\t "
					+ MyArrListUsers.get(positionUsers).get("arrogate")
							.toString() + "·ÀπËß");
			statusName.setText("¬»   :\t "
					+ MyArrListUsers.get(positionUsers).get("nameStatus")
							.toString());
			team.setText("∑’¡   : \t"
					+ MyArrListUsers.get(positionUsers).get("tema").toString());
			num.setText(MyArrListUsers.get(positionUsers).get("num").toString());

			return convertView;

		}

	}

	// µ√«® Õ∫∑’¡«Ë“·µË≈–∑’¡¡’§–·ππ‡∑Ë“‰À√Ë
	class SerchTeam extends AsyncTask<String, Integer, String> {

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
					map.put("eagle", c.getString("eagle"));
					map.put("landgon", c.getString("landgon"));

					MyArrList.add(map);

					eagle = Integer.parseInt(c.getString("eagle"));
					sumEagle = sumEagle + eagle;
					Log.v("eagle", "" + eagle);
					landgon = Integer.parseInt(c.getString("landgon"));
					Log.v("landgon", "" + landgon);
					// µ√«® Õ∫«Ë“∑’¡‰Àπ¡’‡¡◊ËÕß„π°“√¬÷¥§√Õß¡“°°«Ë“°—π
					if (eagle >= landgon) {

						cityEagle = cityEagle + 1;

					} else {
						cityLandgon = cityLandgon + 1;
					}
					sumEagle = sumEagle + eagle;

					sumLandgon = sumLandgon + landgon;

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (cityEagle >= cityLandgon) {
				drawable[0] = R.drawable.eagle;
				drawable[1] = R.drawable.landgon;
				sumCity[0] = String.valueOf(cityEagle);
				sumCity[1] = String.valueOf(cityLandgon);
				sumAmount[0] = String.valueOf(sumEagle);
				sumAmount[1] = String.valueOf(sumLandgon);
				nTeam[0] = "Eagle";
				nTeam[1] = "Landgon";
				for (int i = 0; i < drawable.length; i++) {
					Ranking ranking = new Ranking();

					ranking.setnTeam(nTeam[i]);
					ranking.setSumCity(sumCity[i]);
					ranking.setSumAmount(sumAmount[i]);
					ranking.setDrawable(getResources().getDrawable(drawable[i]));
					tRanking.add(ranking);

				}
			} else {
				drawable[0] = R.drawable.landgon;
				drawable[1] = R.drawable.eagle;
				sumCity[0] = String.valueOf(cityLandgon);
				sumCity[1] = String.valueOf(cityEagle);
				sumAmount[0] = String.valueOf(sumLandgon);
				sumAmount[1] = String.valueOf(sumEagle);
				nTeam[0] = "Landgon";
				nTeam[1] = "Eagle";
				for (int i = 0; i < drawable.length; i++) {

					Ranking ranking = new Ranking();

					ranking.setnTeam(nTeam[i]);
					ranking.setSumCity(sumCity[i]);
					ranking.setSumAmount(sumAmount[i]);
					ranking.setDrawable(getResources().getDrawable(drawable[i]));
					tRanking.add(ranking);
				}

			}

			list_ranking.setAdapter(new ListViewRanking());

		}
	}

	// µ√«® Õ∫ºŸÈ∑’Ë‰¥È§–·ππ¡“°∑’Ë ÿ¥ 10 ∑’¡·√°
	class SerchTop extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String url = "http://110.164.78.161/~b531610005/jsonUsersTop.php";
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

					rUsers = new HashMap<String, String>();
					rUsers.put("nameUsers", c.getString("nameUsers"));
					rUsers.put("arrogate", c.getString("arrogate"));
					rUsers.put("tema", c.getString("tema"));
					rUsers.put("nameStatus", c.getString("nameStatus"));
					rUsers.put("num", String.valueOf(i + 1));

					MyArrListUsers.add(rUsers);
					nameUsers = c.getString("nameUsers");
					arrogate = c.getString("arrogate");
					tema = c.getString("tema");
					nameStatus = c.getString("nameStatus");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			list_top_tan.setAdapter(new ListViewTopTen());
			pDialog.cancel();

		}
	}

	// µ√«® Õ∫∑’¡¢ÕßºŸÈ„™È
	public class SerchUsersTeam extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub

			// Paste Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("tema", teamUsers));
			Log.i("aaaaaaaa", teamUsers);

			try {
				String urlUsers = "http://110.164.78.161/~b531610005/jsonUsersTeam.php?tema="
						+ teamUsers;

				Log.i("ssss", urlUsers);
				JSONArray data = new JSONArray(getJSONUrl(urlUsers, params));
				for (int i = 0; i < data.length(); i++) {
					JSONObject c = data.getJSONObject(i);
					Log.i("aa", "a");

					rReam = new HashMap<String, String>();
					rReam.put("nameUsers", c.getString("nameUsers"));
					rReam.put("arrogate", c.getString("arrogate"));
					rReam.put("nameStatus", c.getString("nameStatus"));
					rReam.put("num", String.valueOf(i + 1));

					MyArrListTeamUsers.add(rReam);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list_team.setAdapter(new ListViewRankingTeam());
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
