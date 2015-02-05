package org.mongodb.citybike.entities;

public class TextSearchBean {
	public String text;

	public TextSearchBean() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String toString() {
		return text;
	}
	
}
