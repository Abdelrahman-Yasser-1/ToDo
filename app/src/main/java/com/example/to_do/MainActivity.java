package com.example.to_do;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.Adapter.OnRecyclerViewItemClickListener;
import com.example.to_do.Adapter.TaskAdapter;
import com.example.to_do.Database.Database;
import com.example.to_do.Database.Task;
import com.example.to_do.Dialogs.AddNewTaskDialog;
import com.example.to_do.Dialogs.EditTaskDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddNewTaskDialog.AddNewTaskDialogListener, EditTaskDialog.EditTaskDialogListener {

    TextView tv_pending_tasks;

    RecyclerView recyclerView;

    TaskAdapter taskAdapter;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton floatingActionButton;

    Database db;
    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Database.getInstance(this);

        db.taskDAO().getAllTask().observe(this, tasks -> {
            this.tasks = (ArrayList<Task>) tasks;
            taskAdapter.setData(this.tasks);
        });


        tv_pending_tasks = findViewById(R.id.tv_pending_tasks);

        //get live data getTaskCountByStatus from database
        db.taskDAO().getTaskCountByStatus(false).observe(this, pending -> {
            tv_pending_tasks.setText("Pending tasks: " + pending);
        });

        recyclerView = findViewById(R.id.rv_tasks);
        recyclerView.setVerticalScrollBarEnabled(true);

        taskAdapter = new TaskAdapter(this, tasks, new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openEditTaskDialog(tasks.get(position));
            }
        });

        recyclerView.setAdapter(taskAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        floatingActionButton = findViewById(R.id.fab_add_task);

        floatingActionButton.setOnClickListener(v -> {
            openAddNewTaskDialog();
        });
    }

    public void openAddNewTaskDialog() {
        AddNewTaskDialog addNewTaskDialog = new AddNewTaskDialog();
        addNewTaskDialog.show(getSupportFragmentManager(), "Add new task dialog");
    }

    @Override
    public void onAddNewTask(Task task) {
        db.taskDAO().insertTask(task);
    }

    public void openEditTaskDialog(Task task) {
        EditTaskDialog editTaskDialog = new EditTaskDialog();
        editTaskDialog.setTask(task);
        editTaskDialog.show(getSupportFragmentManager(), "Add new task dialog");
    }

    @Override
    public void onEditTask(Task task, String status) {
        if (status == "edit")
            db.taskDAO().updateTask(task);
        else
            db.taskDAO().deleteTask(task);
    }

}