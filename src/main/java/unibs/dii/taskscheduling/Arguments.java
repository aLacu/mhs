package unibs.dii.taskscheduling;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.PathConverter;
import lombok.Data;
import unibs.dii.taskscheduling.services.DirectoryValidator;

import java.nio.file.Path;

@Data
public class Arguments {

    @Parameter(names="-input-folder", required = true, description = "Input folder path", converter = PathConverter.class, validateWith = DirectoryValidator.class)
    private Path inputFolder;

    @Parameter(names="-output-folder", required = true, description = "Output folder path", converter = PathConverter.class, validateWith = DirectoryValidator.class)
    private Path outputFolder;

    @Parameter(names="-timeout", required = true, description = "Timeout time, ms")
    private long timeout;

    @Parameter(names="-verbose", required = false, description = "Increase the verbosity of logs")
    private boolean verbose;
}
