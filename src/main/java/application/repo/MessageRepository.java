package application.repo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import application.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderOrderByTimestamp(String sender);
    List<Message> findByReceiverOrderByTimestamp(String receiver);
    List<Message> findBySenderInAndReceiverInOrderByTimestamp(List<String> senders, List<String> receivers);
    List<Message> findBySenderAndReceiverOrderByTimestamp(String sender, String receiver);
    List<Message> findBySenderAndReceiverOrSenderAndReceiverOrderByTimestamp(
            String sender1, String receiver1, String sender2, String receiver2);
}