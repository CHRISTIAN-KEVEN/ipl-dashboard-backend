/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ipldashboardbackend.springbatchUtils;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.ipldashboardbackend.models.Match;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author CHRISTIAN
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    
    private final String[] FIELD_NAMES = new String[] { "id", "city", "date", "player_of_match", "venue", "neutral_venue", "team1", "team2",
                                                        "toss_winner", "toss_decision", "winner", "result", "result_margin", "eliminator",
                                                        "method","umpire1", "umpire2" };
    
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    
    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        
//        ClassPathResource classpathResource = new ClassPathResource("match-data.csv");
//        System.out.println("Class path stream " + classpathResource.getPath());
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("MatchItemReader")
                .resource(new ClassPathResource("match-data.csv"))
                .delimited()
                .names(FIELD_NAMES).fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>(){
                
                    {
                        setTargetType(MatchInput.class);
                    }
                }).build();
    }
    
    
    @Bean
    public  MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }
    
    @Bean 
    public JdbcBatchItemWriter<Match> writer(DataSource datasource) {
        
        return new JdbcBatchItemWriterBuilder<Match>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider())
                .sql("INSERT INTO match (lg_id, city, date, player_of_match, venue, team1, team2,"
                        + "toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2)"
                        + " VALUES(:lgId, :city, :date, :playerOfMatch, :venue, :team1, :team2,"
                        + " :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2)").dataSource(datasource).build();
    } 
    
    @Bean
    public Job importMatchInputJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory
                .get("importMatchInputJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }
    
    @Bean
    public Step step1(JdbcBatchItemWriter<Match> writer) {
        
        return stepBuilderFactory
                .get("step1")
                .<MatchInput, Match>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }   
}
