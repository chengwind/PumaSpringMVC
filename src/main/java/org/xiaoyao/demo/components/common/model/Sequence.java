package org.xiaoyao.demo.components.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sequence {
	
	private String id;

	private Long value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
