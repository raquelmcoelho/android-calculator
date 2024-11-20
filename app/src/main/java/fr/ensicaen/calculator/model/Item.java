package fr.ensicaen.calculator.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import fr.ensicaen.calculator.view.MainActivity;
import fr.ensicaen.calculator.view.RssFragment;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public final String _title;
    public final String _description;
    public final String _date;
    public final byte[] _image;


    public Item(String title, String description, String date, byte[] image) {
        _title = title;
        _description = description;
        _date = date;
        _image = image;
    }

    public void displayItem (RssFragment rssFragment) {
        rssFragment.resetDisplay(_title, _date, getImage(_image), _description);
    }
    private Bitmap getImage(byte[] img) {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }


}