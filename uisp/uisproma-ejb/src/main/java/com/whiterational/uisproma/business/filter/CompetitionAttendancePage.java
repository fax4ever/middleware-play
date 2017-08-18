package com.whiterational.uisproma.business.filter;

import java.util.List;

import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;

public class CompetitionAttendancePage extends ApplicationPage<CompetitionAttendance> {

  public CompetitionAttendancePage(List<CompetitionAttendance> items, Long total) {
    super(items, total);
  }

}
