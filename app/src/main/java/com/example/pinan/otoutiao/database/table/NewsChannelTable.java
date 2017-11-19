package com.example.pinan.otoutiao.database.table;

/**
 *
 * @author pinan
 * @date 2017/11/16
 */

public class NewsChannelTable {
    /**
     * 新闻频道信息表
     */
    public static final String TABLENAME = "NewsChannelTable";
    
    /**
     * 字段部分
     */
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IS_ENABLE = "isEnable";
    public static final String POSITION = "position";
    
    /**
     * 创建表
     */
    public static final String CREATE_TABLE = "create table if not exists " + TABLENAME + "(" +
        ID + " text primary key, " +
        NAME + " text, " +
        IS_ENABLE + " text default '1', " +
        POSITION + " text) ";
}
