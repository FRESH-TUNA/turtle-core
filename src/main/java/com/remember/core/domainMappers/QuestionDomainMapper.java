package com.remember.core.domainMappers;

import com.remember.core.domains.Platform;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.repositories.PracticeStatususRepository;

import com.remember.core.requestDtos.QuestionRequestDto;
import com.remember.core.utils.uriToIdConverter.BasicUriToIdConverter;
import com.remember.core.utils.uriToIdConverter.UriToIdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionDomainMapper extends BasicUriToIdConverter {
    private final PlatformsRepository platformsRepository;
    private final PracticeStatususRepository practiceStatususRepository;
    private final AlgorithmsRepository algorithmsRepository;
    private final UriToIdConverter uriToIdConverter;

    public Question toEntity(Object userId, QuestionRequestDto ro) {
        String status_str =  ro.getPracticeStatus();
        String platform_str = ro.getPlatform();

        Platform platform = platform_str == null ?
                null : platformsRepository.getById(uriToIdConverter.convert(platform_str));
        PracticeStatus status = status_str == null ?
                null : practiceStatususRepository.getById(uriToIdConverter.convert(status_str));

        Question question = Question.builder()
                .user((Long) userId)
                .level(ro.getLevel())
                .practiceStatus(status)
                .platform(platform)
                .link(ro.getLink())
                .title(ro.getTitle())
                .build();

        return addAlgorithms(question, ro.getAlgorithms());
    }

    public Question toEntity(Object userId, Long id, QuestionRequestDto ro) {
        String status_str =  ro.getPracticeStatus();
        String platform_str = ro.getPlatform();

        Platform platform = platform_str == null ?
                null : platformsRepository.getById(uriToIdConverter.convert(platform_str));
        PracticeStatus status = status_str == null ?
                null : practiceStatususRepository.getById(uriToIdConverter.convert(status_str));

        Question question = Question.builder()
                .id(id)
                .user((Long) userId)
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
