package it.redhat.demo.play.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Hike {

	@Id
	@GeneratedValue
	public long id;

	@NotNull
	public String start;

	@NotNull
	public String destination;

	@ManyToOne
	public Trip recommendedTrip;

	Hike() {
	}

	public Hike(String start, String destination) {
		this.start = start;
		this.destination = destination;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Hike hike = (Hike) o;

		return id == hike.id;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		return "Hike{" +
				"id=" + id +
				", start='" + start + '\'' +
				", destination='" + destination + '\'' +
				", recommendedTrip=" + recommendedTrip +
				'}';
	}

}
