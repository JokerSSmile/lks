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

    public void SaveJSFile(FileOutputStream fos, String strFile, String nameFile){
        try {
            fos.write(strFile.getBytes());
            //Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {
            //
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){
                //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String OpenJS(FileInputStream fin) {
        String text = null;
        try {

            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            text = new String(bytes);
        }
        catch(IOException ex){
            //Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            try{
                if(fin!=null) {
                    fin.close();
                }
            }
            catch(IOException ex){
                //Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return text;
    }
}
