package com.remember.core.predicates;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.remember.core.domains.QQuestion;
import com.remember.core.searchParams.QuestionParams;

public class QuestionPredicate {
    public static Predicate generate(QuestionParams params){
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(titleEq(params.getTitle()));
        booleanBuilder.and(practiceStatusEq(params.getPracticeStatus()));
        return booleanBuilder;
    }

    private static BooleanExpression titleEq(String title) {
        return title == null ? null:QQuestion.question.title.contains(title);
    }
    private static BooleanExpression practiceStatusEq(Long status) {
        return status == null ? null:QQuestion.question.practiceStatus.id.eq(status);
    }
}