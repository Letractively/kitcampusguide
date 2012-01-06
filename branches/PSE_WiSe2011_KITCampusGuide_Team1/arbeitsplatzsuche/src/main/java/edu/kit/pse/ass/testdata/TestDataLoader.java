package edu.kit.pse.ass.testdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class TestDataLoader implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	TestData testData;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		testData.userFillWithDummies();
	}
}
