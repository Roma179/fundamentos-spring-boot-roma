package com.fundamentosspring.springboot.fundamentos;

import com.fundamentosspring.springboot.fundamentos.bean.MyBean;
import com.fundamentosspring.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosspring.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosspring.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosspring.springboot.fundamentos.entity.User;
import com.fundamentosspring.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosspring.springboot.fundamentos.repository.UserRepository;
import com.fundamentosspring.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER= LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;

	private UserRepository userRepository;

	private UserService userService;
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean,MyBeanWithDependency myBeanWithDependency,MyBeanWithProperties myBeanWithProperties,UserPojo userPojo,UserRepository userRepository,
								  UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo =userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        //ejemplosAnteriores();
		saveUsersInDatabase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("test1Transactional1","test1Transactional1@gmai.com",LocalDate.now());
		User test2 = new User("test2Transactional1","test2Transactional1@gmai.com",LocalDate.now());
		User test3 = new User("test3Transactional1","test1Transactional1@gmai.com",LocalDate.now());
		User test4 = new User("test4Transactional1","test4Transactional1@gmai.com",LocalDate.now());

		List<User> users = Arrays.asList(test1,test2,test3,test4);

		try{
			userService.saveTransactional(users);
		}catch(Exception e){
			LOGGER.error("Esta es una excepetion dentro del metodo transaccional " + e);
		}

		userService.getAllUsers().
				stream()
				.forEach(user-> LOGGER.info("Este es el usuario dentro del metodo transacional "+user ));
	}

	private void getInformationJpqlFromUser(){
		/*LOGGER.info("Usuario con el metodo findByUserEmail" +
				userRepository.findByUserEmail("rutilio@gmail.com")
				.orElseThrow(()-> new RuntimeException("No se encontro el usuario")));

		userRepository.findAndSort("user", Sort.by("id").descending())
				.stream()
				.forEach(user->LOGGER.info("usuario con method sort " + user));
		userRepository.findByName("Julian")
				.stream()
				.forEach(user->LOGGER.info("User con query method " + user));
		LOGGER.info("Usuario con query method findByEmailAndName "
				+ userRepository.findByEmailAndName("rosaura@gmail.com","Rosaura")
				.orElseThrow(()-> new RuntimeException("Usuario no encontrado")));
		userRepository.findByNameLike("%n%")
				.stream()
				.forEach(user-> LOGGER.info("Usuario findByNameLike " +user));

		userRepository.findByNameOrEmail(null,"juanroma@gmail.com")
				.stream()
				.forEach(user-> LOGGER.info("Usuario findByNameOrEmail " +user));*/
		userRepository.findByBirthDateBetween(LocalDate.of(2008,1,1),LocalDate.of(2021,1,1))
				.stream()
				.forEach(user -> LOGGER.info("Usuario con intervalo de fechas "+user));
		userRepository.findByNameContainingOrderByIdDesc("user")
				.stream()
				.forEach(user->LOGGER.info("Usuario encontrado con like de forma des " + user));
		LOGGER.info("El usuario apartir del named parameter es: "+ userRepository.getAllBirthDateAndEmail(LocalDate.of(2016,04,15),"julian@gmail.com")
				.orElseThrow(()->new RuntimeException("No se encontro el usuario a partir del named parameter")));
	}

	private void saveUsersInDatabase(){
		User user1 = new User("John","jonh@gmail.com",LocalDate.of(2021,03,20));
		User user2 = new User("Julian","julian@gmail.com",LocalDate.of(2016,04,15));
		User user3 = new User("user2","jose@gmail.com",LocalDate.of(2010,04,10));
		User user4 = new User("user3","joma@gmail.com",LocalDate.of(2008,04,14));
		User user5 = new User("user4","rutilio@gmail.com",LocalDate.of(2018,04,16));
		User user6 = new User("user5","jonas@gmail.com",LocalDate.of(2019,04,18));
		User user7 = new User("user6","elvira@gmail.com",LocalDate.of(2008,04,14));
		User user8 = new User("user7","ruben@gmail.com",LocalDate.of(2007,04,16));
		User user9 = new User("user8","juanroma@gmail.com",LocalDate.of(2015,04,15));
		User user10 = new User("Rosaura","rosaura@gmail.com",LocalDate.of(2020,04,25));
		List<User> list = Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10);
		list.stream().forEach(userRepository::save);
	}

    public void ejemplosAnteriores(){
        componentDependency.saludar();
        myBean.print();
        myBeanWithDependency.printWithDependency();
        System.out.println(myBeanWithProperties.function());
        System.out.println(userPojo.getEmail()+'-'+userPojo.getPassword());
        try{
            //error
            int value = 10/0;
            LOGGER.debug("Mi valor : "+ value);
        }catch (Exception e){
            LOGGER.error("Esto esun error del al dividir entre cero "+ e.getStackTrace());
        }
    }
}
