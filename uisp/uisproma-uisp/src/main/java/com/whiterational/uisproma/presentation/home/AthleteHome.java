package com.whiterational.uisproma.presentation.home;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.inject.Inject;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.service.UispAthleteService;
import com.whiterational.uisproma.business.service.UispClubService;
import com.whiterational.uisproma.presentation.faces.ExcelProperty;

@Home
public class AthleteHome implements Serializable, ValueChangeListener {

  /**
   * 
   */
  private static final long      serialVersionUID = -7172516816838127567L;

  private UploadedFile           file;

  private static final Logger    LOGGER           = LoggerFactory.getLogger(AthleteHome.class);
  
  @Inject
  private UispClubService clubService;
  
  @Inject
  private UispAthleteService athleteService;
  
  @Inject
  private ExcelProperty         excel;

  @Inject
  private transient FacesContext ctx;

  public UploadedFile getFile() {
    return file;
  }

  public void setFile(UploadedFile file) {
    this.file = file;
  }

  public void importFile() {
    if (file == null) {
      ctx.addMessage(null, new FacesMessage("Nessun file caricato"));
      return;
    }

    LOGGER.info("File type: " + file.getContentType());
    LOGGER.info("File name: " + file.getName());
    LOGGER.info("File size: " + file.getSize() + " bytes");

    Workbook wb = null;
    
    try {
      wb = WorkbookFactory.create(file.getInputStream());
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      LOGGER.error("File format not correct", e);
      ctx.addMessage(null,
          new FacesMessage(String.format("Il file '%s' di tipo '%s' non è di un tipo idoneo!", file.getName(), file.getContentType())));
      return;
    }
    
    Sheet sheet = wb.getSheetAt(0);    
    long result = importExcel(sheet);
    
    ctx.addMessage(null,
        new FacesMessage(String.format("Il file '%s' di tipo '%s' è stato correttamente importato!", file.getName(), file.getContentType())));
    ctx.addMessage(null, new FacesMessage("contiene " + result + " righe"));
    
  }

  @Override
  public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
    this.file = (UploadedFile) event.getNewValue();
  }
  
  public long importExcel(Sheet sheet) {
    long result = 0l;
    Map<String, SportsClub> clubs = new HashMap<String, SportsClub>();
    Set<String> novelties = new HashSet<String>();

    for (Row row : sheet) {
      String code = excel.getString(row, "import.uispCode");
      if (code == null || code.isEmpty() || !code.matches(Athlete.CODE_REGEX) || athleteService.exist(code))
        continue;

      Athlete athlete = new Athlete();
      athlete.setUispCode(code);
      
      String clubcode = excel.getString(row, "import.club.code");
      if (!clubs.containsKey(clubcode)) {
        
        SportsClub club = clubService.findByCode(clubcode);
        if (club == null) {
          club = new SportsClub();
          String clubName = excel.getString(row, "import.club.name");

          club.setFreshman(clubcode);
          club.setName(clubName);
          
          novelties.add(clubcode);
        }
        clubs.put(clubcode, club);
      }
      athlete.setClub(clubs.get(clubcode));
      athlete.getClub().getAthletes().add(athlete);
      athlete.setSurname(excel.getString(row, "import.surname"));
      athlete.setName(excel.getString(row, "import.name"));
      athlete.getAddress().setStreet(excel.getString(row, "import.street", "import.streetNumber"));
      athlete.getAddress().setZip(excel.getString(row, "import.zip"));
      athlete.getAddress().setCity(excel.getString(row, "import.city"));
      athlete.setBirthDate(excel.getCalendar(row, "import.birthDate"));
      athlete.setHometown(excel.getString(row, "import.homeTown"));
      result++;
    }
    
    clubService.save(clubs.values(), novelties);

    return result;
  }


}
