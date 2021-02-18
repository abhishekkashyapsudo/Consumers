package nagp.directservice.consumers.dao;

import java.util.List;
import java.util.Optional;

import nagp.directservice.consumers.models.Consumer;

public interface IConsumerDao {

	Optional<Consumer> getConsumer(String consumerId);

	List<Consumer> getAllConsumers();

	boolean deleteConsumer(String consumerId);

	void addConsumer(Consumer consumer);

}
