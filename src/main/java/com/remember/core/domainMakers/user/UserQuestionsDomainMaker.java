package com.remember.core.domainMakers.user;

import com.remember.core.domains.Platform;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.repositories.PracticeStatususRepository;
import com.remember.core.ros.user.UserQuestionsRO;
import com.remember.core.tools.UriToIdConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserQuestionsDomainMaker extends UriToIdConverter {
    @Autowired
    private PlatformsRepository platformsRepository;

    @Autowired
    private PracticeStatususRepository practiceStatususRepository;

    public Question toEntity(Long userId, UserQuestionsRO ro) {
        Platform platform = platformsRepository.getById(convertURItoID(ro.getPlatform()));
        PracticeStatus status = practiceStatususRepository
                .getById(convertURItoID(ro.getPracticeStatus()));

        return Question.builder()
                .user(userId)
                .level(ro.getLevel())
                .practiceStatus(status)
                .platform(platform)
                .link(ro.getLink())
                .title(ro.getTitle())
                .build();
    }
}
