/**
 * 
 */
package com.vko.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 角色实体
 * 
 * @author darkwing
 *
 */
@Entity
@Table(name="dk_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends EntityID implements Serializable{
	private static final long serialVersionUID = 1989481701095387602L;
	private String name;  //角色名称
	private String privillege; //角色权限  形如ROLE_ADMIN
	private int level = 0; //角色级别 0超级管理员组，1省级，2地区级，3市县
	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	private Set<Resource> resources;
	
	private Set<User> users; //拥有的用户
	
	
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "roles", fetch = FetchType.LAZY)   
	public Set<User> getUsers() {
		return users;
	}


	public void setUsers(Set<User> users) {
		this.users = users;
	}


	/**
	 * The default constructor
	 */
	public Role() {
		
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the resources
	 */
	@ManyToMany(targetEntity = Resource.class,cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(name = "tab_pub_role_resource", 
    		joinColumns = @JoinColumn(name = "role_id"), 
    		inverseJoinColumns = @JoinColumn(name = "resource_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Resource> getResources() {
		return resources;
	}

	@Transient
	public String getResourcesString() {
	    List<String> resources = new ArrayList<String>();
	    for(Resource resource : this.getResources()) {
	    	resources.add(resource.getPath());
	    }
	    return StringUtils.join(resources.toArray(), ",");
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	public String getPrivillege() {
		return privillege;
	}


	public void setPrivillege(String privillege) {
		this.privillege = privillege;
	}


	/**
	 * @param resources the resources to set
	 */
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
}
