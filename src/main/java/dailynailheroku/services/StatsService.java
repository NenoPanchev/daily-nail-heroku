package dailynailheroku.services;

import dailynailheroku.models.dtos.json.StatsEntityExportDto;
import dailynailheroku.models.view.StatsViewModel;

import java.io.FileNotFoundException;

public interface StatsService {
    void seedInitialStatsByCategory();
    void onRequest();
    StatsViewModel getStatsViewModel();
    StatsEntityExportDto exportStats();

    void seedStats() throws FileNotFoundException;
}
