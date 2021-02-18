package nagp.directservice.consumers.service;

import java.util.List;
import java.util.Optional;

import nagp.directservice.consumers.exceptions.ConsumerNotFoundException;
import nagp.directservice.consumers.models.Consumer;

public interface IConsumerService {

	Optional<Consumer> getConsumer(String consumerId);

	List<Consumer> getAllConsumers();

	String getAllOrders(String consumerId) throws ConsumerNotFoundException;

	String getOrderCount(String consumerId) throws ConsumerNotFoundException;

	boolean deleteConsumer(String consumerId);

	String addConsumer(String name, String state, String address, String phone);

	String placeRequest(String consumerId, String description, String address,String serviceType) throws ConsumerNotFoundException;

}
