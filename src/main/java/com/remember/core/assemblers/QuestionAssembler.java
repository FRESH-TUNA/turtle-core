package com.remember.core.assemblers;

import com.remember.core.assemblers.AlgorithmAssembler;
import com.remember.core.assemblers.PlatformAssembler;
import com.remember.core.assemblers.PracticeStatusAssembler;
import com.remember.core.domains.Algorithm;
import com.remember.core.domains.Question;
import com.remember.core.responses.AlgorithmResponse;
import com.remember.core.responses.PlatformResponse;
import com.remember.core.responses.PracticeStatusResponse;
import com.remember.core.utils.ServerContext;
import com.remember.core.responses.question.QuestionResponse;

import com.remember.core.utils.linkBuilders.LinkBuilder;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionAssembler implements RepresentationModelAssembler<Question, QuestionResponse> {
    private final ServerContext serverContext;
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
        return addSelfLink(vo, question.getId());
    }

    /**
     * helpers
     */
    private QuestionResponse addSelfLink(QuestionResponse vo, Long id) {
        vo.add(LinkBuilder.getDetailLink(serverContext.getRoot(), RESOURCES, id).withSelfRel());
        return vo;
    }

    private List<AlgorithmResponse> algorithmsAssemble(List<Algorithm> algorithms) {
        return algorithms.stream()
                .map(algorithmAssembler::toModel)
                .collect(Collectors.toList());
    }
}
