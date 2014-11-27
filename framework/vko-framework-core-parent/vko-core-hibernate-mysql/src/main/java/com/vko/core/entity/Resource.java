/**
 * 
 */
package com.vko.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 资源实体
 * 
 * @author darkwing
 */
@Entity
@Table(name = "tab_pub_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource extends EntityID implements Serializable {
	private static final long serialVersionUID = 6627382529131187847L;
	private String name;
	private String type;
	private String path;
	private Set<Role> roles;

	/**
	 * The default constructor
	 */
	public Resource() {

	}

	/**
	 * Get role authorities as string
	 * 
	 * @return
	 */
	@Transient
	public String getRoleAuthorities() {
		List<String> roleAuthorities = new ArrayList<String>();
		for (Role role : roles) {
			roleAuthorities.add(role.getPrivillege());
		}
		return StringUtils.join(roleAuthorities.toArray(), ",");
	}

	@Transient
	public String getRoleDescAuthorities() {
		List<String> roleAuthorities = new ArrayList<String>();
		for (Role role : roles) {
			roleAuthorities.add(role.getName());
		}
		return StringUtils.join(roleAuthorities.toArray(), ",");
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the roles
	 */
	@ManyToMany(mappedBy = "resources", targetEntity = Role.class, fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
