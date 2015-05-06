package com.smart.telanganartc.task;

import java.lang.reflect.Type;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smart.telanganartc.MainActivity;
import com.smart.telanganartc.R;
import com.smart.telanganartc.restful.StationInfoService;
import com.smart.telanganartc.utilities.AppUtils;
import com.smart.telanganartc.vo.StationVO;

public class StationInfoAsyncTask extends AsyncTask<Void, Void, List<StationVO>> {

	private MainActivity activity;

	public StationInfoAsyncTask(MainActivity mainActivity) {
		this.activity = mainActivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
		RelativeLayout relativeLayout = (RelativeLayout) activity.findViewById(R.id.relativeLayout);

		progressBar.setVisibility(View.VISIBLE);
		relativeLayout.setVisibility(View.GONE);
	}

	protected void onPostExecute(List<StationVO> result) {
		
		MainActivity.stationList = result;
		Gson gson = new Gson();
		Type collectionType = new TypeToken<List<StationVO>>() {}.getType();
		String jsonData = gson.toJson(result,collectionType);
		
		SharedPreferences pref =  activity.getPreferences(Activity.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("STATION_LIST", jsonData);
		editor.commit();
		
		ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
		RelativeLayout relativeLayout = (RelativeLayout) activity.findViewById(R.id.relativeLayout);
		progressBar.setVisibility(View.GONE);
		relativeLayout.setVisibility(View.VISIBLE);
	}

	@Override
	protected List<StationVO> doInBackground(Void ... params) {

		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(AppUtils.STATION_INFO_URL).build();
		StationInfoService stationService = adapter.create(StationInfoService.class);
		List<StationVO> stations = null;
		try 
		{
			stations = stationService.getStations();	
		}
		catch(RetrofitError err)
		{
			Log.e("RETRO ERROR:", err.toString());
		}
		return stations;
	}
}