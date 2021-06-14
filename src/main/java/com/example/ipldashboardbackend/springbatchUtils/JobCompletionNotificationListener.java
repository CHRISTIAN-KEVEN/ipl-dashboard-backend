/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ipldashboardbackend.springbatchUtils;

import com.example.ipldashboardbackend.models.Team;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CHRISTIAN
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport{
    public static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    
    private final EntityManager em;
    
    @Autowired
    public JobCompletionNotificationListener(EntityManager em) {
        this.em = em;
    }
    
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        
        Map<String, Team> teamData = new HashMap<>();
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("!!! JOB Terminated. Time to verify the results ");
            em.createQuery("SELECT m.team1, COUNT(*) FROM Match m GROUP BY m.team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(e -> new Team((String) e[0], (long) e[1]))
                    .forEach(team -> teamData.put(team.getTeamName() , team));
            
            em.createQuery("SELECT m.team2, COUNT(*) FROM Match m GROUP BY m.team2", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        team.setTotalWins((long) e[1]);
                            });
            
             em.createQuery("SELECT m.matchWinner, COUNT(*) FROM Match m GROUP BY m.matchWinner", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        if(team != null) 
                            team.setTotalWins((long) e[1]);
                            });
             
                 teamData.values().forEach(team -> em.persist(team));
                 teamData.values().forEach(team -> System.out.println(team));
        }
    }
    
}
