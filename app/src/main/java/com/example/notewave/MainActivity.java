package com.example.notewave;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notewave.activities.AddNoteActivity;
import com.example.notewave.activities.NoteDetailActivity;
import com.example.notewave.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView noteListView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteListView = findViewById(R.id.noteListView);
        databaseHelper = new DatabaseHelper(this);

        loadNotes();

        Button addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTitle = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                intent.putExtra("selectedTitle", selectedTitle);
                startActivity(intent);
            }
        });
    }

    private void loadNotes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TITLE};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NOTES,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<String> noteTitles = new ArrayList<>();
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
            noteTitles.add(title);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteTitles);
        noteListView.setAdapter(adapter);
        db.close();
    }
}
