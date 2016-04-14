/**
 * Created by Arthur on 16/03/2016.
 */
var c;
var ctx;
var c1;
var ctx2;

var car = [];
var map = [];

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
    /*
    t = setTimeout(function () {
        refresh()
    }, 2000);
    */
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
            var type = {id:""};
            type.id = map.mapTiles[j][i];
            drawTile(type, w, h, i, j);
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

function drawTile(type, w, h, i, j){
    switch (type.id){
        case "POINT":
            ctx.drawImage(mapPic, 0, 0, 100, 100, w*i, h*j, w, h)
            break;
        case "VERTICAL":
            ctx.drawImage(mapPic, 100, 0, 100, 100, w*i, h*j, w, h)
            break;
        case "HORIZONTAL":
            ctx.drawImage(mapPic, 200, 0, 100, 100, w*i, h*j, w, h)
            break;
        case "NORTH_EAST":
            ctx.drawImage(mapPic, 300, 0, 100, 100, w*i, h*j, w, h)
            break;
        case "NORTH_WEST":
            ctx.drawImage(mapPic, 400, 0, 100, 100, w*i, h*j, w, h)
            break;
        case "SOUTH_EAST":
            ctx.drawImage(mapPic, 500, 0, 100, 100, w*i, h*j, w, h)
            break;
        case "SOUTH_WEST":
            ctx.drawImage(mapPic, 0, 100, 100, 100, w*i, h*j, w, h)
            break;
        case "NORTH_EAST_WEST":
            ctx.drawImage(mapPic, 100, 100, 100, 100, w*i, h*j, w, h)
            break;
        case "EAST_NORTH_SOUTH":
            ctx.drawImage(mapPic, 100, 200, 100, 100, w*i, h*j, w, h)
            break;
        case "SOUTH_WEST_EAST":
            ctx.drawImage(mapPic, 100, 300, 100, 100, w*i, h*j, w, h)
            break;
        case "WEST_NORTH_SOUTH":
            ctx.drawImage(mapPic, 100, 400, 100, 100, w*i, h*j, w, h)
            break;
        default:
            ctx.rect(w*i,h*j,w,h);
    }
}

loadImages();
loadTiles();
getMap();
//setInterval(getCars,1000);

start();