package com.backend.similarity.controller;

import com.backend.similarity.dto.CodeDTO;
import com.backend.similarity.service.CodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/codes")
@CrossOrigin(origins = "*")
public class CodesController {

    private final CodesService codesService;

    @Autowired
    public CodesController(CodesService codesService) {
        this.codesService = codesService;
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<CodeDTO> getCodeBySubmissionId(@PathVariable String submissionId) {
        CodeDTO code = codesService.getCodeBySubmissionId(submissionId);
        if (code != null) {
            return ResponseEntity.ok(code);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}