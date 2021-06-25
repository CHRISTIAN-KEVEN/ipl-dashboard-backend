/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ipldashboardbackend.services;

import Utilities.ResponseObjectBuilder;
import Utilities.Utils;
import com.example.ipldashboardbackend.models.Match;
import com.example.ipldashboardbackend.repositories.MatchRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CHRISTIAN
 */
@Service
public class MatchService {
    
    @Autowired
    MatchRepository matchRepo;
    
    public String getTeamMatchesForYear(String teamName, int year) {
        List<JSONObject> matchList = new ArrayList();
        Optional<List<Match>> optMatches = matchRepo.findByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(teamName,
                                                                                LocalDate.of(year, 1, 1),
                                                                                LocalDate.of(year+1, 1, 1),
                                                                                teamName,
                                                                                LocalDate.of(year, 1, 1),
                                                                                LocalDate.of(year+1, 1, 1)
        );
        
        if(optMatches.isPresent()) {
            optMatches.get().forEach(match -> matchList.add(Utils.formMatchObject(match)));
        }
        
        return ResponseObjectBuilder.dataReturn(matchList);
    }
}
