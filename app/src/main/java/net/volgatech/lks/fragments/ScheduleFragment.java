package net.volgatech.lks.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.volgatech.lks.Adapter.ScheduleAdapter;
import net.volgatech.lks.Network.HttpHandler;
import net.volgatech.lks.Pojo.Schedule;
import net.volgatech.lks.R;
import net.volgatech.lks.activity.MainActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class ScheduleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener {
    SwipeRefreshLayout swipeLayout;
    Spinner spinner;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private  String nameFile = "Schedule.js";
    private int numItem = 0;
    private String[] url = {"http://www.mocky.io/v2/5860d1490f000018309c9aa9",
                            "http://www.mocky.io/v2/5860cca30f0000812f9c9aa2",
                            "http://www.mocky.io/v2/5860cd240f0000932f9c9aa3",
                            "http://www.mocky.io/v2/5860ce280f0000b92f9c9aa6",
                            "http://www.mocky.io/v2/5860ce930f0000bb2f9c9aa7"};
    private Schedule schedule;
    private ScheduleAdapter scheduleAdapter;

    private ArrayList<HashMap<String, String>> scheduleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.f_schedule_fragment, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.schedule_page_activity);
        swipeLayout.setOnRefreshListener(this);
        spinner = (Spinner) view.findViewById(R.id.spinner_schedule);
        String[] strings = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница"};
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.p_spinner, strings);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(R.layout.p_spinner_list_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != numItem) {
            numItem = i;
            new GetContacts().execute();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        private FileOutputStream fos;
        private FileInputStream fin;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!swipeLayout.isRefreshing()) {
                // Showing progress dialog
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Загрузка...");
                pDialog.setCancelable(false);
                pDialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = httpHandler.makeServiceCall(url[numItem]);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr == null){
                try {
                    fin = getActivity().openFileInput(nameFile);
                    jsonStr = httpHandler.OpenJS(fin);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    fos =  getActivity().openFileOutput(nameFile, MODE_PRIVATE);
                    httpHandler.SaveJSFile(fos, jsonStr);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            //Parsing JSON file
            if (jsonStr != null) {
                Gson gson = new Gson();
                schedule = gson.fromJson(jsonStr, Schedule.class);
                scheduleAdapter = new ScheduleAdapter();
                scheduleList = scheduleAdapter.GetScheduleList(schedule);
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


        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Dismiss the progress dialog
            if (pDialog.isShowing() && (!swipeLayout.isRefreshing()))
                pDialog.dismiss();
            //Updating parsed JSON data into ListView
            ListView lv = (ListView) getActivity().findViewById(R.id.schedule_item_list);
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(),  scheduleList,
                    R.layout.p_list_item_schedule, new String[]{"time", "title", "teacher", "location"},
                    new int[]{R.id.time_schedule, R.id.title_schedule, R.id.teacher_schedule, R.id.location_schedule})
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    View v = super.getView(position, convertView, parent);
                    if (scheduleList.get(position).get("color") == "red") {
                        v.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                    if (scheduleList.get(position).get("color") == "blue") {
                        v.setBackgroundColor(getResources().getColor(R.color.blue));
                    }
                    if (scheduleList.get(position).get("color") == "no") {
                        v.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    return v;
                }
            };
            if (scheduleList != null) {
                lv.setAdapter(adapter);
            }

        }
    }
}
