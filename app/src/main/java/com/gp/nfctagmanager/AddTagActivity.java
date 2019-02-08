package com.gp.nfctagmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddTagActivity extends AppCompatActivity {

    public static final String EXTRA_NAME =
            "com.gp.nfctagmanager.EXTRA_NAME";
    public static final String EXTRA_TAG =
            "com.gp.nfctagmanager.EXTRA_TAG";

    private EditText editTextName;
    private EditText editTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        editTextName = findViewById(R.id.edit_text_name);
        editTag = findViewById(R.id.add_tag);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Tag");
    }

    private void saveTag() {
        String name = editTextName.getText().toString();
        String tag = editTag.getText().toString();

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_TAG, tag);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_tag_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_tag:
                saveTag();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
