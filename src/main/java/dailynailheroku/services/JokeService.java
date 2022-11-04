package dailynailheroku.services;

import dailynailheroku.models.dtos.json.JokeEntityExportDto;
import dailynailheroku.models.service.JokeServiceModel;

import java.io.FileNotFoundException;
import java.util.List;

public interface JokeService {
    void createJoke(JokeServiceModel jokeServiceModel);
    String getLatestJoke();
    List<JokeEntityExportDto> exportJokes();

    void seedJokes() throws FileNotFoundException;
}
