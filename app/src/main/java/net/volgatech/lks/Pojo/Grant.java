package net.volgatech.lks.Pojo;

/**
 * Created by Ultim on 23.12.2016.
 */

public class Grant {

    public GrantItem[] GrantItem;

    public class GrantItem{
        public String Name;
        public String DateStart;
        public String DateEnd;
        public String Cost;
    }
}
