package com.ucd.oursql.sql.table.column;

import com.ucd.oursql.sql.table.type.SqlConstant;

import static com.ucd.oursql.sql.table.type.SqlConstantImpl.sqlMap;

public class DataTypeDescriptor implements SqlConstant {

    private int typeId;
    private int precision=-1;
    private int scale=-1;
    private boolean isNullable=true;
    private boolean primaryKey=false;

    public DataTypeDescriptor(int typeId, boolean isNullable,boolean primaryKey) {
        this.typeId = typeId;
        this.isNullable = isNullable;
        this.primaryKey=true;
    }

    public DataTypeDescriptor(int typeId, int precision,int scale,boolean isNullable,boolean primaryKey) {
        this.typeId = typeId;
        this.precision=precision;
        this.scale=scale;
        this.isNullable = isNullable;
        this.primaryKey=primaryKey;
    }

    public DataTypeDescriptor(int typeId) {
        this.typeId = typeId;
    }

    public DataTypeDescriptor(int typeId, boolean isNullable) {
        this.typeId = typeId;
        this.isNullable = isNullable;
    }

    /**
     * Returns the number of decimal digits for the datatype, if applicable.
     *
     * @return The number of decimal digits for the datatype.  Returns
     * zero for non-numeric datatypes.
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * 返回范围 例如int（8）返回8
     * @return
     */
    public int getScale() {
        return scale;
    }



    /**
     * Returns TRUE if the datatype can contain NULL, FALSE if not.
     * I assume we will never have columns where the nullability is unknown.
     *
     * @return TRUE if the datatype can contain NULL, FALSE if not.
     */
    public boolean isNullable() {
        return isNullable;
    }


    /**
     * Get the type Id stored within this type descriptor.
     */
    public int getTypeId() {
        return typeId;
    }



    @Override
    public String toString() {
        return tokenImage[typeId];
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void printDataTypeDescriptor(){
        System.out.println(
                "Datatypedescriptor=============="+
                "Type:"+sqlMap.get(typeId)+","+
                "Precision:"+precision+","+
                "Scale:"+scale+","+
                "isNullable:"+isNullable+","+
                "PrimaryKey:"+primaryKey
        );
    }

    public DataTypeDescriptor getNewDataTypeDescripter(){
        DataTypeDescriptor d=new DataTypeDescriptor(this.typeId,this.precision,this.scale,this.isNullable,this.primaryKey);
//        d.printDataTypeDescriptor();
        return d;
    }


}

