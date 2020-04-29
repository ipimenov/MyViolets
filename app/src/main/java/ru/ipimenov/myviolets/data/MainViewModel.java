package ru.ipimenov.myviolets.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static VioletDatabase database;
    private LiveData<List<Violet>> violets;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = VioletDatabase.getInstance(getApplication());
        violets = database.violetDao().getAllViolets();
    }

    public Violet getVioletById(int id) {
        try {
            return new GetVioletTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllViolets() {
        new DeleteAllVioletsTask().execute();
    }

    public void insertViolet(Violet violet) {
        new InsertVioletTask().execute(violet);
    }

    public void deleteViolet(Violet violet) {
        new DeleteVioletTask().execute(violet);
    }

    public LiveData<List<Violet>> getViolets() {
        return violets;
    }

    private static class DeleteVioletTask extends AsyncTask<Violet, Void, Void> {

        @Override
        protected Void doInBackground(Violet... violets) {
            if (violets != null && violets.length > 0) {
                database.violetDao().deleteViolet(violets[0]);
            }
            return null;
        }
    }

    private static class InsertVioletTask extends AsyncTask<Violet, Void, Void> {

        @Override
        protected Void doInBackground(Violet... violets) {
            if (violets != null && violets.length >0) {
                database.violetDao().insertViolet(violets[0]);
            }
            return null;
        }
    }

    private static class DeleteAllVioletsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.violetDao().deleteAllViolets();
            return null;
        }
    }

    private static class GetVioletTask extends AsyncTask<Integer, Void, Violet> {

        @Override
        protected Violet doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.violetDao().getVioletById(integers[0]);
            }
            return null;
        }
    }
}