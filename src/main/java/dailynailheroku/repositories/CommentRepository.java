package dailynailheroku.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dailynailheroku.models.entities.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {
}
