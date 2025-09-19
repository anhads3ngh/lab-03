package com.example.listycity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    // Args for edit mode
    private static final String ARG_CITY_NAME = "arg_city_name";
    private static final String ARG_PROV = "arg_prov";
    private static final String ARG_POSITION = "arg_position";

    public interface AddCityDialogListener {
        void addCity(City city);
        void editCity(int position, City updatedCity);
    }

    private AddCityDialogListener listener;

    /** Call this for ADD */
    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    /** Call this for EDIT */
    public static AddCityFragment newInstanceForEdit(int position, City city) {
        AddCityFragment f = new AddCityFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putString(ARG_CITY_NAME, city.getName());
        b.putString(ARG_PROV, city.getProvince());
        f.setArguments(b);
        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new IllegalStateException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_city, null);
        EditText cityInput = view.findViewById(R.id.edit_text_city_text);
        EditText provInput = view.findViewById(R.id.edit_text_province_text);

        // Check if we're in EDIT mode (args present)
        Bundle args = getArguments();
        final boolean isEdit = (args != null && args.containsKey(ARG_POSITION));
        final int position = isEdit ? args.getInt(ARG_POSITION) : -1;

        if (isEdit) {
            cityInput.setText(args.getString(ARG_CITY_NAME, ""));
            provInput.setText(args.getString(ARG_PROV, ""));
            cityInput.setSelection(cityInput.getText().length());
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle(isEdit ? "Edit City" : "Add a city")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Save" : "Add", (dialog, which) -> {
                    String cityName = cityInput.getText().toString().trim();
                    String province = provInput.getText().toString().trim();
                    if (cityName.isEmpty() || province.isEmpty()) return;

                    if (isEdit) {
                        listener.editCity(position, new City(cityName, province));
                    } else {
                        listener.addCity(new City(cityName, province));
                    }
                })
                .create();
    }
}


