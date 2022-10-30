package com.remember.core.repositories.question;

import com.remember.core.domains.*;
import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.dtos.searchParams.QuestionParams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BeanConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionSearchRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PlatformsRepository platformsRepository;

    @Autowired
    private AlgorithmsRepository algorithmsRepository;

    @Test
    @DisplayName("page 별로 유저가 생성한 문제들을 읽어오는 테스트")
    void findAll() {
        /**
         * given
         * 유저별로 문제 생성
         */
        int user1_N = 2, user2_N = 3;
        UserIdentityField user1 = getUserIdentityField(1L);
        UserIdentityField user2 = getUserIdentityField(2L);

        for(int i = 0; i < user1_N; ++i)
            create_question(user1);
        for(int i = 0; i < user2_N; ++i)
            create_question(user2);

        /**
         * when
         */
        Pageable pageable1 = PageRequest.of(0, 1);
        Pageable pageable2 = PageRequest.of(0, 2);
        Page<Question> res1 =  questionRepository.findAll(pageable1, user1);
        Page<Question> res2 = questionRepository.findAll(pageable2, user2);

        /**
         * when
         */
        assertThat(res1.getNumberOfElements()).isEqualTo(1);
        assertThat(res1.getTotalElements()).isEqualTo(2);
        assertThat(res2.getNumberOfElements()).isEqualTo(2);
        assertThat(res2.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("page 별로 유저가 생성한 문제들을 읽어오는 테스트")
    void findById() {
        /**
         * given, when
         * 문제 생성
         */
        UserIdentityField user1 = getUserIdentityField(1L);
        Question question = create_question(user1);

        /**
         * when
         */
        assertThat(question).isEqualTo(questionRepository.findById(question.getId()).get());
    }

    @Test
    @DisplayName("page 별로 유저가 생성한 문제들을 이름으로 필터링에서 읽어오기 테스트")
    void findAllByTitle() {
        /**
         * given
         */
        UserIdentityField user1 = getUserIdentityField(1L);
        Platform platform = create_platform();
        PracticeStatus practiceStatus = PracticeStatus.GREAT;
        String[] titles = {"title", "1title", "title2", "titl"};
        List<Algorithm> algorithms = Collections.singletonList(create_algorithm());

        Arrays.stream(titles)
                .forEach(x -> create_question(user1, platform, algorithms, practiceStatus, x));

        /**
         * then
         */
        QuestionParams params = new QuestionParams("title", null, null);
        Pageable pageable1 = PageRequest.of(0, 1);
        Page<Question> res1 =  questionRepository.findAll(pageable1, user1, params);

        /**
         * when
         */
        assertThat(res1.getNumberOfElements()).isEqualTo(1);
        assertThat(res1.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("page 별로 유저가 생성한 문제들을 상태로 필터링에서 읽어오기 테스트")
    void findAllByStatus() {
        /**
         * given
         */
        UserIdentityField user1 = getUserIdentityField(1L);
        Platform platform = create_platform();
        String title = "title";
        List<Algorithm> algorithms = Collections.singletonList(create_algorithm());

        PracticeStatus.findAll().stream()
                .forEach(x -> create_question(user1, platform, algorithms, x, title));

        /**
         * then
         */
        QuestionParams params = new QuestionParams(null, PracticeStatus.PERFECT.name(), null);
        Pageable pageable1 = PageRequest.of(0, 1);
        Page<Question> res1 =  questionRepository.findAll(pageable1, user1, params);

        /**
         * when
         */
        assertThat(res1.getNumberOfElements()).isEqualTo(1);
        assertThat(res1.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("page 별로 유저가 생성한 문제들을 알고리즘 or로 필터링에서 읽어오기 테스트")
    void findAllByAlgorithms() {
        /**
         * given
         */
        UserIdentityField user1 = getUserIdentityField(1L);
        Platform platform = create_platform();
        String title = "title";
        String[] algorithm_titles = {"stack", "queue", "dp", "implementation"};
        PracticeStatus practiceStatus = PracticeStatus.GREAT;
        List<Algorithm> algorithms = Arrays.stream(algorithm_titles)
                .map(this::create_algorithm)
                .collect(Collectors.toList());

        List<Algorithm> algorithms1 = algorithms.subList(0, 1);
        List<Algorithm> algorithms2 = algorithms.subList(0, 2);
        List<Algorithm> algorithms3 = algorithms.subList(3, 4);

        create_question(user1, platform, algorithms1, practiceStatus, title);
        create_question(user1, platform, algorithms2, practiceStatus, title);
        create_question(user1, platform, algorithms3, practiceStatus, title);

        /**
         * then
         */
        QuestionParams params = new QuestionParams(
                null,
                null,
                Collections.singletonList(algorithms.get(0).getId()
        ));
        Pageable pageable1 = PageRequest.of(0, 1);
        Page<Question> res1 =  questionRepository.findAll(pageable1, user1, params);

        /**
         * when
         */
        assertThat(res1.getNumberOfElements()).isEqualTo(1);
        assertThat(res1.getTotalElements()).isEqualTo(2);
    }

    /*
     * helpers
     */
    private Question create_question(UserIdentityField user) {
        Platform platform = create_platform();
        PracticeStatus practiceStatus = PracticeStatus.GREAT;
        List<Algorithm> algorithms = new ArrayList<>();

        algorithms.add(create_algorithm());

        return questionRepository.saveAndFlush(
                Question.builder()
                        .title("title").link("link").user(user)
                        .platform(platform)
                        .practiceStatus(practiceStatus)
                        .algorithms(algorithms)
                        .build()
        );
    }

    private Question create_question(UserIdentityField user,
                                     Platform platform,
                                     List<Algorithm> algorithms,
                                     PracticeStatus status,
                                     String title) {

        return questionRepository.saveAndFlush(
                Question.builder()
                        .title(title).link("link").user(user)
                        .platform(platform)
                        .practiceStatus(status)
                        .algorithms(algorithms)
                        .build()
        );
    }

    private Platform create_platform() {
        return platformsRepository.saveAndFlush(Platform.builder().name("platform").link("link").build());
    }

    private Algorithm create_algorithm() {
        return algorithmsRepository.saveAndFlush(Algorithm.builder().name("알고리즘").build());
    }

    private Algorithm create_algorithm(String name) {
        return algorithmsRepository.saveAndFlush(Algorithm.builder().name(name).build());
    }

    private UserIdentityField getUserIdentityField(Long user) {
        return UserIdentityField.builder().user(user).build();
    }
}