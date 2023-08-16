package com.example.notewave.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notewave.R;
import com.example.notewave.activities.NoteDetailActivity;
import com.example.notewave.models.Note;
import com.example.notewave.utils.DatabaseHelper;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<Note> {
    private DatabaseHelper databaseHelper;
    private Context context;

    public NotesAdapter(@NonNull Context context, int resource, @NonNull List<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_note, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.noteTitleTextView);
        TextView dateTextView = convertView.findViewById(R.id.noteDateTextView);
        Button viewButton = convertView.findViewById(R.id.viewNoteButton);
        Button deleteButton = convertView.findViewById(R.id.deleteNoteButton);

        Note note = getItem(position);

        titleTextView.setText(note.getTitle());
        dateTextView.setText(note.getDate());

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteDetailActivity.class);
                intent.putExtra("selectedTitle", note.getTitle());
                context.startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click
                if (note != null) {
                    databaseHelper.deleteNote(note.getId());
                    remove(note);
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }
}
