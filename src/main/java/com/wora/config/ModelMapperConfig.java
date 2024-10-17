package com.wora.config;

import com.wora.comptetition.application.dto.response.CompetitionResponseDto;
import com.wora.comptetition.application.dto.response.SubscribeToCompetitionResponseDto;
import com.wora.comptetition.domain.entity.GeneralResult;
import com.wora.rider.application.dto.response.RiderResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addConverter(context -> {
            GeneralResult source = context.getSource();
            return new SubscribeToCompetitionResponseDto(
                    mapper.map(source.getCompetition(), CompetitionResponseDto.class),
                    mapper.map(source.getRider(), RiderResponseDto.class)
            );
        }, GeneralResult.class, SubscribeToCompetitionResponseDto.class);

        return mapper;
    }
}
