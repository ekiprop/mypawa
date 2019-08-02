package com.matrix.mypawa;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Power.class}, version = 1)
public abstract class PowerDatabase extends RoomDatabase {

    private static PowerDatabase instance;

    public abstract PowerDao powerDao();

    public static synchronized PowerDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PowerDatabase.class, "power_database")
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
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private PowerDao powerDao;
        private  PopulateDbAsyncTask(PowerDatabase db){
            powerDao = db.powerDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            powerDao.insert(new Power("Device 1","+254702565559", "TF",
                    null, null,null));
            powerDao.insert(new Power("Device 2","+254702565559", null,
                    "TF1", null,null));
            powerDao.insert(new Power("Device 3","+254702565559", null,
                    null, "TF2",null));
            powerDao.insert(new Power("Device 4","+254702565559", null,
                    null, null,"TF3"));

            return null;
        }
    }
}
