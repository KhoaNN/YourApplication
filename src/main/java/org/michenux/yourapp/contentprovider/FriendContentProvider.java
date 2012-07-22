package org.michenux.yourapp.contentprovider;

import org.michenux.android.db.sqlite.SQLiteDatabaseFactory;
import org.michenux.yourapp.model.FriendTable;

import roboguice.content.RoboContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.inject.Inject;

/**
 * @author Michenux
 * 
 */
public class FriendContentProvider extends RoboContentProvider {

	/**
	 * 
	 */
	private static final int FRIENDS = 10;

	/**
	 * 
	 */
	private static final int FRIEND_ID = 20;

	/**
	 * 
	 */
	private static final String AUTHORITY = "org.michenux.yourapp.contentprovider.friends";

	/**
	 * 
	 */
	private static final String BASE_PATH = "friends";

	/**
	 * 
	 */
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	/**
	 * 
	 */
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/friends";

	/**
	 * 
	 */
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/friend";

	/**
	 * 
	 */
	private static final UriMatcher uriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		uriMatcher.addURI(AUTHORITY, BASE_PATH, FRIENDS);
		uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", FRIEND_ID);
	}

	
	@Inject private SQLiteDatabaseFactory dbFactory ;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#delete(android.net.Uri,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = uriMatcher.match(uri);
		SQLiteDatabase sqlDB = this.dbFactory.getDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case FRIENDS:
			rowsDeleted = sqlDB.delete(FriendTable.NAME, selection,
					selectionArgs);
			break;
		case FRIEND_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(FriendTable.NAME,
						FriendTable.Fields._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(FriendTable.NAME,
						FriendTable.Fields._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = this.uriMatcher.match(uri);
		SQLiteDatabase sqlDB = this.dbFactory.getDatabase();
		long id = 0;
		switch (uriType) {
		case FRIENDS:
			id = sqlDB.insert(FriendTable.NAME, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase sqlDB = this.dbFactory.getDatabase();
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(FriendTable.NAME);

		int uriType = this.uriMatcher.match(uri);
		switch (uriType) {
		case FRIENDS:
			break;
		case FRIEND_ID:
			// Adding the ID to the original query
			queryBuilder.appendWhere(FriendTable.Fields._ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		// SQLiteDatabase db = database.getWritableDatabase();
		Log.d("LMI", "sqldb: " + sqlDB );
		Cursor cursor = queryBuilder.query(sqlDB, projection, selection,
				selectionArgs, null, null, sortOrder);
		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int uriType = this.uriMatcher.match(uri);
		SQLiteDatabase sqlDB = this.dbFactory.getDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case FRIENDS:
			rowsUpdated = sqlDB.update(FriendTable.NAME, values, selection,
					selectionArgs);
			break;
		case FRIEND_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(FriendTable.NAME, values,
						FriendTable.Fields._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(FriendTable.NAME, values,
						FriendTable.Fields._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

}
