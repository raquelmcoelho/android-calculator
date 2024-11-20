package fr.ensicaen.calculator.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.ensicaen.calculator.databinding.FragmentSettingsBinding;
import java.util.Locale;
import java.util.Objects;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.RadioButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentSettingsBinding fragmentSettingsBinding;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(getLayoutInflater(), container, false);

        fragmentSettingsBinding.btnLanguage.setOnClickListener(v -> changeLanguage("en"));
        fragmentSettingsBinding.btnTheme.setOnClickListener(v -> changeTheme());

        return fragmentSettingsBinding.getRoot();
    }


    void changeLanguage() {
        System.out.println("changing language");
        RadioButton radioButton = (RadioButton) requireActivity().findViewById(fragmentSettingsBinding.radioBtnLanguage.getCheckedRadioButtonId());
        ((MainActivity) requireActivity()).changeLanguage(radioButton.getContentDescription().toString());

    }

    void changeTheme() {
        System.out.println("changing theme");

    }
}