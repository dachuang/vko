package com.vko.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Entity primary key.
 * 
 * @author darkwing
 */
@MappedSuperclass
public abstract class EntityID implements Serializable {
	private static final long serialVersionUID = -8917134979215575428L;
	protected String id;

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
