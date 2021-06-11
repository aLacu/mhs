package unibs.dii.taskscheduling;

import ch.qos.logback.classic.Level;
import com.beust.jcommander.JCommander;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import unibs.dii.taskscheduling.services.MatrixReaderService;
import unibs.dii.taskscheduling.services.MinimumHittingSetSolverService;
import unibs.dii.taskscheduling.services.OutputWriterService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class MinimumHittingSetApplication implements CommandLineRunner {

    private final MatrixReaderService matrixReaderService;

    private final MinimumHittingSetSolverService minimumHittingSetSolverService;

    private final OutputWriterService outputWriterService;

    private final AtomicBoolean stop=new AtomicBoolean(false);


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MinimumHittingSetApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    public static void setLoggingLevel(ch.qos.logback.classic.Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }

    @Override
    public void run(String... args) throws IOException {
        final Arguments arguments = new Arguments();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);
        run(arguments);
    }

    private void run(Arguments arguments) throws IOException {
        if(arguments.isVerbose())
            setLoggingLevel(Level.DEBUG);
        final Thread thread = new Thread(()->stop(arguments.getTimeout()));
        thread.start();
        internalRun(arguments.getInputFolder(), arguments.getOutputFolder());
        if(thread.isAlive()){
            thread.interrupt();
        }
    }

    private void internalRun(Path folderIn, Path folderOut) throws IOException {
        try(final Stream<Path> walk = Files.walk(folderIn)){
             walk.takeWhile(this::canContinue)
                    //.parallel()
                    //.unordered()
                    .map(Path::toFile)
                     .filter(File::isFile)
                     .filter(f->f.getName().endsWith(".matrix"))
                     .peek(a->log.debug("Starting new file "))
                     .map(matrixReaderService::readMatrixFromFile)
                     .peek(a->log.debug("read file "+a.getFileName()))
                     .map(minimumHittingSetSolverService::calculateMinimumHittingSet)
                     .peek(a->log.debug("calculated mhs "))
                     .forEach(hs->outputWriterService.writeOutput(hs, folderOut));
        }
    }

    private boolean canContinue(Path path){
        return !stop.get();
    }

    @SneakyThrows
    private void stop(long milliseconds){
        log.info("Starting timer of "+milliseconds+" milliseconds");

        Thread.sleep(milliseconds);
        stop.set(true);
        log.info("Time ran out!");
    }


}
