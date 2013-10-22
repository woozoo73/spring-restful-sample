package com.github.woozoo73.srs.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "error")
@XmlType(propOrder = { "message" })
public class ServerError implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;

	public ServerError() {
	}

	public ServerError(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServerError [message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}
