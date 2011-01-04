package edu.kit.cm.kitcampusguide;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class TestBean {
	
	private String text;
	
	public TestBean() {
		this.text = "Test bestanden";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String klick() {
		this.text = "wieder bestanden";
		return "testBean2";
	}
	
	public String back() {
		return "index";
	}

}
