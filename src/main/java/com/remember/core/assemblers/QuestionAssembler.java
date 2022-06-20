package com.remember.core.assemblers;

import com.remember.core.domains.Algorithm;
import com.remember.core.domains.Question;
import com.remember.core.responses.AlgorithmResponse;
import com.remember.core.responses.PlatformResponse;
import com.remember.core.responses.PracticeStatusResponse;
import com.remember.core.utils.ServerContext;
import com.remember.core.responses.question.QuestionResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionAssembler implements RepresentationModelAssembler<Question, QuestionResponse> {
    private final AlgorithmAssembler algorithmAssembler;
    private final PracticeStatusAssembler practiceStatusAssembler;
    private final PlatformAssembler platformAssembler;

    private final String RESOURCES = "users/me/questions";

    @Override
    public QuestionResponse toModel(Question question) {
        PlatformResponse platform = platformAssembler.toModel(question.getPlatform());
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
                .map(algorithmAssembler::toModel)
                .collect(Collectors.toList());
    }
}
