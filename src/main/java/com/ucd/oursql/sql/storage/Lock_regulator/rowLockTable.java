package storage.Lock_regulator;

import java.util.ArrayList;

public class rowLockTable {
//    private String tableName;
    ArrayList<rowLockTableUnit> unitsList = new ArrayList<rowLockTableUnit>();


    public rowLockTable() {
        ArrayList<LockTableUnit> unitsList = new ArrayList<LockTableUnit>();
    }

    public void addUnit(rowLockTableUnit newUnit) {
        if (unitsList.size() != 0) {
            unitsList.get(unitsList.size() - 1).setPointer(newUnit);
        }
        unitsList.add(newUnit);

    }

    public void remoteTopUnit() {
        unitsList.remove(0);
    }

    public rowLockTableUnit getCurrentUnit() {
        return unitsList.get(0);
    }

//    public String getTableName() {
//        return tableName;
//    }

    public ArrayList<rowLockTableUnit> traverseList(String primaryKey) {
        //根据提供的PrimaryKey的名字来遍历这个列表，从而看看这个列表中的到底有没有这个row上锁的信息
        ArrayList<rowLockTableUnit> resultUnitList = new ArrayList<>();
        for(int i=0; i<unitsList.size(); i++){
            rowLockTableUnit currentUnit = unitsList.get(i);
            String currentPrimaryKey = currentUnit.getPrimaryKey();
            if(currentPrimaryKey.equals(primaryKey)){
                resultUnitList.add(currentUnit);
            }
        }
        return resultUnitList;


    }

    public void unlock(String primaryKey) {
        for (rowLockTableUnit eachUnit : unitsList) {
            if (eachUnit.getPrimaryKey().equals(primaryKey)) {
                unitsList.remove(eachUnit);
                break;
            }
        }
    }

    public int getSize() {
        return unitsList.size();
    }
}
