/**
 * Created by Arthur on 16/03/2016.
 */
var c;
var ctx;
var c1;
var ctx2;

var carPic = new Image();
carPic.src = carImage;

var car = [];

function checkTime(i) {
    return (i < 10) ? "0" + i : i;
}

function startTime() {
    var today = new Date(),
        h = checkTime(today.getHours()),
        m = checkTime(today.getMinutes()),
        s = checkTime(today.getSeconds());
    document.getElementById('time').innerHTML = h + ":" + m + ":" + s;
    t = setTimeout(function () {
        startTime()
    }, 500);
}

function start(){
    c = document.getElementById("canvas");
    ctx = c.getContext("2d");
    c1 = document.getElementById("legend");
    ctx2 = c1.getContext("2d");

    drawLegend();

    refresh();
}

function refresh() {
    //Clear canvas
    ctx.clearRect(0, 0, c.width, c.height);
    //Draw canvas
    drawCanvas();
    //Draw Tiles
    refreshTiles();
    //Draw Cars
    drawCars();

    t = setTimeout(function () {
        refresh()
    }, 500);
}

function drawCanvas(){
    ctx.drawImage(carPic, 0, 200);
}

function refreshTiles(){
    //Draw Tiles
}

function drawLegend() {
    ctx2.font = '20pt Arial';
    ctx2.fillText('Legend:', 10, 30);

    ctx2.font = '16pt Arial';
    ctx2.fillText('Car:', 10, 60);
    ctx2.drawImage(carPic, 10, 70);
}

function drawCars(){
    if(car != null)
        var i
        for(i = 0; i<car.length; i++)
            ctx.drawImage(carPic, car[i].loc[0],car[i].loc[1]);
}

function getCars(){
    $.getJSON("/update", function(result){
        car = result;
    });
}

setInterval(getCars,1000);

startTime();

start();