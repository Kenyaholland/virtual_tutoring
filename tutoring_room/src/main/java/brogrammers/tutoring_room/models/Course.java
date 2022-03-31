package brogrammers.tutoring_room.models;

import java.util.ArrayList;

public class Course 
{
	private String crn;
	private ArrayList<String> tutors;
	
	public Course(String crn)
	{
		setCRN(crn);
	}
	
	/**
	 * Sets the course CRN
	 * @param crn This is the course CRN
	 */
	public void setCRN(String crn)
	{
		this.crn = crn;
	}
	
	/**
	 * Returns the course CRN
	 * @return crn This is the course crn
	 */
	public String getCRN()
	{
		return this.crn;
	}
	
	/**
	 * Adds a tutor to a course
	 * @param tutor This is the username of the tutor to be added
	 */
	public void addTutor(String tutor)
	{
		this.tutors.add(tutor);
	}
	
	/**
	 * Removes a tutor from a course
	 * @param tutor This is the username of the tutor to be removed
	 */
	public void removeTutor(String tutor)
	{
		for(int index = 0; index < tutors.size(); index++)
		{
			if(tutors.get(index).contentEquals(tutor))
			{
				tutors.remove(index);
			}
		}
	}
	
	/**
	 * Returns all tutors for the course
	 * @return tutors This is a list of all tutors for the course
	 */
	public ArrayList<String> getTutors()
	{
		return this.tutors;
	}
}
