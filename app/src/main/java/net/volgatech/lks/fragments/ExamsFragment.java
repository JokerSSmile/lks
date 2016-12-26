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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import net.volgatech.lks.Adapter.ExamAdapter;
import net.volgatech.lks.JsonUtility.JsonParser;
import net.volgatech.lks.Network.HttpHandler;
import net.volgatech.lks.Pojo.Exam;
import net.volgatech.lks.R;
import net.volgatech.lks.activity.MainActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ExamsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener {

    SwipeRefreshLayout swipeLayout;
    Spinner spinner;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static final String url = "http://www.mocky.io/v2/584704103f0000090dfe6939";
    private static final String nameFile = "Exam.js";
    private JsonParser jsonParser;
    private ExamAdapter examAdapter;
    private ArrayList<HashMap<String, String>> examList;
    private Exam exams;
    private int numItem = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.f_exams_fragment, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.exam_page_activity);
        swipeLayout.setOnRefreshListener(this);
        spinner = (Spinner) view.findViewById(R.id.spinner_exam);
        String[] strings = {"Текущий семестр", "1 Семестр", "2 Семестр", "3 Семестр", "4 Семестр"};
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.p_spinner, strings);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(R.layout.p_spinner_list_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        jsonParser = new JsonParser();
        examList = new ArrayList<>();
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
        Object item = adapterView.getItemAtPosition(i);
        if (i != numItem){
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
            String jsonStr = httpHandler.makeServiceCall(url);
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
            if (jsonStr != null) {
                Gson gson = new Gson();
                exams = gson.fromJson(jsonStr, Exam.class);
                int a = exams.countExam;
                examAdapter = new ExamAdapter();
                if (numItem == 0){
                    examList = examAdapter.GetCurrentSemester(exams);
                }else {
                    examList = examAdapter.GetNumberSemester(exams, numItem - 1);
                }



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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Dismiss the progress dialog
            if (pDialog.isShowing() && (!swipeLayout.isRefreshing()))
                pDialog.dismiss();
            //Updating parsed JSON data into ListView
            ListView lv = (ListView) getActivity().findViewById(R.id.exam_item_list);

            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), examList,
                    R.layout.p_list_item_exam, new String[]{"title", "date", "teacher", "location"},
                    new int[]{R.id.title_exam, R.id.date_exam, R.id.teacher_exam, R.id.location_exam});

            lv.setAdapter(adapter);
        }
    }

}
