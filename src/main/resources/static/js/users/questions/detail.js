/*
 * state variables
 */
let ALGORITHMS_FETCHED = false;
let PLATFORMS_FETCHERD = false;


/*
 * components
 */
async function question_status_update(question_url, status_url) {
    return axios({
        url: question_url,
        method: "PATCH",
        data: JSON.stringify({ practiceStatus: status_url }),
        withCredentials: true,
        headers: { "Content-Type": `application/json`}
    });
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


/*
 * event handlers
 */
document.addEventListener("DOMContentLoaded", function() {    // Handler when the DOM is fully loaded});
    $('.users.question.root .selectpicker').on('changed.bs.select', async function (e, clickedIndex, isSelected, oldValue) {
        // selectpicker 메소드를 통해 조작시 clickedIndex와 isSelected는 null 이 된다.
        if (clickedIndex != null) {
            /*
             * select dom
             */
            const select_dom = $(this); // selectpicker

            /*
             * datas
             */
            const question = select_dom[0].getAttribute("data-question_url");
            const origin = select_dom[0].getAttribute("data-question_status");
            const updated = select_dom.val(); //클릭된 새로운 값
            await question_status_update_handler(select_dom, question, origin, updated);
        }
    })
});
