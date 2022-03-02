package com.tatvasoftassignment.myapplication.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tatvasoftassignment.myapplication.Adapter.RecyclerViewAdapter;
import com.tatvasoftassignment.myapplication.Database.DatabaseHelper;
import com.tatvasoftassignment.myapplication.Model.StudentData;
import com.tatvasoftassignment.myapplication.R;
import com.tatvasoftassignment.myapplication.Utils.Constant;
import com.tatvasoftassignment.myapplication.databinding.FragmentStudentListBinding;

import java.util.ArrayList;
import java.util.Objects;


public class StudentList extends Fragment {
    private FragmentStudentListBinding binding;
    DatabaseHelper db;
    private final ArrayList<StudentData> data = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    public StudentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentListBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.student_list);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapterRecyclerView();
        createDataList();

        binding.fabAdd.setOnClickListener(v -> {
            StudentForm fragment = new StudentForm();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constant.addData, true);
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainActivity, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    public void createDataList() {
        Cursor getData = db.getData();

        if (getData.getCount() == 0) {
            binding.txtMassage.setVisibility(View.VISIBLE);

        } else {
            binding.txtMassage.setVisibility(View.INVISIBLE);

            while (getData.moveToNext()) {

                data.add(new StudentData(getData.getInt(0), getData.getString(1), getData.getString(2), getData.getString(3), getData.getString(4), getData.getString(5), getData.getString(6), getData.getString(7), getData.getString(8)));
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void setAdapterRecyclerView() {

        adapter = new RecyclerViewAdapter(data, getContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        setAdapterRecyclerView();
        createDataList();
    }

}