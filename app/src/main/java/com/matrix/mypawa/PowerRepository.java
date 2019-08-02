package com.matrix.mypawa;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


public class PowerRepository {

    private PowerDao powerDao;

    private LiveData<List<Power>> allPowers;


    public PowerRepository(Application application){
        PowerDatabase powerDatabase = PowerDatabase.getInstance(application);
        powerDao = powerDatabase.powerDao();
        allPowers = powerDao.getAllPowers();
    }

    public void insert(Power power){
        new InsertPowerAsyncTask(powerDao).execute(power);
    }

    public void update(Power power){
        new UpdatePowerAsyncTask(powerDao).execute(power);
    }

    public void delete(Power power){
        new DeletePowerAsyncTask(powerDao).execute(power);
    }

    public LiveData<List<Power>> getAllPowers(){
        return allPowers;
    }

    private static class InsertPowerAsyncTask extends AsyncTask<Power, Void, Void>{
        private  PowerDao powerDao;
        private InsertPowerAsyncTask(PowerDao powerDao){
            this.powerDao = powerDao;
        }

        @Override
        protected Void doInBackground(Power... powers) {
            powerDao.insert(powers[0]);
            return null;
        }
    }

    private static class UpdatePowerAsyncTask extends AsyncTask<Power, Void, Void>{
        private  PowerDao powerDao;
        private UpdatePowerAsyncTask(PowerDao powerDao){
            this.powerDao = powerDao;
        }

        @Override
        protected Void doInBackground(Power... powers) {
            powerDao.update(powers[0]);
            return null;
        }
    }

    private static class DeletePowerAsyncTask extends AsyncTask<Power, Void, Void>{
        private  PowerDao powerDao;
        private DeletePowerAsyncTask(PowerDao powerDao){
            this.powerDao = powerDao;
        }

        @Override
        protected Void doInBackground(Power... powers) {
            powerDao.delete(powers[0]);
            return null;
        }
    }
}
