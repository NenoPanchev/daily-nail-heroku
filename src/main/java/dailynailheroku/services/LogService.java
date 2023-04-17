package dailynailheroku.services;

import dailynailheroku.models.view.LogViewModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LogService {
    void createLog(HttpServletRequest request, String errorMessage);

    List<LogViewModel> getLogs();
}
