package com.ucd.oursql.sql.storage.Lock_regulator;

import com.ucd.oursql.sql.table.type.PrimaryKey;

import java.util.ArrayList;

public class subLockRegulator {


    public String tableName;

    private rowLockTable sublocktable = new rowLockTable();

    public subLockRegulator(String tName){
        tableName = tName;
        sublocktable = new rowLockTable();
    }

    public boolean AddLock(String primaryKey,String lockType){
        if(lockType.equals(Configuration.SHARELOCK)){
            ArrayList<rowLockTableUnit> searchResult = sublocktable.traverseList(tableName);
            if (searchResult.size() == 0 || !LockUtils.searchExclusiveSub(searchResult)){
                sublocktable.addUnit(new rowLockTableUnit(tableName,primaryKey,Configuration.SHARELOCK));
                return true;
            }
            //不存在exclusive的情况
            else if(LockUtils.searchExclusiveSub(searchResult)){
                return false;
            }
            else{
                return false;
            }

        }
        //类型为排他锁的时候
        else if(lockType.equals(Configuration.EXCLUSIVELOCK)){
            ArrayList<rowLockTableUnit> searchResult = sublocktable.traverseList(primaryKey);
            if(searchResult.size() == 0){
                sublocktable.addUnit(new rowLockTableUnit(tableName,primaryKey,Configuration.EXCLUSIVELOCK));
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }

    }

    public boolean addLockToSub(rowLockTableUnit newUnit){
        boolean resultValue = false;
        String primaryKey = newUnit.getPrimaryKey();
        String lockType = newUnit.getLockType();
        if(lockType.equals(Configuration.SHARELOCK)){
            ArrayList<rowLockTableUnit> searchResult = sublocktable.traverseList(newUnit.getPrimaryKey());
            if (searchResult.size() == 0 || !LockUtils.searchExclusiveSub(searchResult)){
                sublocktable.addUnit(new rowLockTableUnit(tableName, primaryKey,Configuration.SHARELOCK));
                return true;
            }
            //不存在exclusive的情况
            else if(LockUtils.searchExclusiveSub(searchResult)){
                return false;
            }
            else{
                return false;
            }
        }
        else{
            ArrayList<rowLockTableUnit> searchResult = sublocktable.traverseList(newUnit.getPrimaryKey());
            if(searchResult.size() == 0){
                sublocktable.addUnit(new rowLockTableUnit(tableName,primaryKey,Configuration.EXCLUSIVELOCK));
                return true;
            }
            else{
                return false;
            }
        }



    }


    public void tableUnlock(String primaryKey){
        sublocktable.unlock(primaryKey);
    }

    public int getSize(){
        return sublocktable.getSize();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
