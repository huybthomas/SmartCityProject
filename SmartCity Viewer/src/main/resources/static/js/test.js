/**
 * Created by Arthur on 16/03/2016.
 */

var carPic = new Image();
carPic.src = carImage;

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

function refresh() {
    //Draw canvas
    //Draw Tiles
    refreshTiles();
    //Draw Cars
    t = setTimeout(function () {
        refresh()
    }, 500);
}

function drawCanvas(){
    var c = document.getElementById("canvas");
    var ctx = c.getContext("2d");

    ctx.fillStyle = "#FF0000";
    ctx.fillRect(0, 0, 150, 75);

    ctx.drawImage(carPic, 0, 200);
}

function refreshTiles(){
    //Draw Tiles
}

function drawLegend() {
    var c = document.getElementById("legend");
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