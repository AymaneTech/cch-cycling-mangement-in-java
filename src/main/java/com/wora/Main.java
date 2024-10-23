package com.wora;

import com.wora.config.PersistenceConfig;
import com.wora.rider.application.dto.request.RiderRequestDto;
import com.wora.rider.application.dto.request.TeamRequestDto;
import com.wora.rider.application.dto.response.RiderResponseDto;
import com.wora.rider.application.dto.response.TeamResponseDto;
import com.wora.rider.application.service.RiderService;
import com.wora.rider.application.service.TeamService;
import com.wora.rider.domain.valueObject.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(PersistenceConfig.class)) {
            TeamService bean = context.getBean(TeamService.class);
            TeamResponseDto result = bean.create(new TeamRequestDto("kacm", "country"));
            bean.create(new TeamRequestDto("hehehe", "hehehehehe"));

            RiderService riderService = context.getBean(RiderService.class);
            RiderResponseDto riderResponseDto = riderService.create(new RiderRequestDto(new Name("heheh", "hehehe"), "maroc", LocalDate.now(), result.id()));
            System.out.println(riderResponseDto);
        }

    }
}