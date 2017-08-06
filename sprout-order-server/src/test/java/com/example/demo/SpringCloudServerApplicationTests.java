package com.example.demo;

import com.sprout.order.SproutOrderServerApplication;
import com.sprout.order.domain.enentCommons.SinkSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SproutOrderServerApplication.class)
public class SpringCloudServerApplicationTests {

	@Autowired
	private SinkSender sender;

	@Test
	public void contextLoads() {
		Map<String,Object> map = new HashMap<>();
		map.put("key1","123");
		map.put("key2","456");
		sender.output().send(MessageBuilder.withPayload(map).build());
	}

}
