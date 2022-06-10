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
        headers: { "Content-Type": `application/json`}
    });
}

function algorithms_dom_create(algorithms) {
    return algorithms.map(algorithm_dom);
}

function algorithm_dom(algorithm) {
    const dom = document.createElement("option");
    dom.setAttribute("data-content", algorithm.name);
    dom.value = algorithm._links.self.href;
    return dom;
}

function platforms_dom_create(platforms) {
    return platforms.map(platform_dom);
}

function platform_dom(platform) {
    const dom = document.createElement("option");
    dom.setAttribute("data-content", platform.name);
    dom.value = platform._links.self.href;
    return dom;
}

/*
 * service
 */

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
    const select_dom = select[0];

    const algorithms_url = document
        .getElementsByClassName("users question root")[0]
        .getAttribute("data-algorithms_url");

    const data = await findall(algorithms_url);
    const algorithms = algorithms_dom_create(data.data._embedded.algorithms);

    for(a of algorithms)
        select_dom.appendChild(a);
    select.selectpicker('refresh');
}

async function platforms_fetch_when_select_click(select) {
    const select_dom = select[0];

    const platforms_url = document
        .getElementsByClassName("users question root")[0]
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
     * 알고리즘 정보를 읽어옴
     */
    $('.users.question.update-modal .algorithms.selectpicker')
        .on('show.bs.select', async function (e, clickedIndex, isSelected, previousValue) {
            if(!ALGORITHMS_FETCHED) {
                try {
                    await algorithms_fetch_when_select_click($(this));
                    ALGORITHMS_FETCHED = true;
                } catch (e) {
                    alertify.error('서버 장애가 발생했습니다. 잠시후 다시 시도하세요');
                }
            }
        });

    // 플랫폼 정보를 읽어옴
    $('.users.question.update-modal .platforms.selectpicker')
        .on('show.bs.select', async function (e, clickedIndex, isSelected, previousValue) {
            if(!PLATFORMS_FETCHERD) {
                try {
                    await platforms_fetch_when_select_click($(this));
                    PLATFORMS_FETCHERD = true;
                } catch (e) {
                    alertify.error('서버 장애가 발생했습니다. 잠시후 다시 시도하세요');
                }
            }
        });
});
