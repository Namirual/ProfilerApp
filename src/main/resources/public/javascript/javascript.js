function sendAnswers() {
    var header = $("meta[name='csrf_header']").attr("content");
    var token = $("meta[name='csrf-token']").attr("content");

    var dataToSend = JSON.stringify({
        sender: "not implemented",
        answer: getAnswers()
    });

    $.ajax({
        url: window.location.pathname,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        type: 'post',

        beforeSend: function(xhr){xhr.setRequestHeader(header, token);},

        data: dataToSend
    });

    alert(dataToSend);
    location.reload();
}

function getAnswers() {
    allAnswers = "";

    luku = 1;

    while (true) {
        if (document.getElementById("question" + luku) != null) {
            allAnswers += getAnswer(luku);
            luku++;
        } else
            break;
    }
    return allAnswers;
}

function getAnswer(luku) {
    var e = document.getElementById("question" + luku);
    var strUser = e.options[e.selectedIndex].value;
    return strUser;
}