/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ipldashboardbackend.services;

import Utilities.ResponseObjectBuilder;
import Utilities.Utils;
import com.example.ipldashboardbackend.models.Team;
import com.example.ipldashboardbackend.repositories.MatchRepository;
import com.example.ipldashboardbackend.repositories.TeamRepository;
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
public class TeamService {;
        
    @Autowired
    TeamRepository teamRepo;
    @Autowired
    MatchRepository matchRepo;
    
    public String getTeam(String teamName){
        Optional<Team> optionalTeam = teamRepo.findByTeamName(teamName);
        JSONObject json = new JSONObject();
        if(optionalTeam.isPresent()) {  
            Team team = optionalTeam.get();
            json.put("id", team.getId())
                    .put("teamName", team.getTeamName())
                    .put("totalMatches", team.getTotalMatches())
                    .put("totalWins", team.getTotalWins());
            List<JSONObject> matches = new ArrayList();
            matchRepo.getLatestMatches(teamName, 4).ifPresent(match -> match.forEach(m -> matches.add(Utils.formMatchObject(m))));
            
            json.put("matches", matches);
                    
        }
        return ResponseObjectBuilder.dataReturn(json);
    }
}
