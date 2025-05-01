package com.example.preonboarding.request;

import com.example.preonboarding.options.dto.OptionDTO;
import lombok.Data;

import java.util.List;

@Data
public class OptionGroupRequest {
    private String name;
    private int displayOrder;
    private List<OptionDTO> options;
}
