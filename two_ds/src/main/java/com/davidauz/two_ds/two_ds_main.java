package com.davidauz.two_ds;

import com.davidauz.library_module.entity.mssqlentity.Movie;
import com.davidauz.library_module.entity.mysqlentity.Actor;
import com.davidauz.library_module.repository.mssqlRepos.movieRepo;
import com.davidauz.library_module.repository.mysqlRepos.actorRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
@EntityScan
(	basePackages =
{   "com.davidauz.library_module"
,   "com.davidauz.two_ds"
})
@ComponentScan
(	basePackages =
{   "com.davidauz.library_module"
,   "com.davidauz.two_ds"
})
@EnableAutoConfiguration
public class two_ds_main {

	@Autowired
	private actorRepo actor_repo;
	@Autowired
	private movieRepo movie_repo;

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(two_ds_main.class);
		two_ds_main app = context.getBean(two_ds_main.class);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.info("List of defined Beans:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames)
				log.info(beanName);

			MSSQLObjects();
			MYSQLObjects();


		};
	}

	private void MYSQLObjects() {
		log.info("Deleting existing MYSQL objects");
		actor_repo.deleteAll();
		String[][] strData=
				{	{"Ronald McReagan","Actor turned president turned mascot"}
				,	{"Datt Mamon","Friend of Prat Bitt"}
				,	{"Prat Bitt","Friend of Datt Mamon"}
				};
		log.info("Creating MYSQL objects");
		for(String[] str_pair : strData) {
			Actor actor=new Actor();
			actor.setActor_name(str_pair[0]);
			actor.setActor_comment(str_pair[1]);
			actor_repo.save(actor);
		}
		log.info("Fetching MYSQL objects");
		List<Actor> movie_list=actor_repo.findAll();
		for(Actor actor : movie_list)
			log.info("Actor name: '"+actor.getActor_name()+"', description: '"+actor.getActor_comment()+"'\n");

	}

	private void MSSQLObjects() {
		log.info("Deleting existing MSSQL objects");
		movie_repo.deleteAll();
		String[][] strData=
		{	{"Mars or bust","Billionaries space adventures"}
		,	{"Tin Man","Cross between Oz and Marvel"}
		,	{"The incredible Bulk","Gym bro goes rampage"}
		};
		log.info("Creating MSSQL objects");
		for(String[] str_pair : strData) {
			Movie movie=new Movie();
			movie.setMovie_title(str_pair[0]);
			movie.setMovie_description(str_pair[1]);
			movie_repo.save(movie);
		}
		log.info("Fetching MSSQL objects");
		List<Movie> movie_list=movie_repo.findAll();
		for(Movie movie : movie_list)
			log.info("Movie title: '"+movie.getMovie_title()+"', description: '"+movie.getMovie_description()+"'\n");
	}

}
