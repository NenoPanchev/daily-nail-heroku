package dailynailheroku.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

public class FileIOUtilImpl implements FileIOUtil {
    @Override
    public String readFileContent(String filepath) throws IOException {
        return Files.readAllLines(Paths.get(filepath))
                .stream()
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public void write(String content, String filePath) throws IOException {
        Files.write(Paths.get(filePath), Collections.singleton(content), StandardCharsets.UTF_8);
    }
}
