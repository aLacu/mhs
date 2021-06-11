package unibs.dii.taskscheduling.services;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class DirectoryValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        final File file = new File(value);
        if(!file.isDirectory())
            throw new ParameterException(name+" must be an existing directory");
    }
}
