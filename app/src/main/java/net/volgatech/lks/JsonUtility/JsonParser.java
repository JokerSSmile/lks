package net.volgatech.lks.JsonUtility;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ultim on 06.12.2016.
 */

public class JsonParser {
    public JsonParser() {
    }

    public <T> T Parsing(String strings, Class<T> tClass){
        try {
            Gson gson = new Gson();
            tClass = (Class<T>) gson.fromJson(strings, tClass);
            return (T) tClass;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }


}
