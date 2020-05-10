package com.ucd.oursql.sql.table;

public interface TableSchema {
    public static final char ROW_LOCK_GRANULARITY = 'R';
    public static final char TABLE_LOCK_GRANULARITY = 'T';

    public static final int BASE_TABLE_TYPE = 0;
    public static final int SYSTEM_TABLE_TYPE = 1;
    public static final int VIEW_TYPE = 2;

    String[] schemaName={
            "BASE_TABLE_TYPE",
            "SYSTEM_TABLE_TYPE",
            "VIEW_TYPE"};

}
