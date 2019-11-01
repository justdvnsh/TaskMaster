package com.example.taskmaster.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.R;
import com.example.taskmaster.data.notesContract;
import com.example.taskmaster.data.todoContract;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.todoViewHolder> {
    private Cursor mCursor;

    final private ListItemOnClickListener mOnCLickListener;

    public interface ListItemOnClickListener {
        void onClickTodo(HashMap<String ,String> todo);
    }

    public todoAdapter(ListItemOnClickListener listener, Cursor cursor) {
        mOnCLickListener = listener;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public todoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.todo_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        todoAdapter.todoViewHolder viewHolder = new todoAdapter.todoViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull todoViewHolder holder, int position) {
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

    public class todoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTodos;

        public todoViewHolder(View view) {
            super(view);

            mTodos = (TextView) view.findViewById(R.id.todos);
            mTodos.setOnClickListener(this);
        }

        public void bind(int position) {

            if (!mCursor.moveToPosition(position)) {
                return;
            }

            String body = mCursor.getString(mCursor.getColumnIndex(todoContract.todoEntry.COLUMN_BODY));
            String priority = mCursor.getString(mCursor.getColumnIndex(todoContract.todoEntry.COLUMN_PRIORITY));
            long id = mCursor.getLong(mCursor.getColumnIndex(todoContract.todoEntry._ID));
            itemView.setTag(id);

            mTodos.setText(body);
            mTodos.append("\n" + priority);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
        }
    }
}
