package net.volgatech.lks.fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import net.volgatech.lks.Adapter.StudentAdapter;
import net.volgatech.lks.JsonUtility.JsonParser;
import net.volgatech.lks.Network.HttpHandler;
import net.volgatech.lks.Pojo.Student;
import net.volgatech.lks.R;
import net.volgatech.lks.activity.MainActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class InfoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeLayout;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static final String url = "http://www.mocky.io/v2/58469291110000832cf3cba5";
    private ArrayList<HashMap<String, String>> contactList;
    private JsonParser jsonParser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.info_fragment, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.new_page_activity);
        swipeLayout.setOnRefreshListener(this);
        contactList = new ArrayList<>();
        new GetContacts().execute();
        return view;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        Log.e(TAG, "Swipe done");
        new GetContacts().execute();
        swipeLayout.setRefreshing(false);

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        private FileOutputStream fos;
        private FileInputStream fin;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!swipeLayout.isRefreshing()){
                // Showing progress dialog
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Загрузка...");
                pDialog.setCancelable(false);
                pDialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();
            JsonParser jsonParser = new JsonParser();
            // Making a request to url and getting response
            String jsonStr = httpHandler.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr == null){
                try {
                    fin = getActivity().openFileInput("5.js");
                    jsonStr = httpHandler.OpenJS(fin);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    fos =  getActivity().openFileOutput("5.js", MODE_PRIVATE);
                    httpHandler.SaveJSFile(fos, jsonStr);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (jsonStr != null && jsonStr != "") {
                Gson gson = new Gson();
                Student student = gson.fromJson(jsonStr, Student.class);
                StudentAdapter studentAdapter = new StudentAdapter();
                contactList = studentAdapter.Convert(student);

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing() && (!swipeLayout.isRefreshing()))
                pDialog.dismiss();

            //Updating parsed JSON data into ListView
            ListView lv = (ListView) getActivity().findViewById(R.id.list);
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(),  contactList,
                    R.layout.list_item_student, new String[]{"header", "value"},
                    new int[]{R.id.header, R.id.value});

            lv.setAdapter(adapter);
        }
    }
}
