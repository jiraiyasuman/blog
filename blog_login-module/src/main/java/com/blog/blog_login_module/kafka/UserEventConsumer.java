package com.blog.blog_login_module.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.blog_login_module.dto.UserEvent;
import com.blog.blog_login_module.entity.UserRead;
import com.blog.blog_login_module.repository.UserReadRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEventConsumer {

	private Logger LOGGER = LoggerFactory.getLogger(UserEventConsumer.class);
	private final UserReadRepository userReadRepository;
	private final ObjectMapper objectMapper;
	@KafkaListener(topics = "user-events" , groupId = "user-read-group", 
			containerFactory = "kafkaListenerContainerFactory"
			)
	@Transactional
	public void consume(ConsumerRecord<String, String> record, Acknowledgment ack) {
		try {
			UserEvent event = objectMapper.readValue(record.value(), UserEvent.class);
			switch(event.getEventType()) {
			case "CREATE":
				break;
			case "UPDATE":
				UserRead user = userReadRepository.findById(event.getId())
				.orElse(new UserRead());
				user.setId(event.getId());
				user.setEmail(event.getEmail());
				user.setUsername(event.getUsername());
				user.setEnabled(event.isEnabled());
				user.setLocked(event.isLocked());
				userReadRepository.save(user);
				break;
			case "DELETE":
				userReadRepository.deleteById(event.getId());
				break;
			default:
				System.out.println("");
			}
			ack.acknowledge();
		} catch (Exception e) {
			LOGGER.error("Kafka consume error"+e);
			throw new RuntimeException(e);
		}
	}
}
