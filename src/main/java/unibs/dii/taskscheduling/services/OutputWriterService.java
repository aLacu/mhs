package unibs.dii.taskscheduling.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unibs.dii.taskscheduling.model.HittingSet;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
@Service
public class OutputWriterService {

    @SneakyThrows
    public void writeOutput(HittingSet hittingSet, Path folderOut){
        final String outName = hittingSet.getFileName() + ".out";
        final File file = new File(folderOut.toFile().toString() + File.separator + outName);
        Files.write(file.toPath(), Collections.singletonList(hittingSet.toString()), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
    }
}
