package moja.socket_pjt.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import moja.socket_pjt.domain.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT u.name FROM  User AS u WHERE LOCATE(LOWER(:pattern), LOWER(u.name)) > 0 AND u.name != :user ")
    List<String> findNameByNameMatch(@Param("pattern") String pattern, @Param("user") String user);
}
