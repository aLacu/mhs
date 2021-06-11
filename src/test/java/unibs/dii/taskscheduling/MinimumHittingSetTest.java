package unibs.dii.taskscheduling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import unibs.dii.taskscheduling.model.HittingSet;
import unibs.dii.taskscheduling.model.Matrix;
import unibs.dii.taskscheduling.services.MinimumHittingSetSolverService;

@RunWith(SpringJUnit4ClassRunner.class)
class MinimumHittingSetTest {

    /**
     * N = { {B3, B4},
     * {A1, A2, B4},
     * {A2, A5, B3, B4}}
     *
     * M={A1, A2, B3, B4, A5}
     *
     * MHS={{B4},{A1,B3},{A2,B3}}
     */

    private MinimumHittingSetSolverService minimumHittingSetSolverService =new MinimumHittingSetSolverService();


    @Test
    public void testGetMaxEmpty(){
        final int max = minimumHittingSetSolverService.getMax(new boolean[3]);
        Assertions.assertEquals(-1, max);
    }


    @Test
    public void testGetMaxWorking(){
        boolean[] ints = new boolean[3];
        ints[1]=true;
        final int max = minimumHittingSetSolverService.getMax(ints);
        Assertions.assertEquals(1, max);
    }


    @Test
    public void testDefaultMHS(){
        boolean[][] matrix=
        {
            {false,false,true,true,false},
            {true,true,false,true,false},
            {false,true,true,true,true}
        };
        final Matrix matrix1 = new Matrix();
        matrix1.setIntMatrix(matrix);
        matrix1.setFileName("Testfilename");
        final HittingSet hittingSet = minimumHittingSetSolverService.calculateMinimumHittingSet(matrix1);
        final boolean[][] matrixHittingSet = hittingSet.getHittingSet();

        Assertions.assertEquals(3, matrixHittingSet.length);
        final boolean[] firstRow = matrixHittingSet[0];
        Assertions.assertEquals(5, firstRow.length);
        Assertions.assertArrayEquals(new boolean[]{false, false, false, true, false}, firstRow);
        Assertions.assertArrayEquals(new boolean[]{false, true, true, false, false}, matrixHittingSet[2]);
        Assertions.assertArrayEquals(new boolean[]{true, false, true, false, false}, matrixHittingSet[1]);
    }



}
