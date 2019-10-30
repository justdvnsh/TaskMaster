package com.example.taskmaster.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.todoViewHolder> {
    private ArrayList<HashMap<String, String>> todoData;

    final private ListItemOnClickListener mOnCLickListener;

    public interface ListItemOnClickListener {
        void onClickTodo(HashMap<String ,String> todo);
    }

    public todoAdapter(ListItemOnClickListener listener) {
        mOnCLickListener = listener;
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
        if (null == todoData) return 0;
        return todoData.size();
    }

    public void setTodoData(ArrayList<HashMap<String, String>> todo) {
        todoData = todo;
        notifyDataSetChanged();
    }

    public class todoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTodos;

        public todoViewHolder(View view) {
            super(view);

            mTodos = (TextView) view.findViewById(R.id.todos);
            mTodos.setOnClickListener(this);
        }

        public void bind(int position) {
            mTodos.setText("Hello From Todos Adapter");
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
        }
    }
}
