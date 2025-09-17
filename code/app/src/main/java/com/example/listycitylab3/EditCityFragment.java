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

public class EditCityFragment extends DialogFragment {

    interface EditCityDialogListener {
        void editCity(int position, City edited);
    }

    private EditCityDialogListener listener;

    public static EditCityFragment newInstance(City city, int position){
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        args.putInt("position", position);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener){
            listener = (EditCityDialogListener) context;
        }
        else{
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        Bundle args = getArguments();
        City city = null;
        int position;
        if (args != null){
            city = (City) args.getSerializable("city");
            position = args.getInt("position", -1);
        } else {
            position = -1;
        }

        if (city != null){
            editCityName.setText((city.getName()));
            editProvinceName.setText(city.getProvince());
        }

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = editCityName.getText().toString();
                    String newProvince = editProvinceName.getText().toString();

                    City edited = new City(newName, newProvince);

                    if (listener != null){
                        listener.editCity(position, edited);
                    }
                })
                .create();
    }
}
