/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACCLiveTiming.networking.data;

import ACCLiveTiming.networking.enums.LapType;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Leonard
 */
public class LapInfo {

    int lapTimeMS;
    int carIndex;
    int driverIndex;
    List<Integer> splits = new LinkedList<>();
    boolean isInvalid;
    boolean isValidForBest;
    LapType type = LapType.ERROR;

    public LapInfo() {
    }

    public LapInfo(int lapTimeMS, int carIndex, int driverIndex, List<Integer> splits, boolean isInvalid,
            boolean isValidForBest, LapType type) {
        this.lapTimeMS = lapTimeMS;
        this.carIndex = carIndex;
        this.driverIndex = driverIndex;
        this.splits = splits;
        this.isInvalid = isInvalid;
        this.isValidForBest = isValidForBest;
        this.type = type;
    }

    public int getLapTimeMS() {
        return lapTimeMS;
    }

    public int getCarIndex() {
        return carIndex;
    }

    public int getDriverIndex() {
        return driverIndex;
    }

    public List<Integer> getSplits() {
        return splits;
    }

    public boolean getIsInvalid() {
        return isInvalid;
    }

    public boolean getIsValidForBest() {
        return isValidForBest;
    }

    public LapType getType() {
        return type;
    }

}