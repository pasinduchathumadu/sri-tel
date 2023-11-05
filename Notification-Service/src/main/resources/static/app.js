var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
        stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    });
}

setInterval(() => {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}, 2000);


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function showGreeting(message) {
    console.log(message);
    let html = '';
    message.forEach((msg) => {
        html += "<tr><td><b>" + msg.type + "</b></td><td>" + msg.message + "</td>><td>" + msg.createdAt + "</td></tr>";
    });
    $("#greetings").html(html);
}

window.onload = function() {
    connect();
};


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#disconnect" ).click(function() { disconnect(); });
});

