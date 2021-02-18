package nagp.directservice.consumers.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Repository;

import nagp.directservice.consumers.dao.IConsumerDao;
import nagp.directservice.consumers.models.Consumer;


@Repository
public class ConsumerDao implements IConsumerDao{

	private static final String[] states = {"Andhra Pradesh",
			"Arunachal Pradesh",
			"Assam",
			"Bihar",
			"Chhattisgarh",
			"Goa",
			"Gujarat",
			"Haryana",
			"Himachal Pradesh",
			"Jammu and Kashmir",
			"Jharkhand",
			"Karnataka",
			"Kerala",
			"Madhya Pradesh",
			"Maharashtra",
			"Manipur",
			"Meghalaya",
			"Mizoram",
			"Nagaland",
			"Odisha",
			"Punjab",
			"Rajasthan",
			"Sikkim",
			"Tamil Nadu",
			"Telangana",
			"Tripura",
			"Uttarakhand",
			"Uttar Pradesh",
			"West Bengal",
			"Andaman and Nicobar Islands",
			"Chandigarh",
			"Dadra and Nagar Haveli",
			"Daman and Diu",
			"Delhi",
			"Lakshadweep",
	"Puducherry"};

	private static Random random;

	private static List<Consumer> consumers = new ArrayList<>();
	@Override
	public Optional<Consumer> getConsumer(String consumerId) {
		return consumers.stream()
				.filter(c -> consumerId.trim().equalsIgnoreCase(c.getConsumerId())).findFirst();	}

	@Override
	public List<Consumer> getAllConsumers() {
		return consumers;
	}

	@Override
	public boolean deleteConsumer(String consumerId) {
		return consumers.removeIf(c -> consumerId.trim().equalsIgnoreCase(c.getConsumerId()));
	}

	@Override
	public void addConsumer(Consumer consumer) {
		consumers.add(consumer);
	}

	static {
		random = new Random();
		for (int i = 0; i<random.nextInt(30)+20; i++) {
			consumers.add(randomConsumer(i));
		}
	}

	private static Consumer randomConsumer(int i) {
		String state = states[random.nextInt(states.length)];
		return new Consumer("Consumer "+(i+1), state, "Random address "+(i+1), "Need to get Random work "+(i+1)+"completed.");
	}

}
