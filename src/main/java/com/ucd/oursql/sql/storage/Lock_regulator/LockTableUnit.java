package storage.Lock_regulator;

public class LockTableUnit {
    String name;
    String lockType;
    Boolean wait;
    LockTableUnit nextUnit;


    public LockTableUnit(String n, String lt , Boolean w){
        name = n;
        lockType = lt;
        wait = w;
    }

    public void setPointer(LockTableUnit ltu){
        nextUnit = ltu;
    }

    public String getName(){
        return name;
    }

    public String getLockType(){
        return lockType;
    }
}
