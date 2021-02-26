package com.five.nav;

import com.five.nav.datainitializaton.DataPreparationServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class NewsArticleViewerApplication {


	public static void main(String[] args) {
		SpringApplication.run(NewsArticleViewerApplication.class, args);
	}


}
