package com.gp.nfctagmanager;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TagRepository {

    private TagDao tagDao;
    private LiveData<List<Tag>> allTags;

    public TagRepository(Application application) {
        TagDatabase database = TagDatabase.getInstance(application);
        tagDao = database.tagDao();
        allTags = tagDao.getAllTags();
    }

    public void insert(Tag tag){
        new InsertTagAsyncTask(tagDao).execute(tag);
    }

    public void update(Tag tag){
        new UpdateTagAsyncTask(tagDao).execute(tag);
    }

    public void delete(Tag tag){
        new DeleteTagAsyncTask(tagDao).execute(tag);
    }

    public void deleteAllNotes(){
        new DeleteAllTagsAsyncTask(tagDao).execute();
    }

    public LiveData<List<Tag>> getAllTags() {
        return allTags;
    }

    private static class InsertTagAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private InsertTagAsyncTask(TagDao tagDao){
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.insert(tags[0]);
            return null;
        }
    }

    private static class UpdateTagAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private UpdateTagAsyncTask(TagDao tagDao){
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.update(tags[0]);
            return null;
        }
    }

    private static class DeleteTagAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private DeleteTagAsyncTask(TagDao tagDao){
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.delete(tags[0]);
            return null;
        }
    }

    private static class DeleteAllTagsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TagDao tagDao;

        private DeleteAllTagsAsyncTask(TagDao tagDao){
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tagDao.deleteAllTags();
            return null;
        }
    }
}
