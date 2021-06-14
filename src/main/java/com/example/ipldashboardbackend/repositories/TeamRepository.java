/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ipldashboardbackend.repositories;

import com.example.ipldashboardbackend.models.Team;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CHRISTIAN
 */
public interface TeamRepository extends CrudRepository<Team, Long>{
    
    Optional<Team> findByTeamName(String teamName);
}
