package com.matrix.mypawa;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PowerViewModel extends AndroidViewModel {

    private PowerRepository repository;
    private LiveData<List<Power>> allPowers;

    public PowerViewModel(@NonNull Application application) {
        super(application);
        repository = new PowerRepository(application);
        allPowers = repository.getAllPowers();
    }

    public void insert(Power power){
        repository.insert(power);
    }

    public void update(Power power){
        repository.update(power);
    }

    public void delete(Power power){
        repository.delete(power);
    }

    public LiveData<List<Power>> getAllPowers(){
        return allPowers;
    }
}
