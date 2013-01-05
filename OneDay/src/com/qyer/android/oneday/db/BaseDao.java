package com.qyer.android.oneday.db;

import com.qyer.android.oneday.util.AppInfoUtil;
import com.qyer.android.oneday.util.LogManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

class BaseDao implements DBFiled{
	public static final int OPTION_SUCCESS = 0;
	public static final int OPTION_FAILED = -1;
	public static final int OPTION_REPEAT = -2;
	public static final int OPTION_EXCEPTION = -3;
	
	private DBHelper mDBHelper;
	
	protected BaseDao(Context context)
	{
		mDBHelper = new DBHelper(context);
	}

	protected SQLiteDatabase getWritableDatabase()
	{
		return mDBHelper.getWritableDatabase();
	}
	
	protected SQLiteDatabase getReadableDatabase()
	{
		return mDBHelper.getReadableDatabase();
	}
	
	protected SQLiteDatabase getTransactionDatabase()
	{
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		db.beginTransaction();
		return db;
	}
	
	public void close()
	{
		mDBHelper.close();
	}
	
	protected void closeCusrsor(Cursor cursor)
	{
		if(cursor != null)
			cursor.close();
	}
	
	protected void closeDB(SQLiteDatabase db)
	{
		if(db != null)
			db.close();
	}
	
	protected void closeStatement(SQLiteStatement statement)
	{
		if(statement != null)
			statement.close();
	}
	
	protected void endTransaction(SQLiteDatabase db)
	{
		if(db != null){
			db.endTransaction();
			db.close();
		}
	}
	
	private class DBHelper extends SQLiteOpenHelper{
		
		public static final String TAG = "DBHelper";
		
		protected DBHelper(Context context)
		{
			super(context, DBFiled.DB_NAME, null, Integer.parseInt(AppInfoUtil.getVersionCode(context)));
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			LogManager.printD(TAG, "onCreate");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			LogManager.printD(TAG, "onUpgrade oldVersion = "+oldVersion+", newVersion = "+newVersion);
			// for(int i=oldVersion+1;i<=newVersion;i++){
			// switch(i){
			// case VERSION_2:
			// upgradeVersion2(db);
			// break;
			// }
			// }
		}
		
		// private void upgradeVersion2(SQLiteDatabase db)
		// {
		// }
	}
}
