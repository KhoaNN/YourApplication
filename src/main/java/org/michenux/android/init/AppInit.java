package org.michenux.android.init;

import org.michenux.android.db.sqlite.SQLiteDatabaseFactory;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Michenux
 *
 */
@Singleton
public class AppInit {

	/**
	 * Transaction Factory
	 */
	@Inject private SQLiteDatabaseFactory dbFactory ;
	
	/**
	 * @param context
	 * @throws NameNotFoundException
	 */
	public void init( Context context ) throws NameNotFoundException {
		this.dbFactory.init( context, true, true );
	}
	
}
