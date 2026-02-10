package com.bajaj.bfhl.bajajqualifier1.controller;


import com.bajaj.bfhl.bajajqualifier1.dto.ApiResponse;
import com.bajaj.bfhl.bajajqualifier1.dto.BfhlRequest;
import com.bajaj.bfhl.bajajqualifier1.service.BfhlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BfhlController {

    private final BfhlService bfhlService;

    @Value("${official.email}")
    private String EMAIL;

    @GetMapping("/health")
    public ResponseEntity<?> health(){
        return ResponseEntity.ok(
                new ApiResponse(true,EMAIL,null)
        );
    }

    @PostMapping("/bfhl")
    public ResponseEntity<?> bfhl(@RequestBody BfhlRequest bfhlRequest){
        Object result=bfhlService.handle(bfhlRequest);
        return ResponseEntity.ok(
                new ApiResponse(true,EMAIL,result)
        );
    }



}
