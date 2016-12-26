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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.volgatech.lks.Adapter.AchievementsAdapter;
import net.volgatech.lks.Network.HttpHandler;
import net.volgatech.lks.Pojo.Achievement;
import net.volgatech.lks.R;
import net.volgatech.lks.activity.MainActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AchievementsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeLayout;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ArrayList<HashMap<String, String>> achievementsList;
    private ArrayList<HashMap<String, String>> achievementsSumMarkList;
    private static final String url = "http://www.mocky.io/v2/585fe91c0f0000280f9c9a1e";
    private static final String nameFile = "Achievements.js";
    private TextView sumMark;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        pDialog = new ProgressDialog(getActivity());
        View view = inflater.inflate(R.layout.f_achievements_fragment, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.achievements_page_activity);
        swipeLayout.setOnRefreshListener(this);
        achievementsList = new ArrayList<>();
        sumMark = (TextView) view.findViewById(R.id.achievements_sum_mark);
        new GetContacts().execute();
        return view;
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
            if (jsonStr != null && jsonStr != "") {
                Gson gson = new Gson();
                Achievement achievement = gson.fromJson(jsonStr, Achievement.class);
                AchievementsAdapter achievementsAdapter = new AchievementsAdapter();
                achievementsList = achievementsAdapter.GetAchievementsList(achievement);
                achievementsSumMarkList = achievementsAdapter.GetSumMark(achievement);
                //  String qw = achievementsAdapter.GetSumMark(achievement);
                //sumMark

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
            //Dismiss the progress dialog
            if (pDialog.isShowing() && (!swipeLayout.isRefreshing()))
                pDialog.dismiss();

            if ((achievementsList != null) && (achievementsSumMarkList != null)){
                ListView lv = (ListView) getActivity().findViewById(R.id.achievements_item_list);
                ListView lvSumMark = (ListView) getActivity().findViewById(R.id.achievements_list_sum_mark);

                ListAdapter adapter = new SimpleAdapter(
                        getActivity(),  achievementsList,
                        R.layout.p_list_item_achievements, new String[]{"criterion", "subcriterion", "mark", "date"},
                        new int[]{R.id.criterion_achievements, R.id.sub_criterion_achievements, R.id.mark_achievements, R.id.date_achievements});
                ListAdapter adapterSumMark = new SimpleAdapter(
                        getActivity(),  achievementsSumMarkList,
                        R.layout.p_list_sum_mark_achivmemts, new String[]{"sum"},
                        new int[]{R.id.achievements_sum_mark});

                lv.setAdapter(adapter);
                lvSumMark.setAdapter(adapterSumMark);
            }

        }
    }
}
