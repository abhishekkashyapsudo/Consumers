package nagp.directservice.consumers.service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import nagp.directservice.consumers.dao.IConsumerDao;
import nagp.directservice.consumers.exceptions.ConsumerNotFoundException;
import nagp.directservice.consumers.models.Consumer;
import nagp.directservice.consumers.service.IConsumerService;


@Service
public class ConsumerService implements IConsumerService{

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsumerService.class);

	@Resource
	IConsumerDao consumerDao;

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Override
	public Optional<Consumer> getConsumer(String consumerId) {
		return consumerDao.getConsumer(consumerId);
	}

	@Override
	public List<Consumer> getAllConsumers() {
		return consumerDao.getAllConsumers();
	}


	@Override
	public boolean deleteConsumer(String consumerId) {
		return consumerDao.deleteConsumer(consumerId);
	}

	@Override
	public String addConsumer(String name, String state, String address, String phone) {
		Consumer consumer = new Consumer(name, state, address, phone);
		consumerDao.addConsumer(consumer);
		return consumer.getConsumerId();
	}

	@HystrixCommand(fallbackMethod = "requestsFallback")
	@Override
	public String placeRequest(String consumerId, String description, String address, String serviceType) throws ConsumerNotFoundException {
		Optional<Consumer> consumer = getConsumer(consumerId);
		if(consumer.isPresent()) {
			String baseUrl = loadBalancerClient.choose("requests").getUri().toString()+"/requests";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = null;
			try {
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
						.queryParam("consumerId", consumerId)
						.queryParam("description", description)
						.queryParam("address", address)
						.queryParam("serviceType", serviceType);
				response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.POST, null,
						String.class);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
			return response.getBody();
		}
		else {
			throw new ConsumerNotFoundException(consumerId);
		}

	}


	@HystrixCommand(fallbackMethod = "getOrdersFallback")
	public String getOrderCount(String consumerId) throws ConsumerNotFoundException{
		Optional<Consumer> consumer = getConsumer(consumerId);
		if(consumer.isPresent()) {
			String baseUrl = loadBalancerClient.choose("orders").getUri().toString() + "/orders/consumer";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = null;
			try {
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
						.queryParam("consumerId", consumerId);
				response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, null,
						String.class);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
			return response.getBody();
		}
		else {
			throw new ConsumerNotFoundException(consumerId);
		}

	}

	@HystrixCommand(fallbackMethod = "getOrdersFallback")
	public String getAllOrders(String consumerId) throws ConsumerNotFoundException {
		Optional<Consumer> consumer = getConsumer(consumerId);
		if(consumer.isPresent()) {
			String baseUrl = loadBalancerClient.choose("orders").getUri().toString() + "/orders/allConsumers";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = null;
			try {
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
						.queryParam("consumerId", consumerId);
				response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, null,
						String.class);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
			return response.getBody();
		}
		else {
			throw new ConsumerNotFoundException(consumerId);
		}
	}

	public String requestsFallback(String consumerId, String description, String address, String serviceType) {
		logger.warn("Requests Service is down!!! fallback route enabled...");

		return "CIRCUIT BREAKER ENABLED!!! No Response From Requests Service at this moment. " +
		" Service will be back shortly - ";

	}



	public String getOrdersFallback(String sellerId){
		logger.warn("Orders Service is down!!! fallback route enabled...");

		return "CIRCUIT BREAKER ENABLED!!! No Response From Orders Service at this moment. " +
		" Service will be back shortly - ";
	}


}
