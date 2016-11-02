package net.sarangnamu.test_blob.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import net.sarangnamu.common.sqlite.DbHelperBase;
import net.sarangnamu.common.sqlite.DbManager;

import java.util.HashMap;

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2016. 11. 2.. <p/>
 */
public class DbHelper extends DbHelperBase {
    private static final String TAG = "DbHelper";

    private static final String DB_NAME = "bmp.db";
    private static final int VERSION = 1;
    public static final String[] FIELDS = new String[] {
            Columns.DATA,
    };

    public DbHelper(Context context) {
        super(context, DB_NAME, VERSION);

        mTables = new HashMap<>();
        mTables.put(Columns.TABLE, Columns.CREATE);
    }

    public static int getCount() {
        String sql = "SELECT COUNT(*) FROM " + Columns.TABLE;
        Cursor cr = DbManager.getInstance().rawQuery(sql, null);
        if (cr == null) {
            return 0;
        }

        cr.moveToFirst();

        int count = cr.getInt(0);
        cr.close();

        Log.d(TAG, "COUNT : " + count);
        return count;
    }

    public static byte[] getData() {
        String sql = "SELECT " + Columns.DATA +" FROM " + Columns.TABLE;
        Cursor cr = DbManager.getInstance().rawQuery(sql, null);
        byte[] data = null;

        cr.moveToNext();

        try {
            data = cr.getBlob(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cr.close();

        return data;
    }

    public static boolean insert(byte[] data) {
        try {
            ContentValues values = new ContentValues();
            values.put(Columns.DATA, data);

            return DbManager.getInstance().insert(Columns.TABLE, values) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static final class Columns implements BaseColumns {
        public static final String DATA   = "data";
        public static final String TABLE  = "bmp";
        public static final String CREATE = "CREATE TABLE " + TABLE + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATA + " BLOB"
                + ");";
    }
}
