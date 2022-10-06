package com.s4plabs.logbook.db;

import android.provider.BaseColumns;

public final class DBContract
{
    public static final class DayLogs implements BaseColumns
    {
        public static final String TABLE_NAME = "logs";
        public static final String COLUMN_NAME_ID = "id";
        //public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_LOG = "log";
    }
}
