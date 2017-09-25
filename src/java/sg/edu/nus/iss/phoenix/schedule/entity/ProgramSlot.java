/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

import javax.xml.bind.annotation.XmlRootElement;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
/**
 *
 * @author JOHN
 */
@XmlRootElement
public class ProgramSlot {

   private int id;
   private RadioProgram program;
   private String duration;
   private String dateOfProgram;
   private String startTime;   
   private User presenter;
   private User producer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    public User getPresenter() {
        return presenter != null ? presenter : new User();
    }

    public void setPresenter(User presenter) {
        this.presenter = presenter;
    }

    public User getProducer() {
        return producer != null ? producer : new User();
    }

    public void setProducer(User producer) {
        this.producer = producer;
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

    public RadioProgram getProgram() {
        return program != null ? program : new RadioProgram();
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

    public void setProgramName(RadioProgram program) {
        this.program = program;
    }
    
}
