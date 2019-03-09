package com.probstl.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NaturalId;

@Entity
public class Building {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@NaturalId
	private String buildingname; 

	@OneToMany
	@JoinColumn(name = "fk_room")
	private Set<Room> rooms = new HashSet<>();
	
	public Building()  {
		//Empty necessary for hibernate
	}
	
	public Building(String nickname) {
		this.buildingname = nickname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuildingname() {
		return buildingname;
	}

	public void setBuildingname(String nickname) {
		this.buildingname = nickname;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public int hashCode() {
		return Objects.hash(buildingname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Building))
			return false;
		Building other = (Building) obj;
		return Objects.equals(buildingname, other.buildingname);
	}
	

}
