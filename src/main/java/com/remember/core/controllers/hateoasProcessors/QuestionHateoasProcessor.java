package com.remember.core.controllers.hateoasProcessors;

import com.remember.core.domains.Algorithm;
import com.remember.core.domains.Question;
import com.remember.core.dtos.responses.AlgorithmResponse;
import com.remember.core.dtos.responses.PlatformResponse;
import com.remember.core.dtos.responses.PracticeStatusResponse;
import com.remember.core.dtos.responses.question.QuestionResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionHateoasProcessor implements RepresentationModelAssembler<Question, QuestionResponse> {
    private final PracticeStatusAssembler practiceStatusAssembler;

    private final String RESOURCES = "users/me/questions";

    @Override
    public QuestionResponse toModel(Question question) {
        PlatformResponse platform = PlatformResponse.of(question.getPlatform());
        PracticeStatusResponse practiceStatus = practiceStatusAssembler.toModel(question.getPracticeStatus());
        List<AlgorithmResponse> algorithms = algorithmsAssemble(question.getAlgorithms());

        QuestionResponse vo = QuestionResponse.of(question, platform, practiceStatus, algorithms);
        return vo.setSelfLink(RESOURCES, question.getId());
    }

    /**
     * helpers
     */
    private List<AlgorithmResponse> algorithmsAssemble(List<Algorithm> algorithms) {
        return algorithms.stream()
                .map(AlgorithmResponse::of)
                .collect(Collectors.toList());
    }
}
