/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import com.example.ipldashboardbackend.models.Match;
import org.json.JSONObject;

/**
 *
 * @author CHRISTIAN
 */
public class Utils {
    
    public static JSONObject formMatchObject(Match match) {
        return new JSONObject()
                            .put("lgId", match.getLgId())
                            .put("city", match.getCity())
                            .put("date", match.getDate())
                            .put("matchWinner", match.getMatchWinner())
                            .put("playerOfMatch", match.getPlayerOfMatch())
                            .put("result", match.getResult())
                            .put("resultMargin", match.getResultMargin())
                            .put("team1", match.getTeam1())
                            .put("team2", match.getTeam2())
                            .put("tossDecision", match.getTossDecision())
                            .put("tossWinner", match.getTossWinner())
                            .put("umpire1", match.getUmpire1())
                            .put("umpire2", match.getUmpire2())
                            .put("venue", match.getVenue());
    }
}
