package com.remember.core.domainFactories;

import com.remember.core.domains.Algorithm;
import com.remember.core.domains.Platform;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.repositories.PlatformsRepository;

import com.remember.core.requests.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuestionFactory {
    private final PlatformsRepository platformsRepository;
    private final AlgorithmsRepository algorithmsRepository;

    public Question toEntity(Long userId, QuestionRequest ro) {
        String status =  ro.getPracticeStatus();
        PracticeStatus practiceStatus = PracticeStatus.valueOf(status);

        Question question = Question.builder()
                .user(userId)
                .practiceStatus(practiceStatus)
                .platform(getPlatform(ro.getPlatform()))
                .link(ro.getLink())
                .title(ro.getTitle())
                .build();

        return addAlgorithms(question, ro.getAlgorithms());
    }

    public Question toEntity(Long userId, Long id, QuestionRequest ro) {
        String status =  ro.getPracticeStatus();
        PracticeStatus practiceStatus = PracticeStatus.valueOf(status);

        Question question = Question.builder()
                .id(id)
                .user(userId)
                .practiceStatus(practiceStatus)
                .platform(getPlatform(ro.getPlatform()))
                .link(ro.getLink())
                .title(ro.getTitle())
                .build();

        return addAlgorithms(question, ro.getAlgorithms());
    }

    private Platform getPlatform(Long platformId) {
        if (Objects.isNull(platformId)) return null;
        return platformsRepository.getById(platformId);
    }

    private Question addAlgorithms(Question question, List<Long> algorithms) {
        if(algorithms == null || algorithms.isEmpty()) return question;
        for (Long algo : algorithms)
            question.addAlgorithm(getAlgorithm(algo));
        return question;
    }

    private Algorithm getAlgorithm(Long algoId) {
        return algorithmsRepository.getById(algoId);
    }
}
