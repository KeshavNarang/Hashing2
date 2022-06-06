import java.io.*;
import java.util.*;
public class DatabaseTester
{
	public static void main (String [] args) throws Exception
	{
		StudentDatabase list = new StudentDatabase ();
		
		System.out.println("Adding the following students to the database:");
		
		Student one = new Student ("Adam", "Bennet", "03/07/2002", "Neutral");
		Student two = new Student ("Charles", "Daniel", "06/18/2004", "Happy");
		Student three = new Student ("Ezekial", "Adam", "11/24/1999", "Concerned");
		
		list.add(one);
		list.add(two);
		list.add(three);
		
		list.print();
		
		// -------------------------------------------------------------------- //
		
		System.out.println("Updating Ezekial's data (concerned --> excited)");
		
		three.setData("Excited");
		list.add(three);
		
		list.print();
		
		// -------------------------------------------------------------------- //
		
		System.out.println("Printing all students whose full name contains Adam:");
		StudentDatabase adam = list.getByName("Adam");
		adam.print();
		
		// -------------------------------------------------------------------- //
		
		System.out.println("Getting the student with ID 1:");
		Student studentOne = list.getByID(1);
		studentOne.print();
		
		// -------------------------------------------------------------------- //
		
		System.out.println("\nGetting the student with composite key: Charles Daniel (06/18/2004)");
		Student studentTwo = list.getByComposite("Charles Daniel (06/18/2004)");
		studentTwo.print();
		
		// -------------------------------------------------------------------- //
		
		System.out.println("\nRemoving the student with ID 3: ");
		list.remove(list.getByID(3));
		list.print();
		
		// -------------------------------------------------------------------- //		
	}
}
class StudentDatabase
{
	private LinkedList <Student> list = new LinkedList <Student> ();
	private Hashtable <Integer, Student> IDs = new Hashtable <Integer, Student> ();
	private Hashtable <String, Student> Composites = new Hashtable <String, Student> ();
	
	public void add (Student student) throws Exception
	{
		boolean containsID = IDs.containsKey(student.getKey1());
		boolean containsComposite = Composites.containsKey(student.getKey2());
		
		if ((!containsID) && (!containsComposite)) // If it's not there add it
		{
			list.add(student);
			IDs.put(student.getKey1(), student);
			Composites.put(student.getKey2(), student);
		}
		
		else if ((containsID) && (containsComposite)) // If it is there, update it
		{
			int numberMatchingIDs = 0;
			int numberMatchingComposites = 0;
			
			for (Student current : list)
			{
				if (current.getKey1() == student.getKey1())
				{
					numberMatchingIDs++;
				}
				if (current.getKey2().equals(student.getKey2()))
				{
					numberMatchingComposites++;
				}
			}
			
			if ((numberMatchingIDs == 1) && (numberMatchingComposites == 1))
			{
				Student update = getByID(student.getKey1());
				update.setData(student.getData());
			}
			
			else
			{
				throw new Exception ("More than one copy of a key was found");
			}
		}
		
		else // If only one key is there, error!
		{
			throw new Exception ("Only one key matched.");
		}
	}
	
	public void remove (Student student)
	{
		list.remove(student);
		IDs.remove(student);
		Composites.remove(student);
	}
	
	public StudentDatabase getByName (String name) throws Exception
	{
		StudentDatabase nameStudents = new StudentDatabase ();
		
		for (Student student : list)
		{
			if (student.getName().contains(name))
			{
				nameStudents.add(student);
			}
		}
		
		return nameStudents;
	}
	
	public Student getByID (int ID)
	{
		return IDs.get(ID);
	}
	
	public Student getByComposite (String composite)
	{
		return Composites.get(composite);
	}
	
	public void print ()
	{
		System.out.println();
		for (Student student : list)
		{
			student.print();
		}
		System.out.println();
	}
}

class Student
{
	private static int StudentID = 1;
	
	private String firstName;
	private String lastName;
	private int ID;
	private String DOB;
	private String data;
	
	public Student (String firstName, String lastName, String DOB, String data)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.ID = StudentID;
		this.DOB = DOB;
		this.data = data;
		
		StudentID++;
	}
	
	public int getKey1 ()
	{
		return ID;
	}
	
	public String getKey2 ()
	{
		return firstName + " " + lastName + " (" + DOB + ")";
	}
	
	public String getName ()
	{
		return firstName + " " + lastName;
	}
	
	public String getDOB ()
	{
		return DOB;
	}
	
	public String getData ()
	{
		return data;
	}
	
	public void setData (String data)
	{
		this.data = data;
	}
	
	public void print ()
	{
		System.out.println(firstName + " " + lastName + " (" + DOB + "): " + data);
	}
}