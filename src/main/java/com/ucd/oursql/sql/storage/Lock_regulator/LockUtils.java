package storage.Lock_regulator;

import java.util.ArrayList;

public class LockUtils {
    public static boolean searchExclusive(ArrayList<LockTableUnit> array){
        for(LockTableUnit unit : array){
            if(unit.getLockType() == Configuration.EXCLUSIVELOCK){
                return true;
            }
        }
        return false;
    }


    public static boolean searchExclusiveSub(ArrayList<rowLockTableUnit> array){
        for(rowLockTableUnit unit : array){
            if(unit.getLockType() == Configuration.EXCLUSIVELOCK){
                return true;
            }
        }
        return false;
    }
}
