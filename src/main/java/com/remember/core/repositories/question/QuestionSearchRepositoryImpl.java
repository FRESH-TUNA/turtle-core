package com.remember.core.repositories.question;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.remember.core.domains.QQuestion;
import com.remember.core.domains.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class QuestionSearchRepositoryImpl
        extends QuerydslRepositorySupport
        implements QuestionSearchRepository<Question, Long> {

    private final JPAQueryFactory queryFactory;

    public QuestionSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Question.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Question> findAll(Pageable pageable, Long user) {
        JPAQuery<Long> counts_query = counts_base_query(user);
        JPAQuery<Question> query = findAll_base_query(user);

        // count query all
        Long counts = counts_query.fetchOne();
        List<Question> questions = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(questions, pageable, counts);
    }

    @Override
    public Optional<Question> findById(Long id) {
        QQuestion question = QQuestion.question;

        JPAQuery<Question> query = queryFactory
                .select(question)
                .from(question)
                .innerJoin(question.platform).fetchJoin()
                .innerJoin(question.practiceStatus).fetchJoin()
                .leftJoin(question.algorithms).fetchJoin()
                .where(question.id.eq(id));

        return Optional.ofNullable(query.fetchOne());
    }

    /*
     * helper
     */
    private JPAQuery<Question> findAll_base_query(Long user) {
        QQuestion question = QQuestion.question;
        return queryFactory
                .select(question)
                .from(question)
                .innerJoin(question.platform).fetchJoin()
                .innerJoin(question.practiceStatus).fetchJoin()
                .where(question.user.eq(user));
    }

    private JPAQuery<Long> counts_base_query(Long user) {
        QQuestion question = QQuestion.question;
        return queryFactory.select(question.count())
                .from(question)
                .where(question.user.eq(user));
    }
}
