package it.redhat.demo.play.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Trip {

	@Id
	@GeneratedValue
	public long id;

	public String name;

	@Temporal(TemporalType.DATE )
	public Date startDate;

	@Temporal(TemporalType.DATE )
	public Date endDate;

	public double price;

	@OneToMany(mappedBy="recommendedTrip")
	public Set<Hike> availableHikes = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Trip trip = (Trip) o;

		return id == trip.id;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		return "Trip{" +
				"id=" + id +
				", name='" + name + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", price=" + price +
				'}';
	}
}
