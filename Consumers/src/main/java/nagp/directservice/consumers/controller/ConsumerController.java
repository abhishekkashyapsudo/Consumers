package nagp.directservice.consumers.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import nagp.directservice.consumers.exceptions.ConsumerNotFoundException;
import nagp.directservice.consumers.models.Consumer;
import nagp.directservice.consumers.service.IConsumerService;

@RestController
@EnableCircuitBreaker
@RequestMapping("consumers")
public class ConsumerController {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsumerController.class);

	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;

	@Value("${server.port}")
	private int port;

	@Resource
	IConsumerService consumerService;

	/**
	 * Returns the consumer with the passed consumer id
	 * @param consumerId
	 * @return
	 * @throws ConsumerNotFoundException 
	 * @throws InvalidSellerException 
	 */
	@GetMapping(value = "/{consumerId}")
	Consumer getConsumer(@PathVariable(name = "consumerId") String consumerId) throws ConsumerNotFoundException {
		logger.info("Working from port " + port + " of consumer service");
		Optional<Consumer> consumer = consumerService.getConsumer(consumerId);
		if(consumer.isPresent()) {
			return consumer.get();
		}
		throw new ConsumerNotFoundException(consumerId);
	}
	
	@GetMapping()
	List<Consumer> getAllConsumers() {
		logger.info("Working from port " + port + " of Consumer service");
		return consumerService.getAllConsumers();
	}
	
	@GetMapping(value = "orders/{consumerId}")
	String getOrderCount(@PathVariable(name = "consumerId") String consumerId) throws ConsumerNotFoundException {
		logger.info("Working from port " + port + " of Consumer service");
		
		if(!consumerService.getConsumer(consumerId).isPresent())
			throw new ConsumerNotFoundException(consumerId);
		return consumerService.getOrderCount(consumerId);
	}
	
	@GetMapping(value = "allOrders/{consumerId}")
	String getAllOrders(@PathVariable(name = "consumerId") String consumerId) throws ConsumerNotFoundException {
		logger.info("Working from port " + port + " of Consumer service");
		if(!consumerService.getConsumer(consumerId).isPresent())
			throw new ConsumerNotFoundException(consumerId);
		return consumerService.getAllOrders(consumerId);
	}
	
	@GetMapping(value = "request")
	String placeRequest(@RequestParam String consumerId, @RequestParam String description,
			@RequestParam String address,@RequestParam String serviceType) throws ConsumerNotFoundException {
		logger.info("Working from port " + port + " of Consumer service");
		if(!consumerService.getConsumer(consumerId).isPresent())
			throw new ConsumerNotFoundException(consumerId);
		return consumerService.placeRequest(consumerId, description, address, serviceType);
	}
	

	@DeleteMapping(value = "/{consumerId}")
	boolean deleteConsumer(@PathVariable(name = "consumerId") String consumerId) {
		logger.info("Working from port " + port + " of Consumer service");
		return consumerService.deleteConsumer(consumerId);
	}

	@PostMapping
	String addConsumer(@RequestParam String name, @RequestParam String state, 
			@RequestParam String address,@RequestParam String phone) {
		logger.info("Working from port " + port + " of Consumer service");
		String id = null;
		try {
			id = consumerService.addConsumer(name, state, address, phone);
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
			return "ERROR: " + e.getMessage();
		}
		return "Consumer successfully created with seller id " + id;
	}
}
