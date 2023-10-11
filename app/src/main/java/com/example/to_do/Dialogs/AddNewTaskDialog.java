package com.example.to_do.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.to_do.Database.Task;
import com.example.to_do.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class AddNewTaskDialog extends AppCompatDialogFragment {

    private TextInputLayout textInputLayout_title, textInputLayout_description,
            textInputLayout_date, textInputLayout_time;

    private MaterialButton materialButton_add;
    private AddNewTaskDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog, null);
        builder.setView(view);

        textInputLayout_title = view.findViewById(R.id.tf_task_title);
        textInputLayout_description = view.findViewById(R.id.tf_task_description);
        textInputLayout_date = view.findViewById(R.id.tf_task_date);
        textInputLayout_time = view.findViewById(R.id.tf_task_time);
        materialButton_add = view.findViewById(R.id.btn_add_task);

        textInputLayout_date.getEditText().setOnClickListener(v1 -> {
            MyDateTimePicker.showDatePickerDialog(getActivity(), textInputLayout_date);
        });

        textInputLayout_time.getEditText().setOnClickListener(v1 -> {
            MyDateTimePicker.showTimePickerDialog(getActivity(), textInputLayout_time);
        });

        materialButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = textInputLayout_title.getEditText().getText().toString();
                String description = textInputLayout_description.getEditText().getText().toString();
                String date = textInputLayout_date.getEditText().getText().toString();
                String time = textInputLayout_time.getEditText().getText().toString();

                Task task = new Task(title, description, date, time, false);
                if (!title.isEmpty() && !date.isEmpty() && !time.isEmpty()){
                    listener.onAddNewTask(task);
                    dismiss();
                }else
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();


            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddNewTaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement AddNewTaskDialogListener");
        }
    }

    public interface AddNewTaskDialogListener {
        void onAddNewTask(Task task);
    }

}
