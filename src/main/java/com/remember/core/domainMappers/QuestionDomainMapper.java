package com.remember.core.domainMappers;

import com.remember.core.domains.Platform;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.repositories.PracticeStatususRepository;

import com.remember.core.requestDtos.QuestionsRO;
import com.remember.core.tools.uriToIdConverter.BasicUriToIdConverter;
import com.remember.core.tools.uriToIdConverter.UriToIdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionDomainMapper extends BasicUriToIdConverter {
    private final PlatformsRepository platformsRepository;
    private final PracticeStatususRepository practiceStatususRepository;
    private final AlgorithmsRepository algorithmsRepository;
    private final UriToIdConverter uriToIdConverter;

    public Question toEntity(Long userId, QuestionsRO ro) {
        String status_str =  ro.getPracticeStatus();
        String platform_str = ro.getPlatform();

        Platform platform = platform_str == null ?
                null : platformsRepository.getById(uriToIdConverter.convert(platform_str));
        PracticeStatus status = status_str == null ?
                null : practiceStatususRepository.getById(uriToIdConverter.convert(status_str));

        Question question = Question.builder()
                .user(userId)
                .level(ro.getLevel())
                .practiceStatus(status)
                .platform(platform)
                .link(ro.getLink())
                .title(ro.getTitle())
                .build();

        return addAlgorithms(question, ro.getAlgorithms());
    }

    public Question toEntity(Long userId, Long id, QuestionsRO ro) {
        String status_str =  ro.getPracticeStatus();
        String platform_str = ro.getPlatform();

        Platform platform = platform_str == null ?
                null : platformsRepository.getById(uriToIdConverter.convert(platform_str));
        PracticeStatus status = status_str == null ?
                null : practiceStatususRepository.getById(uriToIdConverter.convert(status_str));

        Question question = Question.builder()
                .id(id)
                .user(userId)
                .level(ro.getLevel())
                .practiceStatus(status)
                .platform(platform)
                .link(ro.getLink())
                .title(ro.getTitle())
                .build();

        return addAlgorithms(question, ro.getAlgorithms());
    }

    private Question addAlgorithms(Question question, List<String> algorithms) {
        if(algorithms == null) return question;
        for (String algo : algorithms)
            question.addAlgorithm(algorithmsRepository.getById(uriToIdConverter.convert(algo)));
        return question;
    }
}
