package com.ucd.oursql.sql.storage.Lock_regulator;

import java.util.ArrayList;

public class LockTable {
    ArrayList<LockTableUnit> unitsList = new ArrayList<LockTableUnit>();;
    public LockTable(){
        ArrayList<LockTableUnit> unitsList = new ArrayList<LockTableUnit>();
    }

    public void addUnit(LockTableUnit newUnit){
        if(unitsList.size() != 0){
            unitsList.get(unitsList.size()-1).setPointer(newUnit);
        }
        unitsList.add(newUnit);

    }

    public void remoteTopUnit(){
        unitsList.remove(0);
    }

    public LockTableUnit getCurrentUnit(){
        return  unitsList.get(0);
    }

    public ArrayList<LockTableUnit> traverseList(String tableName){
        //根据table的名字来遍历这个列表，从而看看这个列表中的到底有没有这个table上锁的信息
        ArrayList<LockTableUnit> resultUnitList = new ArrayList<>();
        if(unitsList == null){
            return null;
        }else{
            for(int i = 0; i<unitsList.size(); i++){
                if(unitsList.get(i).getName() == tableName){
                    resultUnitList.add(unitsList.get(i));
                }
            }
            return resultUnitList;
        }


    }

    public void unlock(String tableName){
        for(LockTableUnit eachUnit: unitsList){
            if(eachUnit.getName().equals(tableName)){
                unitsList.remove(eachUnit);
                break;
            }
        }
    }
    public int getSize(){
        return unitsList.size();
    }
}
