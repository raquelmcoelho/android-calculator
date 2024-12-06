package fr.ensicaen.calculator.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MyRSSsaxHandler extends DefaultHandler {


    private String url = null ;// l'URL du flux RSS à parser
    // Ensemble de drapeau permettant de savoir où en est le parseur dans le flux XML
    private boolean inTitle = false ;
    private boolean inDescription = false ;
    private boolean inItem = false ;
    private boolean inDate = false ;
    // L'image référencée par l'attribut url du tag <enclosure>
    private Bitmap image = null ;
    private String imageURL = null ;
    // Le titre, la description et la date extraits du flux RSS
    private StringBuffer title = new StringBuffer();
    private StringBuffer description = new StringBuffer();
    private StringBuffer date = new StringBuffer();
    private int numItem = 0; // Le numéro de l'item à extraire du flux RSS
    private int numItemMax = - 1; // Le nombre total d’items dans le flux RSS

    private List<Item> items = null;
    private int currentItemIndex = -1;

    AppDatabase db;
    ItemDAO itemDAO;



    public void setUrl(String url){
        this.url= url;

    }

    public void processFeed(){
        try {
            numItem = 0; //A modifier pour visualiser un autre item
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(this);
            InputStream inputStream = new URL(url).openStream();
            itemDAO.delete();
            reader.parse(new InputSource(inputStream));
            numItemMax = numItem;

            items = itemDAO.getAll();
        }catch(Exception e) {
            Log.e("smb116rssview", "processFeed Exception" + e.getMessage());
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            inItem = true;
        } else if (qName.equalsIgnoreCase("title")) {
            inTitle = true;
            title.setLength(0); // Reset the title buffer
        } else if (qName.equalsIgnoreCase("description")) {
            inDescription = true;
            description.setLength(0); // Reset the description buffer
        } else if (qName.equalsIgnoreCase("pubDate")) {
            inDate = true;
            date.setLength(0); // Reset the date buffer
        } else if (qName.equalsIgnoreCase("media:content")) {
            imageURL = attributes.getValue("url");
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {

            inItem = false;

            // After reading the item, log the content or handle it as necessary
            Log.d("RSS", "Item " + numItem + " Title: " + title.toString());
            Log.d("RSS", "Item " + numItem + " Date: " + date.toString());
            Log.d("RSS", "Item " + numItem + " Description: " + description.toString());
            itemDAO.insert(new Item(title.toString(), description.toString(), date.toString(), getBitmap(imageURL)));
            numItem++;

            // You can call resetDisplay() here to update the UI after parsing
        } else if (qName.equalsIgnoreCase("title")) {
            inTitle = false;
        } else if (qName.equalsIgnoreCase("description")) {
            inDescription = false;
        } else if (qName.equalsIgnoreCase("pubDate")) {
            inDate = false;
        }
    }



    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String chars = new String(ch, start, length);
        if (inTitle) {
            title.append(chars);
        } else if (inDescription) {
            description.append(chars);
        } else if (inDate) {
            date.append(chars);
        }
    }

    public byte[] getBitmap(String imageURL) {
        try {
            URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                return connection.getInputStream().readAllBytes();
            }


        } catch (IOException e) {
            Log.e("RSS", "Error fetching image: " + e.getMessage());
            return null;
        }
        return null;
    }

    public Item getNextItem() {

        currentItemIndex = ( currentItemIndex + 1 ) % numItemMax;
        return items.get(currentItemIndex);
    }
    public Item getPreviousItem() {
        currentItemIndex = ( currentItemIndex -1+ numItemMax ) % numItemMax;
        return items.get(currentItemIndex);
    }

    public void extractDb() {
        items = itemDAO.getAll();
        numItemMax = items.size();
    }

    public void getDatabase(Context context) {
        db = AppDatabase.getInstance(context);
        itemDAO = db.itemDao();
    }
}


