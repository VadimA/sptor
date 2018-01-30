package com.diplom.sptor.config;

import com.diplom.sptor.planning.PlanningUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by user on 29.01.2018.
 */
@Configuration
public class AppTestConfig {

    @Autowired
    PlanningUtils planningUtils;

    @Bean
    PlanningUtils getPlanningUtils(){
        return planningUtils;
    }

}
