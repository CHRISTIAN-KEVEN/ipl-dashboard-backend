/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ipldashboardbackend.controllers;

import com.example.ipldashboardbackend.services.MatchService;
import com.example.ipldashboardbackend.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author CHRISTIAN
 */
@RestController
@CrossOrigin
public class TeamController {
    
    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchService matchService;
    
    @GetMapping("/teams/{teamName}")
    public String getTeam(@PathVariable String teamName) {
        return teamService.getTeam(teamName);
    }
    
    @GetMapping("/teams/{teamName}/matches")
    public String getTeamMatchesForYear(@PathVariable String teamName, @RequestParam int year) {
        return this.matchService.getTeamMatchesForYear(teamName, year);
    }
} 
