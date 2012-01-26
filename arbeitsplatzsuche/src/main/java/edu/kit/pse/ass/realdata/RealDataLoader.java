package edu.kit.pse.ass.realdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Lennart
 * 
 */
public class RealDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	/** The test data. */
	@Autowired
	RealData realData;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org
	 * .springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		realData.loadAllData();
	}

}
