package com.example.to_do.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.to_do.Database.Task;
import com.example.to_do.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class EditTaskDialog extends AppCompatDialogFragment {

    private TextInputLayout textInputLayout_title, textInputLayout_description, textInputLayout_date, textInputLayout_time;
    private MaterialButton materialButton_edit;

    private ImageButton imageButton_delete, imageButton_edit;
    private CheckBox checkBox;

    private EditTaskDialogListener listener;

    private Task task;

    public EditTaskDialog() {}

    // function to take data from activity to dialog
    public void setTask(Task task) {
        this.task = task;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog, null);
        builder.setView(view);

        // get views from dialog
        textInputLayout_title = view.findViewById(R.id.tf_task_title);
        textInputLayout_description = view.findViewById(R.id.tf_task_description);
        textInputLayout_date = view.findViewById(R.id.tf_task_date);
        textInputLayout_time = view.findViewById(R.id.tf_task_time);
        materialButton_edit = view.findViewById(R.id.btn_edit_task);
        imageButton_delete = view.findViewById(R.id.ib_delete_task);
        imageButton_edit = view.findViewById(R.id.ib_edit_task);
        checkBox = view.findViewById(R.id.cb_status);

        textInputLayout_title.getEditText().setText(task.getTitle());
        textInputLayout_description.getEditText().setText(task.getDesctiption());
        textInputLayout_date.getEditText().setText(task.getDate().toString());
        textInputLayout_time.getEditText().setText(task.getTime().toString());
        checkBox.setChecked(task.getStatus());

        imageButton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                materialButton_edit.setVisibility(View.VISIBLE);

                textInputLayout_title.getEditText().setEnabled(true);
                textInputLayout_description.getEditText().setEnabled(true);
                textInputLayout_date.getEditText().setEnabled(true);
                textInputLayout_time.getEditText().setEnabled(true);

                textInputLayout_title.getEditText().setFocusable(true);
                textInputLayout_description.getEditText().setFocusable(true);
                textInputLayout_date.getEditText().setFocusable(true);
                textInputLayout_time.getEditText().setFocusable(true);

                textInputLayout_title.getEditText().setFocusableInTouchMode(true);
                textInputLayout_description.getEditText().setFocusableInTouchMode(true);
                textInputLayout_date.getEditText().setFocusableInTouchMode(true);
                textInputLayout_time.getEditText().setFocusableInTouchMode(true);

                textInputLayout_title.getEditText().setCursorVisible(true);
                textInputLayout_description.getEditText().setCursorVisible(true);

                checkBox.setClickable(true);
            }
        });

        imageButton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to delete this task?");

                builder.setPositiveButton("Yes, Delete Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listener.onEditTask(task, "delete");
                        dismiss();

                    }
                });

                builder.setNegativeButton("No, Discard", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        materialButton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = textInputLayout_title.getEditText().getText().toString();
                String description = textInputLayout_description.getEditText().getText().toString();
                String date = textInputLayout_date.getEditText().getText().toString();
                String time = textInputLayout_time.getEditText().getText().toString();
                boolean status = checkBox.isChecked();

                // check if all new data is empty or not
                if (!title.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
                    task.setTitle(title);
                    task.setDesctiption(description);
                    task.setDate(date);
                    task.setTime(time);
                    task.setStatus(status);

                    listener.onEditTask(task, "edit");
                    dismiss();
                }else
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();

            }
        });

        textInputLayout_date.getEditText().setOnClickListener(v1 -> {
            MyDateTimePicker.showDatePickerDialog(getActivity(), textInputLayout_date);
        });

        textInputLayout_time.getEditText().setOnClickListener(v1 -> {
            MyDateTimePicker.showTimePickerDialog(getActivity(), textInputLayout_time);
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (EditTaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement EditTaskDialogListener");
        }
    }

    public interface EditTaskDialogListener {
        void onEditTask(Task task, String status);
    }

}
