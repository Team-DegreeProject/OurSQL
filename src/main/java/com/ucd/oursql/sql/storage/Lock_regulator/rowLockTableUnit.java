package storage.Lock_regulator;

public class rowLockTableUnit {
    private String tableName;
    private String primaryKey;
    private String lockType;
    private rowLockTableUnit nextUnit;

    public rowLockTableUnit(String tableName, String primaryKey, String lockType) {
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.lockType = lockType;
    }


    public String getTableName() {
        return tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getLockType() {
        return lockType;
    }

    public void setPointer(rowLockTableUnit nextOne){
        nextUnit = nextOne;
    }
}
