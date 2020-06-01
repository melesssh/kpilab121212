package lab2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

class EqualsTest {
	Person person;
	Person person2;
	Person person3;
	
	@Test
	public void test() {
		initFirstPerson();
		initSecondPerson();
		initThirdPerson();
		System.out.print(person.toJson());
		System.out.print(person2.toJson());
		System.out.print(person3.toJson());
		
		Assert.assertTrue(compare(person,person2));
		Assert.assertTrue(compare(person2,person3));
		Assert.assertTrue(compare(person,person3));
		Assert.assertFalse(compare(person,person3));
	}
	
	void initFirstPerson(){
		person=new Person(1,"Child");
	}
	
	void initSecondPerson(){
		person2=new Person(1,"Child");
	}

	void initThirdPerson(){
		person3=new Person(500,"NotChild");
	}
	
	boolean compare(Person p1, Person p2) {
		return p1.equals(p2);
	}
	
	public class Person {
		
	    @SerializedName("age")
		public int age;
	    
	    @SerializedName("name")
	    public String name;
		
		public Person(int age,String name)
		 {
			this.age=age;
			this.name=name;
		}
		
		public String toJson() {
			return new Gson().toJson(this);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj==null) {
				return false;
			}
			return obj==this||(obj instanceof Person&&((Person)obj).age==this.age&&((Person)obj).name.equals(this.name));
			

		}
	}

}
