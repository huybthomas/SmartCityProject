/**
 * Created by Arthur on 16/03/2016.
 */
var c;
var ctx;
var c1;
var ctx2;

var car = [];
var map = [];

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
    //Draw Tiles
    refreshTiles();


    //Draw Cars
    //drawCars();

    t = setTimeout(function () {
        refresh()
    }, 500);
}

function refreshTiles(){
    //Draw Tiles
    tile();
}

function tile(){
    var tilesX = map.sizeX;
    var tilesY = map.sizeY;
    var w = c.width/tilesX;
    var h = c.height/tilesY;
    if(w > h){
        w = h;
    }else{
        h = w;
    }
    for(var i=0; i<tilesX; i++){
        for(var j=0; j<tilesY; j++){
            ctx.rect(w*i, h*j, w, h);
            //ctx.drawImage(POINT, 50, 50);
            ctx.stroke();
        }
    }
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

function getMap(){
    $.getJSON("/map", function(result){
        map = result;
    });
}

function loadImages(){
    carPic = new Image();
    carPic.src = carImage;

    mapPic = new Image();
    mapPic.src = mapImage;
}

function loadTiles(){
    //POINT = new Image();
    //POINT.src = image.src;
    //POINT = document.getElementById("POINT");
    /*POINT,
    VERTICAL,
    HORIZONTAL,
    //bend
    NORTH_EAST,
    NORTH_WEST,
    SOUTH_EAST,
    SOUTH_WEST,
    //3way-intersect
    NORTH_EAST_WEST,
    EAST_SOUTH_NORTH,
    SOUTH_WEST_EAST,
    WEST_NORTH_SOUTH,
    //4way-intersect
    INTERSECT*/
}

loadImages();
loadTiles();
getMap();
setInterval(getCars,1000);

startTime();

start();