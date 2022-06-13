/*
 * state variables
 */
let ALGORITHMS_FETCHED = false;
let PLATFORMS_FETCHERD = false;


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

function algorithms_dom_create(algorithms) {
    return algorithms.map(algorithm_dom);
}

function algorithm_dom(algorithm) {
    const dom = document.createElement("option");
    dom.setAttribute("data-content", algorithm.name);
    dom.value = algorithm.id;
    return dom;
}

function platforms_dom_create(platforms) {
    return platforms.map(platform_dom);
}

function platform_dom(platform) {
    const dom = document.createElement("option");
    dom.setAttribute("data-content", platform.name);
    dom.value = platform.id;
    return dom;
}

function getCsrfToken() {
    return document.getElementById("X-CSRF-TOKEN").getAttribute("content");
}
/*
 * service
 */
function reload_questions(questions_url, status, title) {
    questions_url = new URL(questions_url.split("?")[0]);

    if(title != "")
        questions_url.searchParams.append("title", title);
    if(status != 0)
        questions_url.searchParams.append("practiceStatus", status);
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

async function algorithms_fetch_when_select_click(select) {
    select.selectpicker({noneSelectedText: "로딩중..."});
    select.selectpicker('render');

    const select_dom = select[0];
    const algorithms_url = document
        .getElementsByClassName("users questions root")[0]
        .getAttribute("data-algorithms_url");

    const data = await findall(algorithms_url);
    const algorithms = await algorithms_dom_create(data.data._embedded.algorithms);

    for(a of algorithms)
        select_dom.appendChild(a);

    select.selectpicker({noneSelectedText: "선택"});
    select.selectpicker('render');
    select.selectpicker('refresh');
}

async function platforms_fetch_when_select_click(select) {
    const select_dom = select[0];

    const platforms_url = document
        .getElementsByClassName("users questions root")[0]
        .getAttribute("data-platforms_url");

    const data = await findall(platforms_url);
    const platforms = platforms_dom_create(data.data._embedded.platforms);

    for(a of platforms)
        select_dom.appendChild(a);
    select.selectpicker('refresh');
}


/*
 * event handlers
 */
document.addEventListener("DOMContentLoaded", function() {    // Handler when the DOM is fully loaded});
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
    $('.users.questions.search select').on('changed.bs.select', async function (e, clickedIndex, isSelected, oldValue) {
        // selectpicker 메소드를 통해 조작시 clickedIndex와 isSelected는 null 이 된다.
        if (clickedIndex != null) {
            const status = $(this).val(); // selectpicker
            const questions_url = document
                .getElementsByClassName("users questions root")[0]
                .getAttribute("data-questions-url");
            const title = document
                .querySelector(".users.questions.search > .searchBar > input").value;

            reload_questions(questions_url, status, title);
        }
        $(".users.questions.search .selectpicker").val()
    })

    // 검색 버튼을 클릭했을때
    $('.users.questions.search > .searchBar > button').on('click', function () {
        const status = $(".users.questions.search .selectpicker").val(); // selectpicker
        const questions_url = document
            .getElementsByClassName("users questions root")[0]
            .getAttribute("data-questions-url");
        const title = document
            .querySelector(".users.questions.search > .searchBar > input").value;

        reload_questions(questions_url, status, title);
    });

    // 검색어를 입력한후 엔터를 쳣을때
    $('.users.questions.search > .searchBar > input').on('keypress', function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            document.querySelector(".users.questions.search > .searchBar > button").click();
        }
    });

    /*
     * 알고리즘 정보를 읽어옴
     */
    $('.users.questions.create-modal .algorithms.selectpicker')
        .on('show.bs.select', async function (e, clickedIndex, isSelected, previousValue) {
            if(!ALGORITHMS_FETCHED) {
                try {
                    await algorithms_fetch_when_select_click($(this));
                    ALGORITHMS_FETCHED = true;
                } catch (e) {
                    $(this).selectpicker({noneSelectedText: "로딩 실패"});
                    $(this).selectpicker('render');
                    alertify.error('서버 장애가 발생했습니다. 잠시후 다시 시도하세요');
                }
            }
    });

    // 플랫폼 정보를 읽어옴
    // $('.users.questions.create-modal .platforms.selectpicker')
    //     .on('show.bs.select', async function (e, clickedIndex, isSelected, previousValue) {
    //         if(!PLATFORMS_FETCHERD) {
    //             try {
    //                 await platforms_fetch_when_select_click($(this));
    //                 PLATFORMS_FETCHERD = true;
    //             } catch (e) {
    //                 alertify.error('서버 장애가 발생했습니다. 잠시후 다시 시도하세요');
    //                 e.target.setAttribute("title", "로딩실패");
    //             }
    //         }
    // });
});
