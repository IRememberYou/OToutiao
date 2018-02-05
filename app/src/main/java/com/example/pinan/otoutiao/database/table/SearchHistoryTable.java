package com.example.pinan.otoutiao.database.table;

/**
 * @author pinan
 * @date 2017/12/5
 */

public class SearchHistoryTable {
    public static final String TABLE_NAME = "searchhistorytable";
    
    public static final String ID = "id";
    public static final String KEYWORD = "keyword";
    public static final String TIME = "time";
    
    public static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "(" +
        ID + " TEXT AUTO_INCREMENT, " +
        KEYWORD + " text primary key, " +
        TIME + " text) ";
}
