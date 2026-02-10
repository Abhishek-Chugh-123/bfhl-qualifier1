package com.bajaj.bfhl.bajajqualifier1.dto;

import lombok.Data;
import java.util.List;

@Data
public class BfhlRequest {

    private Integer fibonacci;
    private List<Integer> prime;
    private List<Integer> lcm;
    private List<Integer> hcf;
    private String AI;
}

