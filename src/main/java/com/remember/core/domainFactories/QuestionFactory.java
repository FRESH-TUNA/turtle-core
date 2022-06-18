package com.remember.core.domainFactories;

import com.remember.core.domains.*;
import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.repositories.PlatformsRepository;

import com.remember.core.requests.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionFactory {
    private final PlatformsRepository platformsRepository;
    private final AlgorithmsRepository algorithmsRepository;

    public Question toEntity(UserIdentityField user, QuestionRequest ro) {
        return Question.builder()
                .user(user)
                .practiceStatus(getPracticeStatus(ro))
                .platform(getPlatform(ro))
                .link(ro.getLink())
                .title(ro.getTitle())
                .algorithms(getAlgorithms(ro))
                .build();
    }

    public Question toEntity(UserIdentityField user, Long id, QuestionRequest ro) {
        return Question.builder()
                .id(id)
                .user(user)
                .practiceStatus(getPracticeStatus(ro))
                .platform(getPlatform(ro))
                .link(ro.getLink())
                .title(ro.getTitle())
                .algorithms(getAlgorithms(ro))
                .build();
    }

    private PracticeStatus getPracticeStatus(QuestionRequest ro) {
        String id = ro.getPracticeStatus();
        return StringUtils.hasText(id) ? PracticeStatus.valueOf(id) : null;
    }

    private Platform getPlatform(QuestionRequest ro) {
        Long platformId = ro.getPlatform();
        if (Objects.isNull(platformId)) return null;
        return platformsRepository.getById(platformId);
    }

    private List<Algorithm> getAlgorithms(QuestionRequest ro) {
        List<Long> ids = ro.getAlgorithms();
        if(Objects.isNull(ids) || ids.isEmpty()) return new ArrayList<>();
        return ids.stream().map(this::getAlgorithm).collect(Collectors.toList());
    }

    private Algorithm getAlgorithm(Long algoId) {
        return algorithmsRepository.getById(algoId);
    }
}
