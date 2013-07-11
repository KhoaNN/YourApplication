package org.michenux.yourappidea.airport;

import java.util.ArrayList;
import java.util.Collections;

import org.michenux.android.rest.GsonRequest;
import org.michenux.yourappidea.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

//http://www.flightradar24.com/AirportInfoService.php?airport=ORY&type=in
//LFRS
public class AirportFragment extends ListFragment {

	private static final Logger log = LoggerFactory.getLogger(AirportFragment.class);
	
	private Menu optionsMenu;

	private RequestQueue requestQueue;
	
	private boolean requestRunning = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		log.info("AirportFragment.onCreate");
		
		setRetainInstance(true);
		setHasOptionsMenu(true);

		AirportAdapter airportAdapter = new AirportAdapter(this.getActivity(),
				R.id.flight_name, new ArrayList<Flight>());
		setListAdapter(airportAdapter);

		this.requestQueue = Volley.newRequestQueue(this.getActivity());
		this.startRequest();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		log.info("AirportFragment.onCreateOptionsMenu");
		this.optionsMenu = menu;
		inflater.inflate(R.menu.airport_menu, menu);
		if ( this.requestRunning) {
			this.setRefreshActionButtonState(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.airport_menuRefresh:
			this.startRequest();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void startRequest() {
		log.info("AirportFragment.startRequest");
		if ( !requestRunning ) {
			AirportFragment.this.requestRunning = true ;
			GsonRequest<AirportRestResponse> jsObjRequest = new GsonRequest<AirportRestResponse>(
					Method.GET,
					getString(R.string.airport_rest_url),
					AirportRestResponse.class, null,
					this.createAirportRequestSuccessListener(),
					this.createAirportRequestErrorListener());
			jsObjRequest.setShouldCache(false);
			this.setRefreshActionButtonState(true);
			this.requestQueue.add(jsObjRequest);
		}
		else {
			log.info("  request is already running");
		}
	}

	public void setRefreshActionButtonState(final boolean refreshing) {
		if (optionsMenu != null) {
			final MenuItem refreshItem = optionsMenu
					.findItem(R.id.airport_menuRefresh);
			if (refreshItem != null) {
				if (refreshing) {
					refreshItem
							.setActionView(R.layout.actionbar_indeterminate_progress);
				} else {
					refreshItem.setActionView(null);
				}
			}
		}
	}

	private Response.Listener<AirportRestResponse> createAirportRequestSuccessListener() {
		return new Response.Listener<AirportRestResponse>() {
			@Override
			public void onResponse(AirportRestResponse response) {
				log.info("AirportFragment.onResponse");
				AirportAdapter adapter = (AirportAdapter) AirportFragment.this
						.getListAdapter();
				adapter.clear();
				Collections.sort(response.getFlights(),
						new FlightEtaComparator());
				adapter.addAll(response.getFlights());
				adapter.notifyDataSetChanged();
				AirportFragment.this.setRefreshActionButtonState(false);
				AirportFragment.this.requestRunning = false ;
			}
		};
	}

	private Response.ErrorListener createAirportRequestErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				log.info("AirportFragment.onErrorResponse");
				AirportFragment.this.setRefreshActionButtonState(false);
				AirportFragment.this.requestRunning = false ;
				
				Crouton.makeText(
					AirportFragment.this.getActivity(),
					getString(R.string.error_retrievingdata),
					Style.ALERT).show();
				
			}
		};
	}
	
	@Override
	public void onDestroy() {
		log.info("AirportFragment.onDestroy");
		this.requestQueue.cancelAll(this);
		Crouton.cancelAllCroutons();
		super.onDestroy();
	}
}
