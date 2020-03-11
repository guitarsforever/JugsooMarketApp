package com.example.jugsoomarket.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jugsoomarket.DB.itemDatabase;
import com.example.jugsoomarket.Dao.ItemDao;
import com.example.jugsoomarket.ViewModel.item;

import java.util.List;

public class ItemRepository {
    private ItemDao itemDao;
    private LiveData<List<item>> allNotes;

    public ItemRepository(Application application) {
        itemDatabase database = itemDatabase.getInstance(application);
        itemDao = database.itemDao();
        allNotes = itemDao.getAllItems();
    }

    public void insert(item item) {
        new InsertItemAsyncTask(itemDao).execute(item);
    }

    public void update(item item) {
        new UpdateItemAsyncTask(itemDao).execute(item);
    }

    public void delete(item item) {
        new DeleteItemAsyncTask(itemDao).execute(item);
    }

    public void deleteAllItems() {
        new DeleteAllItemsAsyncTask(itemDao).execute();
    }

    public LiveData<List<item>> getAllNotes() {
        return allNotes;
    }

    private static class InsertItemAsyncTask extends AsyncTask<item, Void, Void> {
        private ItemDao itemDao;

        private InsertItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(item... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }

    private static class UpdateItemAsyncTask extends AsyncTask<item, Void, Void> {
        private ItemDao itemDao;

        private UpdateItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(item... items) {
            itemDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<item, Void, Void> {
        private ItemDao itemDao;

        private DeleteItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }

    private static class DeleteAllItemsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ItemDao itemDao;

        private DeleteAllItemsAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.deleteAllItems();
            return null;
        }
    }
}
