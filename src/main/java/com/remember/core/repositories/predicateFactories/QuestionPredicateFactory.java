package com.remember.core.repositories.predicateFactories;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.QQuestion;
import com.remember.core.dtos.searchParams.QuestionParams;

import java.util.List;
import java.util.Objects;

public class QuestionPredicateFactory {
    public static Predicate generate(QuestionParams params){
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(titleEq(params.getTitle()));
        booleanBuilder.and(practiceStatusEq(params.getPracticeStatus()));
        booleanBuilder.and(algorithmIdsIn(params.getAlgorithms()));

        return booleanBuilder;
    }

    private static BooleanExpression titleEq(String title) {
        return title == null ? null:QQuestion.question.title.contains(title);
    }

    private static BooleanExpression practiceStatusEq(String status) {
        if(Objects.isNull(status)) return null;
        else return QQuestion.question.practiceStatus.eq(PracticeStatus.valueOf(status));
    }

    private static BooleanExpression algorithmIdsIn(List<Long> algorithms) {
        if(Objects.isNull(algorithms) || algorithms.isEmpty()) return null;
        else return QQuestion.question.algorithms.any().id.in(algorithms);
    }
}
