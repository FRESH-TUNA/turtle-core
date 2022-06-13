package com.remember.core.assemblers.user;

import com.remember.core.assemblers.AlgorithmsAssembler;
import com.remember.core.assemblers.PlatformsAssembler;
import com.remember.core.assemblers.PracticeStatususAssembler;
import com.remember.core.domains.Algorithm;
import com.remember.core.domains.Question;
import com.remember.core.responses.AlgorithmResponseDto;
import com.remember.core.responses.PlatformResponseDto;
import com.remember.core.responses.PracticeStatusResponseDto;
import com.remember.core.utils.ServerContext;
import com.remember.core.responses.question.QuestionResponseDto;

import com.remember.core.utils.linkBuilders.LinkBuilder;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsersMeQuestionAssembler implements RepresentationModelAssembler<Question, QuestionResponseDto> {
    private final ServerContext serverContext;
    private final AlgorithmsAssembler algorithmsAssembler;
    private final PracticeStatususAssembler practiceStatususAssembler;
    private final PlatformsAssembler platformsAssembler;

    private final String RESOURCES = "users/me/questions";

    @Override
    public QuestionResponseDto toModel(Question question) {
        PlatformResponseDto platform = platformsAssembler.toModel(question.getPlatform());
        PracticeStatusResponseDto practiceStatus = practiceStatususAssembler.toModel(question.getPracticeStatus());
        List<AlgorithmResponseDto> algorithms = algorithmsAssemble(question.getAlgorithms());

        QuestionResponseDto vo = QuestionResponseDto.of(question, platform, practiceStatus, algorithms);
        return addSelfLink(vo, question.getId());
    }

    /**
     * helpers
     */
    private QuestionResponseDto addSelfLink(QuestionResponseDto vo, Long id) {
        vo.add(LinkBuilder.getDetailLink(serverContext.getRoot(), RESOURCES, id).withSelfRel());
        return vo;
    }

    private List<AlgorithmResponseDto> algorithmsAssemble(List<Algorithm> algorithms) {
        return algorithms.stream()
                .map(algorithmsAssembler::toModel)
                .collect(Collectors.toList());
    }
}
