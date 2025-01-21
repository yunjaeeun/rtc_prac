package moja.socket_pjt.domain.repository;

import moja.socket_pjt.domain.repository.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findTop10BySenderOrReceiverOrderByTIDDesc(String sender, String receiver);

//    @Query("SELECT c FROM chat AS c WHERE c.sender = :sender OR c.receiver = :receiver ORDER By c.t_id DESC LIMIT 10")
//    List<Chat> frinTop10Chrts(@Param("sender") String sender, @Param("receiver") String receiver);
}
