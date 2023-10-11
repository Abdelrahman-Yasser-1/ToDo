package com.example.to_do.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.Database.Task;
import com.example.to_do.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> data;
    private static Context context;
    private static OnRecyclerViewItemClickListener itemListener;

    public TaskAdapter(Context context, ArrayList<Task> data, OnRecyclerViewItemClickListener itemListener) {
        this.data = data;
        this.context = context;
        this.itemListener = itemListener;
    }

    public ArrayList<Task> getData() {
        return data;
    }

    public void setData(ArrayList<Task> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = data.get(position);

        holder.title.setText(task.getTitle());
        if (!task.getDesctiption().isEmpty()) {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(task.getDesctiption());
        } else
            holder.description.setVisibility(View.GONE);

        holder.time.setText(task.getTime());

        if (position == 0) {
            holder.v_top.setVisibility(View.INVISIBLE);
            if (data.size() == 1)
                holder.v_bottom.setVisibility(View.INVISIBLE);
            else
                holder.v_bottom.setVisibility(View.VISIBLE);
        } else if (position == data.size() - 1)
            holder.v_bottom.setVisibility(View.INVISIBLE);
        else {
            holder.v_top.setVisibility(View.VISIBLE);
            holder.v_bottom.setVisibility(View.VISIBLE);
        }

        if (task.getStatus()) {
            holder.circle.setBackgroundResource(R.drawable.filled_circle);
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.taskColor));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.description.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.circle.setBackgroundResource(R.drawable.outlined_circle);
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.description.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, time, circle;
        LinearLayout linearLayout, ll_task;
        CardView cardView;

        View v_top, v_bottom;

        public TaskViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_title);
            description = view.findViewById(R.id.tv_description);

            linearLayout = view.findViewById(R.id.ll_task_content);

            time = view.findViewById(R.id.tv_time);

            v_top = view.findViewById(R.id.v_top);
            v_bottom = view.findViewById(R.id.v_bottom);
            circle = view.findViewById(R.id.tv_circle);

            cardView = view.findViewById(R.id.cv_task);
            ll_task = view.findViewById(R.id.ll_task);


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
