/**
 * Created by Arthur on 16/03/2016.
 */

var carPic = new Image();
carPic.src = username;

function checkTime(i) {
    return (i < 10) ? "0" + i : i;
}

function startTime() {
    var today = new Date(),
        h = checkTime(today.getHours()),
        m = checkTime(today.getMinutes()),
        s = checkTime(today.getSeconds());
    document.getElementById('time').innerHTML = h + ":" + m + ":" + s;
    draw();
    t = setTimeout(function () {
        startTime()
    }, 500);
}

function drawCanvas() {
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");

    ctx.fillStyle = "#FF0000";
    ctx.fillRect(0, 0, 150, 75);

    ctx.drawImage(carPic, 0, 200);
}

function drawLegend() {
    var c = document.getElementById("myLegend");
    var ctx = c.getContext("2d");

    ctx.font = '20pt Arial';
    ctx.fillText('Legend:', 10, 30);

    ctx.font = '16pt Arial';
    ctx.fillText('Car:', 10, 60);
    ctx.drawImage(carPic, 10, 70);
}

function draw() {
    drawCanvas();
    drawLegend();
}

startTime();

function httpGetAsync(theUrl, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous
    xmlHttp.send(null);
}