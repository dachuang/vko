package com.vko.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * UUID primary key.
 * 
 * @author weixiao
 */
@MappedSuperclass
public abstract class UUIDSupport implements Serializable{
	private static final long serialVersionUID = -2613360769588963587L;
	
	protected String id;

    //@SearchableId
    @Id
    @Column(nullable=false, updatable=false, length=32)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

}
