package com.wora.config;

import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.application.dto.response.PassedStageResponseDto;
import com.wora.comptetition.application.dto.response.StageResponseDto;
import com.wora.comptetition.application.dto.response.SubscribeToCompetitionResponseDto;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.comptetition.domain.entity.StageResult;
import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.entity.Team;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper()
                .registerModule(new RecordModule());

        mapper.addConverter(this::teamToResponseDto, Team.class, TeamResponseDto.class);
        mapper.addConverter(this::generalResultToDto, GeneralResult.class, SubscribeToCompetitionResponseDto.class);
        mapper.addConverter(this::stageResultToPassedStageResponseDto, StageResult.class, PassedStageResponseDto.class);
        mapper.addConverter(this::requestDtoToRider, RiderRequestDto.class, Rider.class);
        mapper.addConverter(this::riderToResponseDto, Rider.class, RiderResponseDto.class);

        return mapper;
    }

    private Rider requestDtoToRider(MappingContext<RiderRequestDto, Rider> context) {
        RiderRequestDto source = context.getSource();
        return new Rider(source.name(), source.nationality(), source.dateOfBirth(), new Team());
    }

    private RiderResponseDto riderToResponseDto(MappingContext<Rider, RiderResponseDto> context) {
        Rider source = context.getSource();
        return new RiderResponseDto(source.getId(), source.getName(), source.getNationality(), source.getDateOfBirth(),
                context.getMappingEngine().map(context.create(Team.class, TeamResponseDto.class)));
    }

    private TeamResponseDto teamToResponseDto(MappingContext<Team, TeamResponseDto> context) {
        Team source = context.getSource();
        return new TeamResponseDto(
                source.getId(),
                source.getName(),
                source.getCountry(),
                null
        );
    }

    private PassedStageResponseDto stageResultToPassedStageResponseDto(MappingContext<StageResult, PassedStageResponseDto> context) {
        final StageResult source = context.getSource();


        return new PassedStageResponseDto(
                context.getMappingEngine().map(context.create(source.getStage(), StageResponseDto.class)),
                context.getMappingEngine().map(context.create(source.getRider(), RiderResponseDto.class)),
                source.getDuration(),
                source.getPosition()
        );
    }

    private SubscribeToCompetitionResponseDto generalResultToDto(MappingContext<GeneralResult, SubscribeToCompetitionResponseDto> context) {
        final GeneralResult source = context.getSource();
        return new SubscribeToCompetitionResponseDto(
                context.getMappingEngine().map(context.create(source.getCompetition(), CompetitionResponseDto.class)),
                context.getMappingEngine().map(context.create(source.getRider(), RiderResponseDto.class))
        );
    }
}
