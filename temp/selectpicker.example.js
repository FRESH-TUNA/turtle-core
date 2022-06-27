/*
 * state variables
 */
let ALGORITHMS_FETCHED = false;
let PLATFORMS_FETCHERD = false;

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

/*
 * 플랫폼 정보를 읽어옴
 */
$('.users.questions.create-modal .platforms.selectpicker')
    .on('show.bs.select', async function (e, clickedIndex, isSelected, previousValue) {
        if(!PLATFORMS_FETCHERD) {
            try {
                await platforms_fetch_when_select_click($(this));
                PLATFORMS_FETCHERD = true;
            } catch (e) {
                alertify.error('서버 장애가 발생했습니다. 잠시후 다시 시도하세요');
                e.target.setAttribute("title", "로딩실패");
            }
        }
});
