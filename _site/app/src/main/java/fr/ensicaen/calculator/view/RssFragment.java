package fr.ensicaen.calculator.view;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.ensicaen.calculator.R;
import fr.ensicaen.calculator.databinding.FragmentRssBinding;
import fr.ensicaen.calculator.model.MyRSSsaxHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RssFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RssFragment extends Fragment {

    private FragmentRssBinding fragmentRssBinding;
    MyRSSsaxHandler  handler = new MyRSSsaxHandler();;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RssFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RssFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RssFragment newInstance(String param1, String param2) {
        RssFragment fragment = new RssFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRssBinding = FragmentRssBinding.inflate(getLayoutInflater(), container, false);

        processRSS();

        fragmentRssBinding.buttonPrev.setOnClickListener((View v) -> {
            handler.getPreviousItem().displayItem(this);
        } );
        fragmentRssBinding.buttonNext.setOnClickListener((View v) -> {
            handler.getNextItem().displayItem(this);
        } );

        return fragmentRssBinding.getRoot();
    }


    public void resetDisplay(String title, String date, Bitmap image, String description) {
        fragmentRssBinding.imageTitle.setText(title);
        fragmentRssBinding.imageDate.setText(date);
        fragmentRssBinding.imageDisplay.setImageBitmap(image);
        fragmentRssBinding.imageDescription.setText(description);
    }

    public void processRSS() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        service.execute( () -> {
            handler.getDatabase(getContext());

            if (((MainActivity) requireActivity()).isOnline()) {
                handler.setUrl("https://www.lemonde.fr/international/rss_full.xml");
                handler.processFeed();
            }
            handler.extractDb();

            mainThreadHandler.post(() -> {
                handler.getNextItem().displayItem(this);
                fragmentRssBinding.buttonPrev.setClickable(true);
                fragmentRssBinding.buttonNext.setClickable(true);
                fragmentRssBinding.buttonNext.setVisibility(View.VISIBLE);
                fragmentRssBinding.buttonPrev.setVisibility(View.VISIBLE);

            });
        });

    }

}