package fr.ensicaen.calculator.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.ensicaen.calculator.databinding.FragmentSettingsBinding;

import android.widget.RadioButton;

public class SettingsFragment extends Fragment {


    private FragmentSettingsBinding fragmentSettingsBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(getLayoutInflater(), container, false);

        fragmentSettingsBinding.btnLanguage.setOnClickListener(v -> changeLanguage());
        fragmentSettingsBinding.btnTheme.setOnClickListener(v -> changeTheme());

        return fragmentSettingsBinding.getRoot();
    }


    void changeLanguage() {
        System.out.println("changing language");
        RadioButton radioButton = requireActivity().findViewById(fragmentSettingsBinding.radioBtnLanguage.getCheckedRadioButtonId());
        ((MainActivity) requireActivity()).changeLanguage(radioButton.getContentDescription().toString());

    }

    void changeTheme() {
        System.out.println("changing theme");
        ((MainActivity) requireActivity()).changeTheme();
    }
}