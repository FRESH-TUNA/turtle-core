package com.remember.core.assemblers.user;

import com.remember.core.domains.Platform;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import com.remember.core.responseDtos.user.UserQuestionsResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserQuestionsAssemblerTest {
    private UserQuestionsAssembler assembler = new UserQuestionsAssembler();

    @Test
    @DisplayName("문제들의 리스트 결과값을 위해 직렬화에 필요한 Dto 테스트")
    void toModel_test() {
        /*
         * given
         */
        Question question = Question.builder()
                .id(1L)
                .title("title")
                .link("link")
                .user(1L)
                .level(1)
                .platform(Platform.builder().name("name").build())
                .practiceStatus(PracticeStatus.builder().status("status").build())
                .build();

        /*
         * then
         */
        UserQuestionsResponseDto dto = assembler.toModel(question);

        /*
         * when
         */
        assertThat(dto.getLink("self").get().getHref()).isEqualTo("/users/1/questions/1");
    }
}
