package com.example.taskmaster.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.icu.text.CaseMap;
import android.location.GpsStatus;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.R;
import com.example.taskmaster.data.notesContract;

import java.util.ArrayList;
import java.util.HashMap;

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.noteViewHolder>{
    private Cursor mCursor;

    final private ListItemOnClickListener mOnClickListener;

    public interface ListItemOnClickListener {
        void onClickNotes(HashMap<String, String> note);
    }

    public noteAdapter(ListItemOnClickListener listener, Cursor cursor) {
        mOnClickListener = listener;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public noteAdapter.noteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.note_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        noteViewHolder viewHolder = new noteViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull noteAdapter.noteViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    public class noteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mNotes;

        public noteViewHolder(@NonNull View itemView) {
            super(itemView);

            mNotes = (TextView) itemView.findViewById(R.id.notes);
            mNotes.setOnClickListener(this);
        }

        public void bind(int position) {

            if (!mCursor.moveToPosition(position)) {
                return ;
            }

            String title = mCursor.getString(mCursor.getColumnIndex(notesContract.notesEntry.COLUMN_TITLE));
            String body = mCursor.getString(mCursor.getColumnIndex(notesContract.notesEntry.COLUMN_BODY));

            mNotes.setText(title);
            mNotes.append(" \n " + body);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
//            mOnClickListener.onClickNotes();
        }
    }
}
