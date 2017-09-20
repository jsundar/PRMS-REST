/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author win
 */
@XmlRootElement
public class WeeklySchedule {
    private String startDate;
    private String assignedBy;
    
    private List<ProgramSlot> programSots;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public List<ProgramSlot> getProgramSots() {
        return programSots;
    }

    public void setProgramSots(List<ProgramSlot> programSots) {
        this.programSots = programSots;
    }
    
    
    
    
    
    
}
