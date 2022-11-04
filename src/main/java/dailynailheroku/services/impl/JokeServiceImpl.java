package dailynailheroku.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import dailynailheroku.constants.GlobalConstants;
import dailynailheroku.models.dtos.json.JokeEntityExportDto;
import dailynailheroku.models.entities.JokeEntity;
import dailynailheroku.models.entities.UserEntity;
import dailynailheroku.models.service.JokeServiceModel;
import dailynailheroku.models.service.UserServiceModel;
import dailynailheroku.models.validators.ServiceLayerValidationUtil;
import dailynailheroku.repositories.JokeRepository;
import dailynailheroku.services.JokeService;
import dailynailheroku.services.UserService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JokeServiceImpl implements JokeService {
    private final JokeRepository jokeRepository;
    private final ModelMapper modelMapper;
    private final ServiceLayerValidationUtil serviceLayerValidationUtil;
    private final UserService userService;
    private final Gson gson;

    public JokeServiceImpl(JokeRepository jokeRepository, ModelMapper modelMapper, ServiceLayerValidationUtil serviceLayerValidationUtil, UserService userService, Gson gson) {
        this.jokeRepository = jokeRepository;
        this.modelMapper = modelMapper;
        this.serviceLayerValidationUtil = serviceLayerValidationUtil;
        this.userService = userService;
        this.gson = gson;
    }

    @Override
    public void createJoke(JokeServiceModel jokeServiceModel) {
        serviceLayerValidationUtil.validate(jokeServiceModel);

        UserServiceModel principal = userService.getPrincipal();
        jokeServiceModel
                .setCreated(LocalDateTime.now())
                .setAuthor(principal);
        jokeRepository.save(modelMapper.map(jokeServiceModel, JokeEntity.class));
    }

    @Override
    public String getLatestJoke() {
        return jokeRepository.findLatestJoke();
    }

    @Override
    public List<JokeEntityExportDto> exportJokes() {
        return jokeRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, JokeEntityExportDto.class)
                        .setCreated(entity.getCreated().toString().substring(0, 16)))
                .collect(Collectors.toList());
    }

    @Override
    public void seedJokes() throws FileNotFoundException {
        if (jokeRepository.count() > 0) {
            return;
        }
        JokeEntityExportDto[] jokeEntityExportDtos = gson
                .fromJson(new FileReader(GlobalConstants.JOKES_FILE_PATH), JokeEntityExportDto[].class);

        List<JokeEntity> entities = Arrays.stream(jokeEntityExportDtos)
                .map(dto -> modelMapper.map(dto, JokeEntity.class)
                        .setAuthor(modelMapper.map(userService.findByEmail(dto.getAuthorEmail()), UserEntity.class))
                        .setCreated(getLocalDateTimeFromString(dto.getCreated())))
                .collect(Collectors.toList());
        jokeRepository.saveAllAndFlush(entities);
    }

    private LocalDateTime getLocalDateTimeFromString(String time) {
        if (time == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(time, formatter);
    }
}
