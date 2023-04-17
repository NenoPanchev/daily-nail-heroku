package dailynailheroku.repositories;

import dailynailheroku.models.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, String> {
    @Query("SELECT l FROM LogEntity l " +
            "ORDER BY l.time DESC ")
    List<LogEntity> findAllOrderByTimeDesc();
}
