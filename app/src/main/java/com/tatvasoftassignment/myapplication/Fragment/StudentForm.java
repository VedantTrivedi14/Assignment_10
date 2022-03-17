package com.tatvasoftassignment.myapplication.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tatvasoftassignment.myapplication.Database.DatabaseHelper;
import com.tatvasoftassignment.myapplication.Model.StudentData;
import com.tatvasoftassignment.myapplication.R;
import com.tatvasoftassignment.myapplication.Utils.Constant;
import com.tatvasoftassignment.myapplication.databinding.FragmentStudentFromBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class StudentForm extends Fragment {
    private FragmentStudentFromBinding binding;
    public static int set_day;
    public static int set_month;
    public static int set_year;
    private String language = "";
    private ArrayAdapter<String> bloodGroupAdapter;
    private boolean addData = true;
    private int id;
    private DatabaseHelper db;
    private StudentData data;
    private DatePickerDialog.OnDateSetListener datePicker;

    public StudentForm() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentFromBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        db = new DatabaseHelper(getContext());
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.student_form);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        set_country_bloodGroup();
        setDateOfBirth();


        assert getArguments() != null;
        if (!getArguments().getBoolean(Constant.addData)) {
            updateData();
        }

        binding.btnSave.setOnClickListener(v -> {
            if (isValid()) {

                if (addData) {

                    db.insertData(Objects.requireNonNull(binding.etName.getText()).toString(),
                            Objects.requireNonNull(binding.etEmail.getText()).toString(),
                            Objects.requireNonNull(binding.etPhone.getText()).toString(),
                            Objects.requireNonNull(binding.etAddress.getText()).toString(),
                            Objects.requireNonNull(binding.etDate.getText()).toString(),
                            binding.acTxtBloodGroup.getText().toString(),
                            binding.rbMale.isChecked() ? String.valueOf(R.string.male) : String.valueOf(R.string.female),
                            getLanguage());
                    Toast.makeText(getContext(), getString(R.string.added_successfully), Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivity, new StudentList())
                            .addToBackStack(null)
                            .commit();
                } else {
                    Boolean update = db.updateData(id,
                            Objects.requireNonNull(binding.etName.getText()).toString(),
                            Objects.requireNonNull(binding.etEmail.getText()).toString(),
                            Objects.requireNonNull(binding.etPhone.getText()).toString(),
                            Objects.requireNonNull(binding.etAddress.getText()).toString(),
                            Objects.requireNonNull(binding.etDate.getText()).toString(),
                            binding.acTxtBloodGroup.getText().toString(),
                            String.valueOf(binding.rbMale.isChecked() ? R.string.male : R.string.female),
                            getLanguage());
                    if (update) {
                        Toast.makeText(getContext(), getString(R.string.data_updated_successfully), Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainActivity, new StudentList())
                                .addToBackStack(null)
                                .commit();

                    }
                }
            }
        });


    }

    String getLanguage() {
        if (binding.checkEnglish.isChecked()) {
            language = getString(R.string.english) + "\n";
        } else {
            language += "";
        }
        if (binding.checkHindi.isChecked()) {
            language += getString(R.string.hindi) + "\n";
        } else {
            language += "";
        }
        if (binding.checkGujarati.isChecked()) {
            language += getString(R.string.gujarati) + "\n";
        } else {
            language += "";
        }
        return language;
    }

    private void set_country_bloodGroup() {
        String[] bg = getResources().getStringArray(R.array.blood_group_array);
        bloodGroupAdapter = new ArrayAdapter<>(getContext(), R.layout.layout_dropdown_item, bg);
        binding.acTxtBloodGroup.setAdapter(bloodGroupAdapter);

    }


    private void setDateOfBirth() {

        Calendar calendar = Calendar.getInstance();
        datePicker = (datePicker1, year, month, day) -> {
            set_year = year;
            Log.i("date", String.valueOf(set_year));
            set_month = month;
            Log.i("date", String.valueOf(set_month));
            set_day = day;
            Log.i("date", String.valueOf(set_day));
            calendar.set(Calendar.YEAR, set_year);
            calendar.set(Calendar.MONTH, set_month);
            calendar.set(Calendar.DAY_OF_MONTH, set_day);
            String birthdate = day + "/" + (month + 1) + "/" + year;
            binding.etDate.setText(birthdate);
        };
        binding.etDate.setOnClickListener(v -> {
            DatePickerDialog dpd = new DatePickerDialog(getContext(), datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dpd.show();
        });
    }


    @SuppressLint("UseCompatLoadingForColorStateLists")
    private Boolean isValid() {
        boolean valid = true;

        if (Objects.requireNonNull(binding.etName.getText()).toString().length() == 0) {
            Toast.makeText(getContext(), getString(R.string.Enter_Name), Toast.LENGTH_LONG).show();
            binding.etName.requestFocus();
            binding.etNameField.setHelperText(getString(R.string.require));
            binding.etNameField.setHelperTextColor(getResources().getColorStateList(R.color.red));
            valid = false;
        } else if (Objects.requireNonNull(binding.etEmail.getText()).toString().length() == 0) {
            Toast.makeText(getContext(), getString(R.string.Enter_Email), Toast.LENGTH_LONG).show();
            binding.etEmail.requestFocus();
            binding.etEmailField.setHelperText(getString(R.string.require));
            binding.etEmailField.setHelperTextColor(getResources().getColorStateList(R.color.red));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull(binding.etEmail.getText()).toString()).matches()) {
            Toast.makeText(getContext(), getString(R.string.invalidate_email), Toast.LENGTH_LONG).show();
            binding.etEmail.requestFocus();
            binding.etEmailField.setHelperText(getString(R.string.require));
            binding.etEmailField.setHelperTextColor(getResources().getColorStateList(R.color.red));
            valid = false;
        } else if (Objects.requireNonNull(binding.etDate.getText()).toString().length() == 0) {
            Toast.makeText(getContext(), getString(R.string.select_Date), Toast.LENGTH_LONG).show();
            binding.etDateField.setHelperText(getString(R.string.require));
            binding.etDateField.setHelperTextColor(getResources().getColorStateList(R.color.red));
            binding.etDate.requestFocus();
            valid = false;
        } else if (Objects.requireNonNull(binding.etAddress.getText()).toString().length() == 0) {
            Toast.makeText(getContext(), getString(R.string.Enter_Address), Toast.LENGTH_LONG).show();
            binding.etAddress.requestFocus();
            binding.etAddressField.setHelperText(getString(R.string.require));
            binding.etAddressField.setHelperTextColor(getResources().getColorStateList(R.color.red));
            valid = false;
        } else if (Objects.requireNonNull(binding.etPhone.getText()).toString().length() != 10) {
            Toast.makeText(getContext(), getString(R.string.Enter_Contact_Number), Toast.LENGTH_LONG).show();
            binding.etPhone.requestFocus();
            binding.etPhoneField.setHelperText(getString(R.string.require));
            binding.etPhoneField.setHelperTextColor(getResources().getColorStateList(R.color.red));
            valid = false;
        } else if (binding.acTxtBloodGroup.getText().toString().length() == 0) {
            Toast.makeText(getContext(), getString(R.string.select_country), Toast.LENGTH_LONG).show();
            binding.acTxtBloodGroup.requestFocus();
            binding.etBloodGroupField.setHelperText(getString(R.string.require));
            binding.etBloodGroupField.setHelperTextColor(getResources().getColorStateList(R.color.red));
            valid = false;
        } else if (!binding.checkEnglish.isChecked() && !binding.checkHindi.isChecked() && !binding.checkGujarati.isChecked()) {
            Toast.makeText(getContext(), getString(R.string.select_language), Toast.LENGTH_LONG).show();
            valid = false;
        } else if (!binding.rbMale.isChecked() && !binding.rbFemale.isChecked()) {
            Toast.makeText(getContext(), getString(R.string.select_gender), Toast.LENGTH_SHORT).show();
            binding.rgButton.requestFocus();
            valid = false;
        }


        return valid;
    }

    void updateData() {
        requireActivity().setTitle(getString(R.string.update_data_title));
        addData = false;
        assert getArguments() != null;
        id = getArguments().getInt(Constant.updateData);
        Cursor cursor = db.getDataById(id);

        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), getString(R.string.no_details_found), Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext()) {

            data = new StudentData(id, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
        }

        binding.btnSave.setText(R.string.update);
        binding.etName.setText(data.getName());
        binding.etEmail.setText(data.getEmail());
        binding.etPhone.setText(data.getContactNo());
        binding.etAddress.setText(data.getAddress());
        binding.etDate.setText(data.getBirthDate());

        binding.acTxtBloodGroup.setAdapter(bloodGroupAdapter);
        binding.acTxtBloodGroup.setText(data.getBloodGroup(), false);

        binding.checkEnglish.setChecked(data.getLanguage().contains(getString(R.string.english)));
        binding.checkHindi.setChecked(data.getLanguage().contains(getString(R.string.hindi)));
        binding.checkGujarati.setChecked(data.getLanguage().contains(getString(R.string.gujarati)));
        String gen = data.getGender();
        Log.i("gender", gen);
        if (data.getGender().equals(String.valueOf(R.string.male))) {
            binding.rbMale.setChecked(true);
        } else {
            binding.rbFemale.setChecked(true);
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(data.getBirthDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day1 = Integer.parseInt((String) android.text.format.DateFormat.format("dd", date));
        int month1 = Integer.parseInt((String) android.text.format.DateFormat.format("MM", date));
        int year1 = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", date));

        binding.etDate.setOnClickListener(v -> {
            DatePickerDialog dpd = new DatePickerDialog(getContext(), datePicker, year1, month1 - 1, day1);
            dpd.show();
        });
    }

}