package edu.kit.pse.ass.testdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * The Class TestDataLoader.
 */
public class TestDataLoader implements
		ApplicationListener<ContextRefreshedEvent> {

	/** The test data. */
	@Autowired
	TestData testData;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org
	 * .springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		testData.loadAllData();
	}
}
