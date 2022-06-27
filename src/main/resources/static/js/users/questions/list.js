
/*
 * components
 */
async function findall(url) {
    return axios({
        url: url,
        method: "GET",
        headers: { "Content-Type": `application/json`}
    });
}

async function question_status_update(question_url, status_url) {
    return axios({
        url: question_url,
        method: "PATCH",
        data: JSON.stringify({ practiceStatus: status_url }),
        withCredentials: true,
        headers: {
            "Content-Type": `application/json`,
            "X-CSRF-TOKEN": getCsrfToken()
        }
    });
}

function getCsrfToken() {
    return document.getElementById("X-CSRF-TOKEN").getAttribute("content");
}
/*
 * service
 */
function reload_questions(questions_url, status, algorithms, title) {
    questions_url = new URL(questions_url.split("?")[0]);

    if(title != "")
        questions_url.searchParams.append("title", title);
    if(status != 0)
        questions_url.searchParams.append("practiceStatus", status);
    if(algorithms.size != 0)
        questions_url.searchParams.append("algorithms", algorithms.join());

    window.location.href=questions_url;
}

async function question_status_update_handler(select_dom, question, old_status, new_status) {
    if (old_status != new_status) {
        try {
            await question_status_update(question, new_status);
            select_dom[0].setAttribute("data-status", new_status);
            alertify.success('업데이트 성공');
        } catch (err) {
            alertify.error('업데이트 실패! 잠시후 시도하세요');
            select_dom.selectpicker('val', old_status);
        }
    }
}

/**
 * 검색 바 상태 로딩
 */
function search_bar_state_load() {
    const url = new URL(window.location);

    const algorithms = url.searchParams.get("algorithms");
    const practiceStatus = url.searchParams.get("practiceStatus");
    const title = url.searchParams.get("title");

    if (algorithms != null)
        set_search_bar_algorithms(algorithms);

    if (practiceStatus != null)
        set_search_bar_practiceStatus(practiceStatus);

    if (title != null)
        set_search_bar_title(title);

    function set_search_bar_algorithms(algorithms) {
        algorithms = algorithms.split(",");
        algorithms_selectpicker = $(".users.questions.search .algorithms.selectpicker");
        algorithms_selectpicker.selectpicker("val", algorithms);
    }

    function set_search_bar_practiceStatus(practiceStatus) {
        selectpicker = $(".users.questions.search .practiceStatus.selectpicker");
        selectpicker.selectpicker("val", practiceStatus);
    }

    function set_search_bar_title(title) {
        input = document.querySelector(".users.questions.search .searchBar input");
        input.setAttribute("value", title);
    }
}




/**
 * event handlers
 */
document.addEventListener("DOMContentLoaded", function() {    // Handler when the DOM is fully loaded});
    /*
     * 검색바 상태 로딩
     */
    search_bar_state_load();

    /*
     * 상태 업데이트
     */
    $('.users.questions .datas select').on('changed.bs.select', async function (e, clickedIndex, isSelected, oldValue) {
        // selectpicker 메소드를 통해 조작시 clickedIndex와 isSelected는 null 이 된다.
        if (clickedIndex != null) {
            /*
             * select dom
             */
            const select_dom = $(this); // selectpicker

            /*
             * datas
             */
            const question = select_dom[0].getAttribute("data-question");
            const origin = select_dom[0].getAttribute("data-status");
            const updated = select_dom.val(); //클릭된 새로운 값
            await question_status_update_handler(select_dom, question, origin, updated);
        }
    })

    /*
     * search handler
     */
    // 상태 필터를 걸었을때
    $('.users.questions.search .practiceStatus.selectpicker').on('changed.bs.select', async function (e, clickedIndex, isSelected, oldValue) {
        // selectpicker 메소드를 통해 조작시 clickedIndex와 isSelected는 null 이 된다.
        if (clickedIndex != null) {
            document.querySelector(".users.questions.search > .searchBar button").click();
        }
    })

    // 검색 버튼을 클릭했을때
    $('.users.questions.search > .searchBar button').on('click', function () {
        const status = $(".users.questions.search .practiceStatus.selectpicker").val(); // selectpicker
        const questions_url = document
            .getElementsByClassName("users questions root")[0]
            .getAttribute("data-questions-url");
        const algorithms = $(".users.questions.search .algorithms.selectpicker").val(); // selectpicker

        const title = document
            .querySelector(".users.questions.search > .searchBar input").value;

        console.log(algorithms)
        reload_questions(questions_url, status, algorithms, title);
    });

    // 검색어를 입력한후 엔터를 쳣을때
    $('.users.questions.search > .searchBar input').on('keypress', function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            document.querySelector(".users.questions.search > .searchBar button").click();
        }
    });
});
