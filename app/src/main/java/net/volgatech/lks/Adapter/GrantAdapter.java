package net.volgatech.lks.Adapter;

import net.volgatech.lks.Pojo.Grant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Ultim on 23.12.2016.
 */

public class GrantAdapter {
    private ArrayList<HashMap<String, String>> grantList;

    public GrantAdapter() {
        grantList = new ArrayList<>();
    }

    public ArrayList<HashMap<String, String>> GetGrantArray(Grant grant){
        grantList.clear();
        for (int i = 0; i < grant.GrantItem.length; i++)
        {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", grant.GrantItem[i].Name);

            String timestamp = grant.GrantItem[i].DateStart.replace("/Date(", "").replace(")/", "");
            Date createdOn = new Date(Long.parseLong(timestamp));
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
            String formattedDate = sdf.format(createdOn);
            hashMap.put("date",  formattedDate);

            hashMap.put("cost",  grant.GrantItem[i].Cost);
            grantList.add(hashMap);
        }
        return grantList;
    }
}
