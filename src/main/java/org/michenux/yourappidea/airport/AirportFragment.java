package org.michenux.yourappidea.airport;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

import org.michenux.yourappidea.R;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

//http://www.flightradar24.com/AirportInfoService.php?airport=ORY&type=in
//LFRS
public class AirportFragment extends ListFragment {

	private WeakReference<AirportAsyncTask> asyncTaskWeakRef;

	private Menu optionsMenu;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		AirportAdapter airportAdapter = new AirportAdapter(this.getActivity(), R.id.flight_name, new ArrayList<Flight>());
		setListAdapter(airportAdapter);
		startNewAsyncTask();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		this.optionsMenu = menu;
		inflater.inflate(R.menu.airport_menu, menu);

		if (isAsyncTaskPendingOrRunning()) {
			setRefreshActionButtonState(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.airport_menuRefresh:

			if (!isAsyncTaskPendingOrRunning()) {
				this.startNewAsyncTask();
			}

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void startNewAsyncTask() {
		AirportAsyncTask oAsyncTaskArret = new AirportAsyncTask(this);
		this.asyncTaskWeakRef = new WeakReference<AirportAsyncTask>(oAsyncTaskArret);
		oAsyncTaskArret.execute();
	}

	private boolean isAsyncTaskPendingOrRunning() {
		return this.asyncTaskWeakRef != null
				&& this.asyncTaskWeakRef.get() != null
				&& !this.asyncTaskWeakRef.get().getStatus()
						.equals(Status.FINISHED);
	}

	
	public void setRefreshActionButtonState(final boolean refreshing) {
		if (optionsMenu != null) {
			final MenuItem refreshItem = optionsMenu
					.findItem(R.id.airport_menuRefresh);
			if (refreshItem != null) {
				if (refreshing) {
					refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
				} else {
					refreshItem.setActionView(null);
				}
			}
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		if ( this.asyncTaskWeakRef.get() != null ) {
			this.asyncTaskWeakRef.get().cancel(true);
		}
	}
	
	private static class AirportAsyncTask extends AsyncTask<String, Void, AirportRestResponse> {

		private WeakReference<AirportFragment> fragmentWeakRef;

		private boolean error = false;

		private AirportAsyncTask(AirportFragment fragment) {
			this.fragmentWeakRef = new WeakReference<AirportFragment>(fragment);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.fragmentWeakRef.get().setRefreshActionButtonState(true);
		}

		@Override
		protected AirportRestResponse doInBackground(String... params) {

			AirportRestResponse result = null;
			try {
				
				result = AirportRestService.getInstance().getAirportInfo("ORY", AirportRestService.IN_MODE, 
						this.fragmentWeakRef.get().getString(R.string.airport_rest_url));
				
			} catch (Exception e) {
				Log.e("LMI","Error retrieving data", e);
				this.error = true;
			}
			return result;
		}

		@Override
		protected void onPostExecute(AirportRestResponse airportResponse) {
			super.onPostExecute(airportResponse);

			if (this.fragmentWeakRef.get() != null) {

				AirportFragment fragment = this.fragmentWeakRef.get();
				if (!this.error) {

					AirportAdapter adapter = (AirportAdapter) fragment.getListAdapter();
					adapter.clear();
					Collections.sort(airportResponse.getFlights(),new FlightEtaComparator());
					adapter.addAll(airportResponse.getFlights());
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(this.fragmentWeakRef.get().getActivity(),
							fragment.getActivity().getString(R.string.error_retrievingdata),
							Toast.LENGTH_SHORT).show();
				}
				fragment.setRefreshActionButtonState(false);
			}
		}
	}
}
