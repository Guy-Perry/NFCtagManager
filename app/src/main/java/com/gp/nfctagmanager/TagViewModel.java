package com.gp.nfctagmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class TagViewModel extends AndroidViewModel {
    private TagRepository repository;
    private LiveData<List<Tag>> allTags;

    public TagViewModel(@NonNull Application application) {
        super(application);
        repository = new TagRepository(application);
        allTags = repository.getAllTags();
    }

    public void insert(Tag tag){
        repository.insert(tag);
    }

    public void update(Tag tag){
        repository.update(tag);
    }

    public void delete(Tag tag){
        repository.delete(tag);
    }

    public void deleteAllTags(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Tag>> getAllTags(){
        return allTags;
    }
}
