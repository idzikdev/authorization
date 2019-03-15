package pl.idzikdev.authorization.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.idzikdev.authorization.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    boolean existsByLogin(String login);
    Optional<UserEntity> findByLogin(String login);
}
