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


public class RssFragment extends Fragment {

    private FragmentRssBinding fragmentRssBinding;
    MyRSSsaxHandler  handler = new MyRSSsaxHandler();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                fragmentRssBinding.linearLayout.setVisibility(View.VISIBLE);
                fragmentRssBinding.buttonNext.setVisibility(View.VISIBLE);
                fragmentRssBinding.buttonPrev.setVisibility(View.VISIBLE);
            });
        });
    }
}