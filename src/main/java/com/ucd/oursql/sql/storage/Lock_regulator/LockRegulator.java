package com.ucd.oursql.sql.storage.Lock_regulator;

import java.util.ArrayList;

public class LockRegulator {
    public String tableName;

    private LockTable locktable;

    public LockRegulator(){
//        tableName = tName;
        locktable = new LockTable();
    }

    public boolean AddLock(String tableName,String lockType){
        if(lockType.equals(Configuration.SHARELOCK)){
            ArrayList<LockTableUnit> searchResult = locktable.traverseList(tableName);
            if (searchResult.size() == 0 || !LockUtils.searchExclusive(searchResult)){
                locktable.addUnit(new LockTableUnit(tableName,Configuration.SHARELOCK,false));
                return true;
            }
            //不存在exclusive的情况
            else if(LockUtils.searchExclusive(searchResult)){
                return false;
            }
            else{
                return false;
            }

        }
        //类型为排他锁的时候
        else if(lockType.equals(Configuration.EXCLUSIVELOCK)){
            ArrayList<LockTableUnit> searchResult = locktable.traverseList(tableName);
            if(searchResult.size() == 0){
                locktable.addUnit(new LockTableUnit(tableName,Configuration.EXCLUSIVELOCK,false));
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


    public void tableUnlock(String tableName){
        locktable.unlock(tableName);
    }

    public int getSize(){
        return locktable.getSize();
    }

    public String getTableName() {
        return tableName;
    }

}
