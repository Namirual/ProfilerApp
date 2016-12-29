function addAnswer() {

    var answer = getNextNumber();
    var field = $('<input/>', {type: "textbox", size: "30", name: "answer",
        id: "answer" + answer.id, value: ""});

    var status = $('<input/>', {type: "button", onclick: "removeAnswer(" + answer + ")",
        id: "field" + answer.id, value: "-"});

    $("<div id='field" + answer + "'</div>").text("Answer:").appendTo("#answers");
    $("#field" + answer).append(field);

    $("#field" + answer).append(status);

    $("<b />").appendTo("#answers");
}

function removeAnswer(idNum) {
    $("#field" + idNum).remove();
}

function getNextNumber() {
    luku = 1;

    while (true) {
        if (document.getElementById("field" + luku) != null) {
            luku++;
        } else
            break;
    }
    return luku;

}

function sendQuestion() {

    if (checkQuestionIsFilled() == false) {
        return;
    }
    if (checkAnswersAreFilled() == false) {
        return;
    }

    var header = $("meta[name='csrf_header']").attr("content");
    var token = $("meta[name='csrf-token']").attr("content");

    var questionParam = getQuestion();

    var params = getAnswers();
    params += ("question=" + questionParam);

    $.ajax({
        url: window.location.pathname,
        //dataType: 'json',
        //contentType: 'application/json; charset=utf-8',
        type: 'post',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data: params
    });

    //alert(params);

    location.reload();
}

function getAnswers() {
    allAnswers = "";

    luku = 1;
    answerList = document.getElementsByName("answer");

    for (var x = 0; x < answerList.length; x++) {
        allAnswers += ("answer=" + answerList[x].value + "&");

    }
    return allAnswers;
}

function getQuestion() {
    return $("#question").val();
}

function checkQuestionIsFilled() {
    if ($("#question").val() === "") {
        alert("Questions may not be empty.");
        return false;
    }
    return true;
}

function checkAnswersAreFilled() {

    answerList = document.getElementsByName("answer");

    if (answerList.length < 2) {
        alert("You must provide at least two answers.");
        return false;
    }

    for (var x = 0; x < answerList.length; x++) {
        if (answerList[x].value === "") {
            alert("Please fill empty fields.");
            return false;
        }
    }
    return true;
}

$(document).ready(function () {
    addAnswer();
    addAnswer();
});

