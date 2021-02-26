package com.five.nav.datainitializaton;

import com.five.nav.repository.ArticleAuditRepository;
import com.five.nav.repository.ArticleRepository;
import com.five.nav.repository.GroupRepository;
import com.five.nav.repository.UserAuditRepository;
import com.five.nav.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@Profile("test")
@Primary
public class DatabasePreparationServiceTest implements DataPreparationServiceInterface{

  UserRepository userRepository;
  ArticleRepository articleRepository;
  GroupRepository groupRepository;

  UserAuditRepository userAuditRepository;
  ArticleAuditRepository articleAuditRepository;

  @Override
  @EventListener(ApplicationReadyEvent.class)
  public void prepare(){

    log.info("---------------Preparing data!---------------");
    cleanDatabase();
    createInitialData();
    log.info("---------------Data prepared!---------------");
  }

  public void cleanDatabase(){
    userAuditRepository.deleteAll();
    log.info("Deleted all user audits!");

    articleAuditRepository.deleteAll();
    log.info("Deleted all article audits!");

    articleRepository.deleteAll();
    log.info("Deleted all articles!");

    groupRepository.deleteAll();
    log.info("Delete all groups!");

    userRepository.deleteAll();
    log.info("Delete all users!");

  }

  public void createInitialData(){
    log.info("Didn't create initial data yet!");
  }

}
