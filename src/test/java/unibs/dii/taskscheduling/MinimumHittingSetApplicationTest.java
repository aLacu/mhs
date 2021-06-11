package unibs.dii.taskscheduling;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import unibs.dii.taskscheduling.services.MatrixReaderService;
import unibs.dii.taskscheduling.services.MinimumHittingSetSolverService;
import unibs.dii.taskscheduling.services.OutputWriterService;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
class MinimumHittingSetApplicationTest {


    private final MinimumHittingSetApplication minimumHittingSetApplication=new MinimumHittingSetApplication(new MatrixReaderService(),
            new MinimumHittingSetSolverService(), new OutputWriterService());



    @Test
    public void testBenchmark1() throws IOException {
        minimumHittingSetApplication.run(
                "-input-folder","/Users/blasko/Documents/Repositories/mhs/src/test/resources/bench",
                "-output-folder","/Users/blasko/Documents/Repositories/mhs/src/test/resources/out",
                "-timeout","10000000",
                "-verbose");

    }




}
