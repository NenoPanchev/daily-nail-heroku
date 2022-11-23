package dailynailheroku.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import dailynailheroku.models.dtos.json.StatsEntityExportDto;
import dailynailheroku.models.entities.StatsEntity;
import dailynailheroku.models.view.StatsViewModel;
import dailynailheroku.repositories.StatsRepository;
import dailynailheroku.services.StatsService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import static dailynailheroku.constants.GlobalConstants.USERS_FILE_PATH;

@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public StatsServiceImpl(StatsRepository statsRepository, ModelMapper modelMapper, Gson gson) {
        this.statsRepository = statsRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedInitialStatsByCategory() {
        if (statsRepository.count() > 0) {
            return;
        }
        StatsEntity statsEntity = new StatsEntity()
                .setAuthorizedRequests(0)
                .setUnauthorizedRequests(0);
        statsRepository.save(statsEntity);
    }

    @Override
    public void onRequest() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        StatsEntity statsEntity = statsRepository.findAll().get(0);
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            statsEntity.setAuthorizedRequests(statsEntity.getAuthorizedRequests() + 1);
        } else {
            statsEntity.setUnauthorizedRequests(statsEntity.getUnauthorizedRequests() + 1);
        }
        statsRepository.save(statsEntity);
    }

    @Override
    public StatsViewModel getStatsViewModel() {
        return modelMapper.map(statsRepository.findAll().get(0), StatsViewModel.class);
    }

    @Override
    public StatsEntityExportDto exportStats() {
        StatsEntity statsEntity = statsRepository.findAll().get(0);
        return modelMapper.map(statsEntity, StatsEntityExportDto.class);
    }

    @Override
    public void seedStats() throws FileNotFoundException {
        StatsEntityExportDto statsEntityExportDto = gson
                .fromJson(new FileReader(USERS_FILE_PATH), StatsEntityExportDto.class);

        statsRepository.save(modelMapper.map(statsEntityExportDto, StatsEntity.class));

    }
}
