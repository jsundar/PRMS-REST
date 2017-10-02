package sg.edu.nus.iss.phoenix.radioprogram.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

/**
 *
 * @author Lecturer
 * @version 1.0
 */
public class ProgramService {
	DAOFactoryImpl factory;
	ProgramDAO rpdao;

	public ProgramService() {
		super();
		// Sorry. This implementation is wrong. To be fixed.
		factory = new DAOFactoryImpl();
		rpdao = factory.getProgramDAO();
	}
        
        /**
        * This method find the list of radio program
        *
        * @param rpso radio program to be search
        * @return list of found radio program 
        */
	public ArrayList<RadioProgram> searchPrograms(RadioProgram rpso) {
		ArrayList<RadioProgram> list = new ArrayList<>();
		try {
			list = (ArrayList<RadioProgram>) rpdao.searchMatching(rpso);
		} catch (SQLException e) {
                    // TODO Auto-generated catch block
                    Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, e);
		}
		return list;
	}

        /**
        * This method find the radio program using ID
        *
        * @param rp radio program to be search
        * @return list of found radio program 
        */
	public ArrayList<RadioProgram> findRPByCriteria(RadioProgram rp) {
		ArrayList<RadioProgram> currentList = new ArrayList<RadioProgram>();

		try {
			currentList = (ArrayList<RadioProgram>) rpdao.searchMatching(rp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currentList;

	}

        /**
        * This method find the radio program using name
        *
        * @param rpName name of radio program to be search
        * @return the unique radio program
        */
	public RadioProgram findRP(String rpName) {
		RadioProgram currentrp = new RadioProgram();
		currentrp.setName(rpName);
		try {
			currentrp = ((ArrayList<RadioProgram>) rpdao
					.searchMatching(currentrp)).get(0);
			return currentrp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentrp;

	}

        /**
        * This method find all radio program
        *
        * @return list of all radio program
        */
	public ArrayList<RadioProgram> findAllRP() {
		ArrayList<RadioProgram> currentList = new ArrayList<RadioProgram>();
		try {
			currentList = (ArrayList<RadioProgram>) rpdao.loadAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentList;

	}
        
        /**
        * This method create the radio program
        *
        * @param rp radio program to create
        */
	public void processCreate(RadioProgram rp) {
		try {
			rpdao.create(rp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

        /**
        * This method modify the radio program
        *
        * @param rp radio program to modify
        */
	public void processModify(RadioProgram rp) {
		
			try {
				rpdao.save(rp);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

        /**
        * This method delete the radio program
        *
        * @param name name of radio program to delete
        */
	public void processDelete(String name) {

            try {
                RadioProgram rp = new RadioProgram(name);
                rpdao.delete(rp);
            } catch (NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	}

}
