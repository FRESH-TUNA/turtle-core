package com.remember.core.repositories.question;

import com.remember.core.configs.BeanConfig;
import com.remember.core.domains.Platform;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.repositories.PracticeStatususRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private PracticeStatususRepository practiceStatususRepository;

    @Test
    @DisplayName("page 별로 유저가 생성한 문제들을 읽어오는 테스트")
    void findAll() {
        /*
         * given
         */
        Platform platform = create_platform();
        PracticeStatus practiceStatus = create_practiceStatus();
        List<Question> questions = new ArrayList<>();
        questions.add(create_question(platform, practiceStatus));
        questions.add(create_question(platform, practiceStatus));

        /*
         * then
         */
        Pageable pageable1 = PageRequest.of(0, 1);
        Pageable pageable2 = PageRequest.of(0, 2);
        Page<Question> res1 =  questionRepository.findAll(pageable1, 1L);
        Page<Question> res2 = questionRepository.findAll(pageable2, 1L);

        /*
         * when
         */
        assertThat(res1.getNumberOfElements()).isEqualTo(1);
        assertThat(res1.getTotalElements()).isEqualTo(2);
        assertThat(res2.getNumberOfElements()).isEqualTo(2);
        assertThat(res2.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("id로 문제 하나를 읽어오는 테스트")
    void findById() {
        /*
         * given
         */
        Platform platform = create_platform();
        PracticeStatus practiceStatus = create_practiceStatus();
        Question question = create_question(platform, practiceStatus);


        /*
         * then
         */
        Optional<Question> created_question = questionRepository.findById(question.getId());
        Optional<Question> not_found = questionRepository.findById(-1L);

        /*
         * when
         */
        assertThat(created_question.get()).isEqualTo(question);
        assertThat(not_found.isPresent()).isEqualTo(false);
    }

    /*
     * helpers
     */
    private Question create_question(Platform platform, PracticeStatus practiceStatus) {
        return questionRepository.saveAndFlush(
                Question.builder()
                        .title("title").link("link").level(1).user(1L)
                        .platform(platform).practiceStatus(practiceStatus)
                        .build()
        );
    }

    private Platform create_platform() {
        return platformsRepository.saveAndFlush(Platform.builder().name("platform").link("link").build());
    }

    private PracticeStatus create_practiceStatus() {
        return practiceStatususRepository.saveAndFlush(PracticeStatus.builder().status("status").color("color").build());
    }
}