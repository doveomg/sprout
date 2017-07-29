package com.sprout.demo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
@Entity
@Table(name="ROLEENTITY")
public class RoleEntity implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer roleid;
	
	private String roleName;
	private String description;
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns = {@JoinColumn(name="userid")}, 
    	inverseJoinColumns = {@JoinColumn(name="roleid") })
	private Set<UserEntity> users;

	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<UserEntity> getUsers() {
		return users;
	}
	public void setUsers(Set<UserEntity> users) {
		this.users = users;
	}
	
}