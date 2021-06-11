package unibs.dii.taskscheduling.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unibs.dii.taskscheduling.model.BufferedBigQueue;
import unibs.dii.taskscheduling.model.HittingSet;
import unibs.dii.taskscheduling.model.Matrix;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.min;

@RequiredArgsConstructor
@Slf4j
@Service
public class MinimumHittingSetSolverService {

    public HittingSet calculateMinimumHittingSet(Matrix matrix){
        final HittingSet hittingSet = new HittingSet();
        hittingSet.setFileName(matrix.getFileName());
        boolean[][] hittingSetMatrix=solve(matrix.getIntMatrix());
        hittingSet.setHittingSet(hittingSetMatrix);
        return hittingSet;
    }

    //MBase procedure
    @SneakyThrows
    private boolean[][] solve(boolean[][] intMatrix) {
        final int length = intMatrix[0].length;

        
        final ArrayList<boolean[]> output = new ArrayList<>();
        try (final BufferedBigQueue bufferedBigQueue = new BufferedBigQueue();)
        {
            bufferedBigQueue.enqueue(new boolean[length]);
            while (!bufferedBigQueue.isEmpty()){
                final boolean[] element = bufferedBigQueue.dequeue();

                for(int i = getSuccessor(getMax(element), intMatrix); i< length; i++){
                    final boolean[] toTest = Arrays.copyOf(element, element.length);
                    toTest[i]=true;
                    if(containsMhs(toTest, output))
                        continue;
                    final int check = check(toTest, intMatrix);
                    if(check==0&&i< length-1) {
                        bufferedBigQueue.enqueue(toTest);
                    }
                    if(check==1)
                    {
                        output.add(toTest);
                    }
                }
            }
        }

        return output.toArray(new boolean[0][]);
    }



    private boolean containsMhs(boolean[] toTest, ArrayList<boolean[]> output) {
        for (boolean[] ints : output) {
            if(contains(toTest, ints))
                return true;
        }
        return false;
    }

    private boolean contains(boolean[] toTest, boolean[] ints) {
        for(int i=0; i<ints.length; i++){
            if(ints[i]&&!toTest[i])
                return false;
        }
        return true;
    }

    private int check(boolean[] toTest, boolean[][] intMatrix) {
        final int rows = intMatrix.length;
        boolean[] c=new boolean[rows];

        for(int i=0; i<toTest.length; i++){
            if(!toTest[i])
                continue;
            boolean empty=true;
            for(int j = 0; j< rows; j++){
                if(intMatrix[j][i]){
                    empty=false;
                    c[j]=true;
                }
            }
            if(empty)
                return -1;
        }
        for(int i=0; i<c.length; i++){
            if(!c[i])
                return 0;
        }
        return 1;
    }

    public int getSuccessor(int val, boolean[][] intMatrix) {
        return min(val+1, intMatrix[0].length-1);
    }

    public int getMax(boolean[] element) {
        int max=element.length;
        do{
            max--;
        }
        while(max>-1&&!element[max]);
        return max;
    }
}
