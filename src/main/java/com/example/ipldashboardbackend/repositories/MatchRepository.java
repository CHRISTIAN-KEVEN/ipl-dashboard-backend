/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ipldashboardbackend.repositories;

import com.example.ipldashboardbackend.models.Match;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;  
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CHRISTIAN
 */
public interface MatchRepository extends CrudRepository<Match, Long>{
    
    public Optional<List<Match>> findByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);
    
    default public Optional<List<Match>> getLatestMatches(String teamName, int count) {
        return this.findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
    
    Optional<List<Match>> findByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
            String team1, LocalDate date1, LocalDate date2, String team2, LocalDate date3, LocalDate date4
    );
}
