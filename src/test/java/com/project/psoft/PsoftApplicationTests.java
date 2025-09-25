package com.project.psoft;

import com.project.psoft.readermanagement.model.NameV;
import com.project.psoft.readermanagement.model.Password;
import com.project.psoft.readermanagement.model.ReaderNumber;
import com.project.psoft.readermanagement.services.CreateReaderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PsoftApplicationTests {


	@Test
	void contextLoads() {
	}
	@Test
	public void test1()
	{
		CreateReaderRequest request = new CreateReaderRequest("jose@email.com", "Jose", "2002/03/28", "912345678","Sim","pass","pass");
	}
	@Test
	public void readerNumber()
	{
		ReaderNumber number = new ReaderNumber();
		for(int i= 0; i<10000; i++){
			System.out.println(number.generate(i+1L)+"\n");
		}
	}

	@Test
	public void passwordTests(){
		Password passVer = new Password();

		System.out.println(passVer.validate("pass123!"));
		System.out.println(passVer.validate("paSs123"));
		System.out.println(passVer.validate("paSs_"));
		System.out.println(passVer.validate("paSs_1"));
		System.out.println(passVer.validate("!paSs"));
		System.out.println(passVer.validate("paSs"));
	}

	@Test
	public void nameTests(){
		NameV nameVVer = new NameV();

		System.out.println(nameVVer.validate("Jose"));
		System.out.println(nameVVer.validate("Jose1"));
		System.out.println(nameVVer.validate("Jose!"));
		System.out.println(nameVVer.validate("Jose_"));
		System.out.println(nameVVer.validate("Jose Manuel"));
		System.out.println(nameVVer.validate("A character can be any letter number punctuation special character or space Each of these characters takes up one byte of space in a aaaaaaaaaaaa aaaaa"));
	}
}
