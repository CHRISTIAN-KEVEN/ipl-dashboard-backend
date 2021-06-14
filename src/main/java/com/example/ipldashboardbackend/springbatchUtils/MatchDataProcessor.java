/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ipldashboardbackend.springbatchUtils;

import org.springframework.batch.item.ItemProcessor;
import com.example.ipldashboardbackend.models.Match;
import java.time.LocalDate;

/**
 *
 * @author CHRISTIAN
 */
public class MatchDataProcessor implements ItemProcessor<MatchInput, Match>{

//    private static final Logger log = Logger.getLogger(MatchDataProcessor.class);
    
    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        
        final Match match = new Match();
        match.setLgId(Long.valueOf(matchInput.getId()));
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setMatchWinner(matchInput.getWinner());
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        
        String firstInnings, secondInnings; 
        
        if("bat".equals(matchInput.getToss_decision())) {
            firstInnings = matchInput.getToss_winner();
            secondInnings = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
        }
        else {
            secondInnings = matchInput.getToss_winner();
            firstInnings = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
        }
        match.setTeam1(firstInnings);
        match.setTeam2(secondInnings);
        
        return match;
    }
    
}
