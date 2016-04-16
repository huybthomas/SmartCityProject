/**
 * Created by Arthur on 16/03/2016.
 */
var c;
var ctx;
var cc;
var cctx;
var c1;
var ctx2;

var car = [];
var map = [];
var paths = [];

function start(){
    c = document.getElementById("mapCanvas");
    ctx = c.getContext("2d");
    cc = document.getElementById("carCanvas");
    cctx = cc.getContext("2d");
    c1 = document.getElementById("legend");
    ctx2 = c1.getContext("2d");

    drawLegend();

    drawMap();
    setInterval(getCars, 2000);
}

function drawMap(){
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
            if(map.mapTiles[j][i] == null){
                //Do nothing
            }else{
                var type = {id:""};
                type.id = map.mapTiles[j][i];
                drawTile(type, w, h, i, j);
            }
        }
    }
}

function drawCars(){
    var tilesX = map.sizeX;
    var tilesY = map.sizeY;
    var w = c.width/tilesX;
    var h = c.height/tilesY;
    if(w > h){
        w = h;
    }else{
        h = w;
    }
    cctx.clearRect(0, 0, cc.width, cc.height);
    for(var i = 0; i<car.length; i++)
        cctx.drawImage(carPic, w*car[i].loc[0]+w/4,h*car[i].loc[1]+h/4, w/2 , h/2);
}

function drawLegend() {
    ctx2.font = '20pt Arial';
    ctx2.fillText('Legend:', 10, 30);

    ctx2.font = '16pt Arial';
    ctx2.fillText('Car:', 10, 60);
    ctx2.drawImage(carPic, 10, 70);
}

function getCars(){
    $.getJSON("/update", function(result){
        car = result;
    });
    drawCars();
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

function drawTile(type, w, h, i, j){
    switch (type.id){
        case "PARKING":
            ctx.drawImage(mapPic, 0, 0, 100, 100, w*i, h*j, w, h);
            break;
        case "VERTICAL":
            ctx.drawImage(mapPic, 100, 0, 100, 100, w*i, h*j, w, h);
            break;
        case "HORIZONTAL":
            ctx.drawImage(mapPic, 200, 0, 100, 100, w*i, h*j, w, h);
            break;
        case "NORTH_EAST":
            ctx.drawImage(mapPic, 300, 0, 100, 100, w*i, h*j, w, h);
            break;
        case "NORTH_WEST":
            ctx.drawImage(mapPic, 400, 0, 100, 100, w*i, h*j, w, h);
            break;
        case "SOUTH_EAST":
            ctx.drawImage(mapPic, 500, 0, 100, 100, w*i, h*j, w, h);
            break;
        case "SOUTH_WEST":
            ctx.drawImage(mapPic, 0, 100, 100, 100, w*i, h*j, w, h);
            break;
        case "NORTH_EAST_WEST":
            ctx.drawImage(mapPic, 100, 100, 100, 100, w*i, h*j, w, h);
            break;
        case "EAST_NORTH_SOUTH":
            ctx.drawImage(mapPic, 200, 100, 100, 100, w*i, h*j, w, h);
            break;
        case "SOUTH_WEST_EAST":
            ctx.drawImage(mapPic, 300, 100, 100, 100, w*i, h*j, w, h);
            break;
        case "WEST_NORTH_SOUTH":
            ctx.drawImage(mapPic, 400, 100, 100, 100, w*i, h*j, w, h);
            break;
        default:
            ctx.rect(w*i,h*j,w,h);
            ctx.stroke();
    }
}

loadImages();
getMap();

setTimeout(start, 500);