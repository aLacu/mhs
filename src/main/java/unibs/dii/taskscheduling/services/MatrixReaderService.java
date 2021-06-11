package unibs.dii.taskscheduling.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unibs.dii.taskscheduling.model.Matrix;

import java.io.File;
import java.nio.file.Files;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
@Service
public class MatrixReaderService {

    @SneakyThrows
    public Matrix readMatrixFromFile(File file) {
        final Matrix matrix = new Matrix();
        matrix.setFileName(file.getName());
        try(Stream<String> lines=Files.lines(file.toPath())){
            final boolean[][] intMatrix = lines
                    .filter(s -> !s.startsWith(";"))
                    .map(this::parseLine).toArray(boolean[][]::new);
            matrix.setIntMatrix(intMatrix);
        }

        return matrix;
    }

    private boolean[] parseLine(String line){
        final String[] split = line.split(" ");
        boolean[] arr = new boolean[split.length-1];
        int count = 0;
        for (int j=0; j<arr.length; j++) {
            arr[count++]=Integer.parseInt(split[j])==1;
        }
        return arr;
    }
}
