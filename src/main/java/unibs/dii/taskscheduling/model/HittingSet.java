package unibs.dii.taskscheduling.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;


public class HittingSet {

    @Getter
    @Setter
    private boolean[][] hittingSet;

    @Getter
    @Setter
    private String fileName;

    @Override
    public String toString() {
        return "HittingSet{" +
                "hittingSet=" + Arrays.toString(hittingSet) +
                '}';
    }
}
