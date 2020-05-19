package com.ucd.oursql.sql.driver;

import com.ucd.oursql.sql.parsing.ParseException;
import com.ucd.oursql.sql.parsing.SqlParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OurSqlPreparedStatement implements PreparedStatement {
    private String sql;
    public OurSqlPreparedStatement(String sql){
        this.sql=sql;
    }
    @Override
    public ResultSet executeQuery() throws SQLException {
        InputStream target = new ByteArrayInputStream(sql.getBytes());
        SqlParser parser = new SqlParser(target);
        try {
            Object result=parser.parse();
            if(result instanceof ResultSet){
                return (ResultSet)result;
            }else{
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int executeUpdate() throws SQLException {
        InputStream target = new ByteArrayInputStream(sql.getBytes());
        SqlParser parser = new SqlParser(target);
        try {
            Object result=parser.parse();
            if(result instanceof Integer){
                return (int)result;
            }else{
                return -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void setNull(int i, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setBoolean(int i, boolean b) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setByte(int i, byte b) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setShort(int i, short i1) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    public void findIndex(int i,int s){
        int index=-1;
        if(sql.indexOf("?")!=0){
            index=sql.indexOf("?");
            for(int j=2; j<=i;j++){
                index=sql.indexOf("?",index);
            }
        }
        StringBuilder sb=new StringBuilder(sql);
        sb.replace(index,index+1, String.valueOf(s));
        sql=sb.toString();
        System.out.println(sql);
    }
    @Override
    public void setInt(int i, int i1) throws SQLException {
        findIndex(i,i1);
    }
    public void findIndex(int i,long s){
        int index=-1;
        if(sql.indexOf("?")!=0){
            index=sql.indexOf("?");
            for(int j=2; j<=i;j++){
                index=sql.indexOf("?",index);
            }
        }
        StringBuilder sb=new StringBuilder(sql);
        sb.replace(index,index+1, String.valueOf(s));
        sql=sb.toString();
        System.out.println(sql);
    }
    @Override
    public void setLong(int i, long l) throws SQLException {
        findIndex(i,l);
    }
    public void findIndex(int i,float s){
        int index=-1;
        if(sql.indexOf("?")!=0){
            index=sql.indexOf("?");
            for(int j=2; j<=i;j++){
                index=sql.indexOf("?",index);
            }
        }
        StringBuilder sb=new StringBuilder(sql);
        sb.replace(index,index+1, String.valueOf(s));
        sql=sb.toString();
        System.out.println(sql);
    }
    @Override
    public void setFloat(int i, float v) throws SQLException {
        findIndex(i,v);
    }
    public void findIndex(int i,double s){
        int index=-1;
        if(sql.indexOf("?")!=0){
            index=sql.indexOf("?");
            for(int j=2; j<=i;j++){
                index=sql.indexOf("?",index);
            }
        }
        StringBuilder sb=new StringBuilder(sql);
        sb.replace(index,index+1, String.valueOf(s));
        sql=sb.toString();
        System.out.println(sql);
    }
    @Override
    public void setDouble(int i, double v) throws SQLException {
        findIndex(i,v);
    }

    @Override
    public void setBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    public void findIndex(int i,String s){
        int index=-1;
        if(sql.indexOf("?")!=0){
            index=sql.indexOf("?");
            for(int j=2; j<=i;j++){
                index=sql.indexOf("?",index);
            }
        }
        StringBuilder sb=new StringBuilder(sql);
        sb.replace(index,index+1,"\""+s+"\"");
        sql=sb.toString();
        System.out.println(sql);
    }
    @Override
    public void setString(int i, String s) throws SQLException {
        StringBuilder buf = new StringBuilder();
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            switch (c)
            {
                case '\0':
                    buf.append("\\0");
                    break;
                case '\n':
                    buf.append("\\n");
                    break;
                case '\r':
                    buf.append("\\r");
                    break;
                case '\'':
                    buf.append("\\'");
                    break;
                case '"':
                    buf.append("\\\"");
                    break;
                case '\\':
                    buf.append("\\\\");
                    break;
                case '%':
                    buf.append("\\%");
                    break;
                case '_':
                    buf.append("\\_");
                    break;
                default:
                    buf.append(c);
                    break;
            }
        }
    s=buf.toString();
    findIndex(i,s);
    }

    @Override
    public void setBytes(int i, byte[] bytes) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setDate(int i, Date date) throws SQLException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=sdf.format(date);
        findIndex(i,dateStr);
    }

    @Override
    public void setTime(int i, Time time) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr=sdf.format(time);
        findIndex(i,timeStr);
    }

    @Override
    public void setTimestamp(int i, Timestamp timestamp) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampStr=sdf.format(timestamp);
        findIndex(i,timestampStr);
    }

    @Override
    public void setAsciiStream(int i, InputStream inputStream, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setUnicodeStream(int i, InputStream inputStream, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setBinaryStream(int i, InputStream inputStream, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void clearParameters() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setObject(int i, Object o, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setObject(int i, Object o) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute() throws SQLException {
        InputStream target = new ByteArrayInputStream(sql.getBytes());
        SqlParser parser = new SqlParser(target);
        try {
            Object result=parser.parse();
            if(result instanceof ResultSet){
                return true;
            }else{
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setCharacterStream(int i, Reader reader, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setRef(int i, Ref ref) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setBlob(int i, Blob blob) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setClob(int i, Clob clob) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setArray(int i, Array array) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setDate(int i, Date date, Calendar calendar) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setTime(int i, Time time, Calendar calendar) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setTimestamp(int i, Timestamp timestamp, Calendar calendar) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setNull(int i, int i1, String s) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setURL(int i, URL url) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setRowId(int i, RowId rowId) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setNString(int i, String s) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setNCharacterStream(int i, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setNClob(int i, NClob nClob) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setClob(int i, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setBlob(int i, InputStream inputStream, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setNClob(int i, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setSQLXML(int i, SQLXML sqlxml) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setObject(int i, Object o, int i1, int i2) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setAsciiStream(int i, InputStream inputStream, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setBinaryStream(int i, InputStream inputStream, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setCharacterStream(int i, Reader reader, long l) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setAsciiStream(int i, InputStream inputStream) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setBinaryStream(int i, InputStream inputStream) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setCharacterStream(int i, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setNCharacterStream(int i, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setClob(int i, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setBlob(int i, InputStream inputStream) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setNClob(int i, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet executeQuery(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void close() throws SQLException {
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setMaxFieldSize(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxRows() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setMaxRows(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setEscapeProcessing(boolean b) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setQueryTimeout(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void cancel() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setCursorName(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setFetchDirection(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setFetchSize(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void addBatch(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void clearBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Connection getConnection() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean getMoreResults(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String s, int i) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String s, int[] ints) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String s, String[] strings) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String s, int i) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String s, int[] ints) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String s, String[] strings) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isClosed() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setPoolable(boolean b) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
