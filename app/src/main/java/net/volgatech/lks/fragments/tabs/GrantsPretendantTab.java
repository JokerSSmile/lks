package net.volgatech.lks.fragments.tabs;

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

import net.volgatech.lks.Adapter.GrantsPretendantAdapter;
import net.volgatech.lks.Network.HttpHandler;
import net.volgatech.lks.Pojo.GrantsPretendant;
import net.volgatech.lks.R;
import net.volgatech.lks.activity.MainActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class GrantsPretendantTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    SwipeRefreshLayout swipeLayout;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ArrayList<HashMap<String, String>> grantList;
    private static final String url = "http://www.mocky.io/v2/585d57fa1000008715501e48";
    private static final String nameFile = "PretendantGrant.js";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pDialog = new ProgressDialog(getActivity());
        View view = inflater.inflate(R.layout.t_grants_pretendant_tab, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.pretendant_grants_page_activity);
        swipeLayout.setOnRefreshListener(this);
        grantList = new ArrayList<>();
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
                GrantsPretendant grant = gson.fromJson(jsonStr, GrantsPretendant.class);
                GrantsPretendantAdapter grantAdapter = new GrantsPretendantAdapter();
                grantList = grantAdapter.GetGrantsPretendantArray(grant);

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
            // if (pDialog.isShowing() && (!swipeLayout.isRefreshing()))
            // pDialog.dismiss();

            ListView lv = (ListView) getActivity().findViewById(R.id.pretendant_grant_item_list);
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(),  grantList,
                    R.layout.p_list_item_pretendant_grant, new String[]{"title", "category", "date"},
                    new int[]{R.id.title_pretendant_grant, R.id.category_pretendant_grant, R.id.date_pretendant_grant});

            lv.setAdapter(adapter);
        }
    }
}