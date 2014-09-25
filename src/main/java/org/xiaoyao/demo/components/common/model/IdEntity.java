package org.xiaoyao.demo.components.common.model;

import org.springframework.data.annotation.Id;

public abstract class IdEntity {

	@Id
	private String id;

//	protected String squenceKey = this.getClass().getName();
//
//	protected Long squenceStart = new Long(10000);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public String getSquenceKey() {
//		return squenceKey;
//	}
//
//	public void setSquenceKey(String squenceKey) {
//		this.squenceKey = squenceKey;
//	}
//
//	public Long getSquenceStart() {
//		return squenceStart;
//	}
//
//	public void setSquenceStart(Long squenceStart) {
//		this.squenceStart = squenceStart;
//	}
}
