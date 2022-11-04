package dailynailheroku.services;

import dailynailheroku.models.dtos.json.CommentEntityExportDto;
import dailynailheroku.models.service.CommentServiceModel;

import java.io.FileNotFoundException;
import java.util.List;

public interface CommentService {

    void add(CommentServiceModel commentServiceModel, String id);

    void delete(String id);

    List<CommentEntityExportDto> exportComments();

    void seedComments() throws FileNotFoundException;
}
