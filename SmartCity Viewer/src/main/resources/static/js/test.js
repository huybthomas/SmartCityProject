/**
 * Created by Arthur on 16/03/2016.
 */
document.write("<p>This is a paragraph</p>");

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

function draw() {
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    ctx.fillStyle = "#FF0000";
    ctx.fillRect(0, 0, 150, 75);

    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    ctx.drawImage(carPic,0,0);
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