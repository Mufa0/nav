package com.five.nav.datainitializaton;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@Profile("!test")
public class DatabasePreparationService implements DataPreparationServiceInterface{


  @Override
  @EventListener(ApplicationReadyEvent.class)
  public void prepare(){
    log.info("We are on development profile, no need for data preparation!");
  }

}
