package com.gp.nfctagmanager;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Tag.class}, version = 1, exportSchema = false)
public abstract class TagDatabase extends RoomDatabase {

    private static TagDatabase instance;

    public abstract TagDao tagDao();

    public static synchronized TagDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TagDatabase.class, "tag_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //Creates dummy tag in database
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private TagDao tagDao;

        private PopulateDBAsyncTask(TagDatabase db) {
            tagDao = db.tagDao();
        }

        //dummy tag details
        @Override
        protected Void doInBackground(Void... voids){
            tagDao.insert(new Tag("ExampleTagName", "5893268754"));
            return null;
        }
    }
}
