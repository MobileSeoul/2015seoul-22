package com.example.genebe.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.genebe.DebugUtil;
import com.example.genebe.Model.User;

/**
 * Created by malgogi on 15. 9. 16..
 */
public class SQLHelper extends SQLiteOpenHelper {
    private static SQLHelper helper;
    private static SQLiteDatabase db;

    private static final String DB_NAME = "MyRoadClient";
    private static final int DB_VERSION = 1;

    private SQLHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(DebugUtil.TAG, "start sql helper");

    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        String sql = "create table if not exists MyRoadClient(" +
                    "_id integer primary key autoincrement," +
                    "username string," +
                    "password string," +
                    "access_token string," +
                    "refresh_token string" +
                    ")";
        db.execSQL( sql );
        db.close();
        Log.d(DebugUtil.TAG, "create sql helper");
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        String sql = "drop table if exists MyRoadClient";
        db.execSQL( sql );
        onCreate( db );
    }

    public static SQLHelper getInstance( Context context ) {
        //access db and insert user info

        if ( helper == null ) {
            helper = new SQLHelper(
                    context.getApplicationContext(),
                    DB_NAME + ".db",
                    null,
                    DB_VERSION );
        }

        helper.onCreate( helper.getWritableDatabase() );


        return helper;


    }

    public boolean insert( String username, String password, String access_token, String refresh_token ) {
        if ( helper == null ) {
            return false;
        }

        db = helper.getWritableDatabase();

        if ( !db.isOpen() ) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put( "username", username );
        values.put( "password", password );
        values.put( "access_token", access_token );
        values.put( "refresh_token", refresh_token );

        db.insert(DB_NAME, null, values);
        db.close();

        return true;
    }

    public boolean delete( int _id ) {
        if ( helper == null ) {
            return false;
        }

        db = helper.getWritableDatabase();

        if ( !db.isOpen() ) {
            return false;
        }

        db.delete( DB_NAME, "_id=?", new String[]{ Integer.toString( _id ) });
        db.close();
        return true;
    }

    public boolean update( int _id, String username, String password, String access_token, String refresh_token ) {
        if ( helper == null ) {
            return false;
        }

        db = helper.getWritableDatabase();

        if ( !db.isOpen() ) {
            return false;
        }

        ContentValues values = new ContentValues();

        if ( username != null ) {
            values.put( "username", username );
        }

        if ( password != null ) {
            values.put( "password", password );
        }

        if ( access_token != null ) {
            values.put( "access_token", access_token );
        }

        if ( refresh_token != null ) {
            values.put( "refresh_token", refresh_token );
        }

        db.update( DB_NAME, values, "_id=?", new String[] { Integer.toString( _id ) } );


        return true;
    }

    public User select() {
        if ( helper == null ) {
            return null;
        }

        db = helper.getReadableDatabase();

        if ( !db.isOpen() ) {
            return null;
        }

        Cursor c = db.query( DB_NAME, null, null, null, null, null, null );
        User user = null;
        while( c.moveToNext() )
        {
            int _id = c.getInt( c.getColumnIndex( "_id" ) );
            String username = c.getString(c.getColumnIndex("username"));
            String password = c.getString( c.getColumnIndex( "password" ) );
            String access_token = c.getString( c.getColumnIndex( "access_token" ) );
            String refresh_token = c.getString( c.getColumnIndex( "refresh_token" ) );

             user = new User( _id, username, password, access_token, refresh_token );
        }

        return user;
    }




}
