/**
 * Created by Arthur on 13/04/2016.
 */

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
    tile();
}

function tile(){
    var tilesX = map.sizeX;
    var tilesY = map.sizeY;
    var w = document.getElementById("myCanvas").attributes.width.value/tilesX;
    var h = document.getElementById("myCanvas").attributes.height.value/tilesY;
    if(w > h){
        w = h;
    }else{
        h = w;
    }
    for(var i=0; i<tilesY; i++){
        for(var j=0; j<tilesX; j++){
            add_image("https://pixabay.com/static/uploads/photo/2015/10/01/21/39/background-image-967820_960_720.jpg", w, h, "Image", i.toString().concat(j.toString()), i);
        }
    }
}

function add_image(src, width, height, alt, number, timesDown) {
    var tileName = "tile";
    var img = document.createElement("img");
    img.background = "url(/static/images/tiles.png)";
    img.width = width;
    img.height = height;
    img.alt = alt;
    var name = tileName.concat(number);
    img.id = name;
    document.getElementById("myCanvas").appendChild(img);
}

function getMap(){
    $.getJSON("/map", function(result){
        map = result;
    });
}

getMap();

startTime();

setTimeout(start, 500);