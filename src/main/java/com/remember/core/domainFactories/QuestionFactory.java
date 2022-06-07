package com.remember.core.domainFactories;

import com.remember.core.domains.Platform;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.repositories.PlatformsRepository;

import com.remember.core.requests.QuestionRequestDto;
import com.remember.core.utils.UriToIdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionFactory {
    private final PlatformsRepository platformsRepository;
    private final AlgorithmsRepository algorithmsRepository;

    public Question toEntity(Long userId, QuestionRequestDto ro) {
        String status =  ro.getPracticeStatus();
        String platform_str = ro.getPlatform();

        Platform platform = platformsRepository.getById(UriToIdConverter.convert(platform_str));
        PracticeStatus practiceStatus = PracticeStatus.valueOf(status);

        Question question = Question.builder()
                .user(userId)
                .practiceStatus(practiceStatus)
                .platform(platform)
                .link(ro.getLink())
                .title(ro.getTitle())
                .build();

        return addAlgorithms(question, ro.getAlgorithms());
    }

    public Question toEntity(Long userId, Long id, QuestionRequestDto ro) {
        String status =  ro.getPracticeStatus();
        String platform_str = ro.getPlatform();

        Platform platform = platform_str == null ?
                null : platformsRepository.getById(UriToIdConverter.convert(platform_str));
        PracticeStatus practiceStatus = PracticeStatus.valueOf(status);

        Question question = Question.builder()
                .id(id)
                .user(userId)
                .practiceStatus(practiceStatus)
                .platform(platform)
                .link(ro.getLink())
                .title(ro.getTitle())
                .build();

        return addAlgorithms(question, ro.getAlgorithms());
    }

    private Question addAlgorithms(Question question, List<String> algorithms) {
        if(algorithms == null) return question;
        for (String algo : algorithms)
            question.addAlgorithm(algorithmsRepository.getById(UriToIdConverter.convert(algo)));
        return question;
    }
}
