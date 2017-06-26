package test.com.rxjavarxandroid.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;


/**
 * Created by xiaomo on 2016/8/11.
 * 数据库升级处理
 */
public class HDevOpenHelper extends  DaoMaster.OpenHelper{

    public HDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        /**
         * 数据库升级
         * 1、创建临时数据库备份数据
         * 2、删除数据库
         * 3、创建新数据库并恢复数据
         * 参数：1、db 2、所以得数据库dao
         */
        MigrationHelper.migrate(db,ChannleEntityDao.class);
    }
}
