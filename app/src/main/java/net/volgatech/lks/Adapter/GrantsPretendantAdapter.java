package net.volgatech.lks.Adapter;

import net.volgatech.lks.Pojo.GrantsPretendant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Ultim on 23.12.2016.
 */

public class GrantsPretendantAdapter {

    private ArrayList<HashMap<String, String>> grantList;

    public GrantsPretendantAdapter(){
        grantList = new ArrayList<>();
    }
    public ArrayList<HashMap<String, String>> GetGrantsPretendantArray(GrantsPretendant grant){
        grantList.clear();
        for (int i = 0; i < grant.GrantsPretendantItem.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("title", grant.GrantsPretendantItem[i].Name);
            hashMap.put("category", grant.GrantsPretendantItem[i].Category);

            String timestamp = grant.GrantsPretendantItem[i].Date.replace("/Date(", "").replace(")/", "");
            Date createdOn = new Date(Long.parseLong(timestamp));
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
            String formattedDate = sdf.format(createdOn);
            hashMap.put("date",  formattedDate);

            grantList.add(hashMap);
        }

        return grantList;

    }
}
