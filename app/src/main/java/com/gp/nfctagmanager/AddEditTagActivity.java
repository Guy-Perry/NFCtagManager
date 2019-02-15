package com.gp.nfctagmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditTagActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.gp.nfctagmanager.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.gp.nfctagmanager.EXTRA_NAME";
    public static final String EXTRA_TAG =
            "com.gp.nfctagmanager.EXTRA_TAG";

    public static final String EXTRA_DELETE =
            "com.gp.nfctagmanager.EXTRA_DELETE";

    private EditText editTextName;
    private EditText editTag;
    private FloatingActionButton deleteTagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        editTextName = findViewById(R.id.edit_text_name);
        editTag = findViewById(R.id.add_tag);
        deleteTagButton = findViewById(R.id.button_delete_tag);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Tag");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTag.setText(intent.getStringExtra(EXTRA_TAG));
            deleteTagButton.show();
            deleteTagButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(AddEditTagActivity.this)
                            .setTitle("Title")
                            .setMessage("Are you sure you want to delete this tag")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    deleteTag();
                                    Toast.makeText(AddEditTagActivity.this, "Tag deleted", Toast.LENGTH_SHORT).show();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                    //Toast.makeText(this, "All tags deleted", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            setTitle("Add Tag");
            deleteTagButton.hide();
        }
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

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    public void deleteTag() {
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, "[deleted]");
        data.putExtra(EXTRA_TAG, "[deleted]");

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        data.putExtra(EXTRA_DELETE, true);
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
