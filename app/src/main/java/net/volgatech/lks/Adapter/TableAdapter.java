package net.volgatech.lks.Adapter;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.volgatech.lks.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ultim on 12.12.2016.
 */

public class TableAdapter {
    String name;
    ArrayAdapter<String> adapter;

    public TableAdapter(String name, ArrayAdapter<String> adapter) {
        this.name = name;
        this.adapter = adapter;
    }
}
