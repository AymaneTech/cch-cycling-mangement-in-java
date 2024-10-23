package com.wora.comptetition.application.dto.response;

import com.wora.rider.application.dto.response.RiderResponseDto;
import jakarta.validation.constraints.NotNull;

public record GeneralResultResponseDto( @NotNull CompetitionResponseDto competition,
                                       @NotNull RiderResponseDto rider
) {
}
