package com.project.psoft.forbiddenWords.repository;

import com.project.psoft.forbiddenWords.model.Forbiddenword;

public interface ForbiddenwordRepository {
    Forbiddenword save(Forbiddenword f);
    int findForbiddenword(String fw);

    boolean isForbiddenword(String name);
}
