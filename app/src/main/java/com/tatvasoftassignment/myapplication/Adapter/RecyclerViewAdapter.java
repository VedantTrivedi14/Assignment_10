package com.tatvasoftassignment.myapplication.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoftassignment.myapplication.Database.DatabaseHelper;
import com.tatvasoftassignment.myapplication.Fragment.StudentForm;
import com.tatvasoftassignment.myapplication.Model.StudentData;
import com.tatvasoftassignment.myapplication.R;
import com.tatvasoftassignment.myapplication.Utils.Constant;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    Context ctx;
    ArrayList<StudentData> studentList;

    public RecyclerViewAdapter(ArrayList<StudentData> studentList, Context ctx) {
        this.ctx = ctx;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(ctx).inflate(R.layout.list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        StudentData data = studentList.get(position);
        holder.txtName.setText(data.getName());
        holder.txtEmail.setText(data.getEmail());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtEmail;
        ImageButton imgBtnMenu;
        DatabaseHelper db;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            imgBtnMenu = itemView.findViewById(R.id.imgBtnMenu);
            db = new DatabaseHelper(ctx);
            imgBtnMenu.setOnClickListener(v -> {
                int dataId = studentList.get(getAdapterPosition()).getId();
                PopupMenu popupMenu = new PopupMenu(ctx, v);
                popupMenu.getMenuInflater().inflate(R.menu.update_delet_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {

                    switch (item.getItemId()) {
                        case R.id.miUpdate:
                            StudentForm fragment = new StudentForm();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(Constant.addData, false);
                            bundle.putInt(Constant.updateData, studentList.get(getAdapterPosition()).getId());
                            fragment.setArguments(bundle);
                            ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.mainActivity, fragment)
                                    .addToBackStack(null).commit();
                            notifyDataSetChanged();
                            Toast.makeText(ctx, R.string.edit, Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.miDelete:
                            AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
                                    .setTitle(R.string.alert)
                                    .setMessage(R.string.delete_alert_msg)
                                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                                        db.deleteData(dataId);
                                        studentList.remove(dataId);
                                        notifyDataSetChanged();
                                        Toast.makeText(ctx, R.string.delete_done, Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton(R.string.no, (dialogInterface, i) -> {

                                    });
                            builder.create().show();
                            break;
                    }

                    return false;
                });
                popupMenu.show();
            });


        }

    }
}
