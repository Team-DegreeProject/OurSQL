package com.ucd.oursql.sql.table.type;

import com.ucd.oursql.sql.parsing.SqlParserConstants;

import java.util.HashMap;
import java.util.Map;

public class SqlConstantImpl implements SqlParserConstants {

    public static Map<Integer,String> sqlMap = new HashMap();

    public SqlConstantImpl(){ constructMap(); }
    public final static int TABLE_DESCRIPTOR=500;
//    public final static int AUTO_START=501;


    private void constructMap(){
        sqlMap.put(INT,"com.ucd.oursql.sql.table.type.number.SqlInt");
        sqlMap.put(DECIMAL,"com.ucd.oursql.sql.table.type.number.SqlDecimal");
        sqlMap.put(DOUBLE,"com.ucd.oursql.sql.table.type.number.SqlDouble");
        sqlMap.put(FLOAT,"com.ucd.oursql.sql.table.type.number.SqlFloat");
        sqlMap.put(REAL,"com.ucd.oursql.sql.table.type.number.SqlReal");
        sqlMap.put(CHAR,"com.ucd.oursql.sql.table.type.text.SqlChar");
        sqlMap.put(VARCHAR,"com.ucd.oursql.sql.table.type.text.SqlVarChar");
        sqlMap.put(BLOB,"com.ucd.oursql.sql.table.type.text.SqlBlob");
        sqlMap.put(DATE,"com.ucd.oursql.sql.table.type.date.SqlDate");
        sqlMap.put(TIME,"com.ucd.oursql.sql.table.type.date.SqlTime");
        sqlMap.put(TIMESTAMP,"com.ucd.oursql.sql.table.type.date.SqlTimeStamp");
        sqlMap.put(YEAR,"com.ucd.oursql.sql.table.type.date.SqlYear");
        sqlMap.put(TABLE_DESCRIPTOR,"com.ucd.oursql.sql.table.TableDescriptor");
        sqlMap.put(BIGINT,"com.ucd.oursql.sql.table.type.number.SqlBigInt");
        sqlMap.put(NUMERIC,"com.ucd.oursql.sql.table.type.number.SqlNumeric");
        sqlMap.put(USER,"com.ucd.oursql.sql.system.User");
        sqlMap.put(STRING,"java.lang.String");
        sqlMap.put(TABLE,"com.ucd.oursql.sql.table.Table");
        sqlMap.put(DATABASE,"com.ucd.oursql.sql.system.Database");
        sqlMap.put(PRIMARY_KEY,"com.ucd.oursql.sql.table.type.PrimaryKey");
        sqlMap.put(DISTINCT,"com.ucd.oursql.sql.table.type.Distinct");
    }




}
