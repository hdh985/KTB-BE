package com.yagsog.api.user;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class MedicationsRequestDto {
    private List<MedicationsDto> medications;
}

