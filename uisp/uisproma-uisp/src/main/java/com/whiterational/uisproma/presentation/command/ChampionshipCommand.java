package com.whiterational.uisproma.presentation.command;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Sport;
import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.service.UispChampionshipAttendanceService;
import com.whiterational.uisproma.business.service.UispChampionshipService;
import com.whiterational.uisproma.business.service.UispSportService;
import com.whiterational.uisproma.presentation.view.ViewMode;

@ManagedBean(name = "champ")
@ViewScoped
public class ChampionshipCommand implements Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = 3284431860581114713L;

	@Inject
	private UispChampionshipService service;

	@Inject
	private UispSportService sport;

	@Inject
	private UispChampionshipAttendanceService att;

	private Championship entity = new Championship();
	private ChampionshipAttendance attendance = new ChampionshipAttendance();
	private Map<Long, Sport> sportChoises = null;
	private Set<ChampionshipAttendance> attendanceToDelete = new HashSet<ChampionshipAttendance>();

	@Inject
	private transient FacesContext ctx;

	@Inject
	private transient ResourceBundle bundle;

	private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipCommand.class);

	public String load() {
		LOGGER.debug("load postback: " + ctx.isPostback());
		if (ctx.isPostback())
			return "";

		if (entity.getId() == null)
			return "";

		entity = service.read(entity.getId());
		
		return "";
	}

	public String save() {
		persist();

		return "champhome?faces-redirect=true";
	}

	private void persist() {
		String user = ctx.getExternalContext().getRemoteUser();
		LOGGER.debug("save by: " + user);

		List<ChampionshipAttendance> attendances = entity.getAttendances();
		for (ChampionshipAttendance attendance : attendances) {
			attendance.setChampionship(entity);
			LOGGER.debug((new SimpleDateFormat("dd/MM/yyyy")).format(attendance.getMoment().getTime()) + "");
		}

		for (ChampionshipAttendance attendance : attendanceToDelete) {
			if (attendance.getId() == null)
				continue;

			att.delete(attendance);
		}

		if (ViewMode.CREATE.equals(this.getMode()))
			service.create(entity, user);
		else
			service.update(entity, user);
	}

	public String apply() {
		persist();

		return "";
	}

	public String delete() {
		LOGGER.debug("delete");

		if (ViewMode.UPDATE.equals(this.getMode()))
			service.delete(entity);

		return "champhome?faces-redirect=true";
	}

	public Championship getEntity() {
		return entity;
	}

	public Date getMoment() {
		return (attendance.getMoment() == null) ? null : attendance.getMoment().getTime();
	}

	public void setMoment(Date date) {
		if (date == null) {
			attendance.setMoment(null);
			return;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		attendance.setMoment(cal);
	}

	public void setEntity(Championship champ) {
		this.entity = champ;
	}

	public ChampionshipAttendance getAttendance() {
		return attendance;
	}

	public Date getStart() {
		return (entity.getStart() == null) ? null : entity.getStart().getTime();
	}

	public Long getSport() {
		return (entity.getSport() == null) ? null : entity.getSport().getId();
	}

	public void setSport(Long id) {
		if (sportChoises == null)
			sportChoises = sport.getChoises();

		if (sportChoises.containsKey(id))
			entity.setSport(sportChoises.get(id));
	}

	public List<SelectItem> getSports() {
		if (sportChoises == null)
			sportChoises = sport.getChoises();

		ArrayList<SelectItem> items = new ArrayList<SelectItem>();
		for (Entry<Long, Sport> entry : sportChoises.entrySet()) {
			items.add(new SelectItem(entry.getKey(), entry.getValue().getName()));
		}

		return items;
	}

	public ViewMode getMode() {
		return (entity.getId() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
	}

	public String getCode() {
		return (ViewMode.CREATE.equals(getMode())) ? bundle.getString("new") : entity.getId() + "";
	}

	public String getTitle() {
		return bundle.getString("champHome." + getMode().getText() + ".title");
	}

	public String getActionName() {
		return bundle.getString("action." + getMode().getText());
	}

	public void applySum() {
		entity.applySum();
	}

}
