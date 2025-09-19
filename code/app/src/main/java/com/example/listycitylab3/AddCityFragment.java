package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.CollationElementIterator;

public class AddCityFragment extends DialogFragment {
    private static final String ARG_CITY = "city";
    private static final String ARG_POSITION = "position";
    private City initialCity;
    private int position = -1;

    public static AddCityFragment newInstance(@Nullable City city, int position) {
        AddCityFragment f = new AddCityFragment();
        Bundle args = new Bundle();
        if (city != null) {
            args.putSerializable(ARG_CITY, city);
            args.putInt(ARG_POSITION, position); // pass position when editing
        }
        f.setArguments(args);
        return f;
    }

    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(City city, int position);
    }
    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);


        if (getArguments() != null) {
            initialCity = (City) getArguments().getSerializable(ARG_CITY);
            position = getArguments().getInt(ARG_POSITION, -1);
        }
        if (initialCity != null) {
            editCityName.setText(initialCity.getName());
            editProvinceName.setText(initialCity.getProvince());
        }

        boolean isEdit = (position != -1);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(isEdit ? "Edit city" : "Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Ok" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString().trim();
                    String provinceName = editProvinceName.getText().toString().trim();
                    City updated = new City(cityName, provinceName);

                    if (isEdit) {
                        listener.updateCity(updated, position);
                    } else {
                        listener.addCity(updated);
                    }
                })
                .create();
    }
}