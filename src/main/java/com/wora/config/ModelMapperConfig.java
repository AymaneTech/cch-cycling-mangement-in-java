package com.wora.config;

import com.wora.comptetition.application.dto.response.*;
import com.wora.comptetition.domain.entity.Competition;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.entity.Stage;
import com.wora.comptetition.domain.entity.StageResult;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.entity.Team;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setAmbiguityIgnored(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true);
        mapper.registerModule(new RecordModule());

        mapper.addConverter(context -> {
            Team team = context.getSource();
            return new TeamResponseDto(
                    team.getId(),
                    team.getName(),
                    team.getCountry(),
                    team.getRiders().stream().map(r ->
                                    new RiderResponseDto(r.getId(), r.getName(), r.getNationality(), r.getDateOfBirth(), null))
                            .toList()
            );
        }, Team.class, TeamResponseDto.class);

        mapper.addConverter(context -> {
            Rider rider = context.getSource();
            Team team = rider.getTeam();
            return new RiderResponseDto(rider.getId(), rider.getName(), rider.getNationality(), rider.getDateOfBirth(),
                    new TeamResponseDto(team.getId(), team.getName(), team.getCountry(), List.of()));
        }, Rider.class, RiderResponseDto.class);

        mapper.addConverter(context -> {
            Competition competition = context.getSource();
            return new CompetitionResponseDto(competition.getId(),
                    competition.getName(),
                    competition.getStartDate(),
                    competition.getEndDate(),
                    competition.getStages()
                            .stream().map(s -> mapper.map(s, EmbeddableStage.class))
                            .toList()
            );
        }, Competition.class, CompetitionResponseDto.class);

        mapper.addConverter(context -> {
            Stage stage = context.getSource();
            return new StageResponseDto(
                    stage.getId(),
                    stage.getStageNumber(),
                    stage.getDistance(),
                    stage.getStartLocation(),
                    stage.getEndLocation(),
                    stage.getDate(),
                    mapper.map(stage.getCompetition(), CompetitionResponseDto.class)
            );
        }, Stage.class, StageResponseDto.class);

        mapper.addConverter(context -> {
            Stage stage = context.getSource();
            return new EmbeddableStage(
                    stage.getId(),
                    stage.getStageNumber(),
                    stage.getDistance(),
                    stage.getStartLocation(),
                    stage.getEndLocation(),
                    stage.getDate()
            );
        }, Stage.class, EmbeddableStage.class);

        mapper.addConverter(context -> {
            GeneralResult generalResult = context.getSource();
            return new SubscribeToCompetitionResponseDto(
                    mapper.map(generalResult.getCompetition(), CompetitionResponseDto.class),
                    mapper.map(generalResult.getRider(), RiderResponseDto.class)
            );
        }, GeneralResult.class, SubscribeToCompetitionResponseDto.class);

        mapper.addConverter(context -> {
            StageResult stageResult = context.getSource();
            return new PassedStageResponseDto(
                    mapper.map(stageResult.getStage(), StageResponseDto.class),
                    mapper.map(stageResult.getRider(), RiderResponseDto.class),
                    stageResult.getDuration(),
                    stageResult.getPosition()
            );
        }, StageResult.class, PassedStageResponseDto.class);

        return mapper;
    }
}
