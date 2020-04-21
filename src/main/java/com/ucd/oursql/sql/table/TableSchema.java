package com.ucd.oursql.sql.table;

public interface TableSchema {
    public static final char ROW_LOCK_GRANULARITY = 'R';
    public static final char TABLE_LOCK_GRANULARITY = 'T';

    public static final String STD_SYSTEM_SCHEMA_NAME = "SYS";
    public static final String STD_SYSTEM_DIAG_SCHEMA_NAME = "SYSCS_DIAG";

    public static final int BASE_TABLE_TYPE = 0;
    public static final int SYSTEM_TABLE_TYPE = 1;
    public static final int VIEW_TYPE = 2;
    public static final int GLOBAL_TEMPORARY_TABLE_TYPE = 3;
    public static final int SYNONYM_TYPE = 4;
    public static final int VTI_TYPE = 5;

    String[] schemaName={
            "BASE_TABLE_TYPE",
            "SYSTEM_TABLE_TYPE",
            "VIEW_TYPE",
            "GLOBAL_TEMPORARY_TABLE_TYPE",
            "SYNONYM_TYPE",
            "VTI_TYPE"};

}
