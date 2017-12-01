package com.example.pinan.otoutiao.database.table;

/**
 * @author pinan
 * @date 2017/11/30
 */

public class MediaChannelTable {
    //表名
    public static String TABLE_NAME = "MediaChannelTable";
    
    //字段
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AVATAR = "avatar";
    public static final String TYPE = "type";
    public static final String FOLLOWCOUNT = "followCount";
    public static final String DESCTEXT = "descText";
    public static final String URL = "url";
    
    //建表语句
    public static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "(" +
        ID + " text primary key, " +
        NAME + " text, " +
        AVATAR + " text, " +
        TYPE + " text, " +
        FOLLOWCOUNT + " text, " +
        DESCTEXT + " text, " +
        URL + " text)";
    
//    public static final String CREATE_TABLE = "create table if not exists " + TABLENAME + "(" +
//        ID + " text primary key, " +
//        NAME + " text, " +
//        AVATAR + " text, " +
//        TYPE + " text, " +
//        FOLLOWCOUNT + " text, " +
//        DESCTEXT + " text, " +
//        URL + " text) ";
    
}
