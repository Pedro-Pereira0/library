package com.project.psoft.authormanagement.service;

import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UpdateAuthorRequest {
    @Nullable
    private String name;
    @Nullable
    private String shortBio;
}
