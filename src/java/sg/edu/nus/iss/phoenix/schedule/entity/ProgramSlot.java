/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JOHN
 */
@XmlRootElement
public class ProgramSlot {

   private int id;
   private String programName;
   private String duration;
   private String dateOfProgram;
   private String startTime;   
   private String presenter;
   private String producer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenterId) {
        this.presenter = presenterId;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producerId) {
        this.producer = producerId;
    }

    public String getDuration() {
        return duration;
    }

    public String getDateOfProgram() {
        return dateOfProgram;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getProgramName() {
        return programName;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDateOfProgram(String dateOfProgram) {
        this.dateOfProgram = dateOfProgram;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
    
}
