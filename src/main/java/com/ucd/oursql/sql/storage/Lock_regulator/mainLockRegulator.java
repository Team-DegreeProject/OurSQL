package storage.Lock_regulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mainLockRegulator {
    Map<String, subLockRegulator> regulatorMap = new HashMap<String, subLockRegulator>();

    public void addRegulator(subLockRegulator Nregulator){
        String tableName = Nregulator.getTableName();
        regulatorMap.put(tableName,Nregulator);
    }

    public void addLock(rowLockTableUnit Nunit){
        String tableName = Nunit.getTableName();
        String primaryKey = Nunit.getPrimaryKey();
        String lockType = Nunit.getLockType();
        //if the map does not have the required regulator then we need to add a new one
        if(regulatorMap.get(tableName) == null){
            subLockRegulator freshRegulator = new subLockRegulator(tableName);
            addRegulator(freshRegulator);
        }

        subLockRegulator currentRegulator = regulatorMap.get(tableName);
        currentRegulator.addLockToSub(Nunit);
    }
}
