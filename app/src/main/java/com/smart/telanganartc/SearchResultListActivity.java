package com.smart.telanganartc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.smart.telanganartc.adapter.SearchResultAdapter;
import com.smart.telanganartc.vo.SearchResultVO;

public class SearchResultListActivity extends ActionBarActivity{

	private AdView adView;
	ArrayList<SearchResultVO> list = null;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_results_list);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		list = this.getIntent().getParcelableArrayListExtra("SearchResults");
		if(savedInstanceState!=null && list==null)
		{

			list = savedInstanceState.getParcelableArrayList("List");
		}
		final ListView listView = (ListView) findViewById(R.id.list);
		SearchResultAdapter adapter = new SearchResultAdapter(this, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position>0)
				{
					SearchResultVO resultVO = (SearchResultVO) listView.getAdapter().getItem(position);
					Intent intent = new Intent(SearchResultListActivity.this, JourneyDetailsActivity.class);
					intent.putExtra("SearchResultVO", resultVO);
					intent.putExtra("FROM", SearchResultListActivity.this.getIntent().getStringExtra("FROM"));
					intent.putExtra("TO", SearchResultListActivity.this.getIntent().getStringExtra("TO"));
					intent.putExtra("DATE", SearchResultListActivity.this.getIntent().getStringExtra("DATE"));

					startActivity(intent);
				}
			}
		});
		TextView fromTextView = (TextView) findViewById(R.id.fromTextView);
		TextView toTextView = (TextView) findViewById(R.id.toTextView);
		TextView dateTextView = (TextView) findViewById(R.id.dateTextView);

		fromTextView.setText(getIntent().getStringExtra("FROM"));
		toTextView.setText(getIntent().getStringExtra("TO"));

		String dateStr = getIntent().getStringExtra("DATE");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			Log.e("Error", e.getLocalizedMessage());
		}
		format.applyPattern("EEE dd MMM, yyyy");
		dateTextView.setText("JOURNEY DATE : "+format.format(date));

		adView = (AdView)this.findViewById(R.id.adMobView1);
		AdRequest adRequest = new AdRequest.Builder()
		.build();
		adView.loadAd(adRequest);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("List", list);
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if(savedInstanceState!=null)
		{
			list = savedInstanceState.getParcelableArrayList("List");
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
}
