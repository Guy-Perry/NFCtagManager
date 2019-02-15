package com.gp.nfctagmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TAG_REQUEST = 1;
    public static final int EDIT_TAG_REQUEST = 2;

    private TagViewModel tagViewModel;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        FloatingActionButton buttonAddTag = findViewById(R.id.button_add_tag);
        buttonAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditTagActivity.class);
                startActivityForResult(intent, ADD_TAG_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TagAdapter adapter = new TagAdapter();
        recyclerView.setAdapter(adapter);

        tagViewModel = ViewModelProviders.of(this).get(TagViewModel.class);
        tagViewModel.getAllTags().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(@Nullable List<Tag> tags) {
                adapter.setTags(tags);
            }
        });

        adapter.setOnItemClickListener(new TagAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Tag tag) {
                Intent intent = new Intent(MainActivity.this, AddEditTagActivity.class);
                intent.putExtra(AddEditTagActivity.EXTRA_ID, tag.getId());
                intent.putExtra(AddEditTagActivity.EXTRA_NAME, tag.getName());
                intent.putExtra(AddEditTagActivity.EXTRA_TAG, tag.getTagCode());
                startActivityForResult(intent, EDIT_TAG_REQUEST);
            }
        });
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TAG_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditTagActivity.EXTRA_NAME);
            String tagId = data.getStringExtra(AddEditTagActivity.EXTRA_TAG);

            Tag tag = new Tag(name, tagId);
            tagViewModel.insert(tag);

            Toast.makeText(this, "Tag saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TAG_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTagActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Tag can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditTagActivity.EXTRA_NAME);
            String tagId = data.getStringExtra(AddEditTagActivity.EXTRA_TAG);

            Tag tag = new Tag(name, tagId);
            tag.setId(id);
            tagViewModel.update(tag);

            Toast.makeText(this, "Tag updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tag not saved", Toast.LENGTH_SHORT).show();
        }
    }

    //add items to the drop down menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //options for the drop down menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_tags:
                tagViewModel.deleteAllTags();
                Toast.makeText(this, "All tags deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
