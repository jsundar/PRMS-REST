/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.programslot.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JOHN
 */
@XmlRootElement
public class ProgramSlot {
    
   private String duration;
   private String dateOfProgram;
   private String startTime;
   private String programName;
   private String presenterId;
   private String producerId;

    public String getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(String presenterId) {
        this.presenterId = presenterId;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
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
