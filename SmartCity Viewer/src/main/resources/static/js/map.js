/**
 * Created by Arthur on 23/03/2016.
 */

//push and pop for arrays

//var tile = {"loc": []};
var tiles = [];
var size = 0;

//tile = {type:"Horiontal", x:3, y:6};
//tiles.push(tile);

function logMap(){
    var i = 0;
    for	(i = 0; i < tiles.length; i++) {
        console.log(i, ": ",tiles[i].loc);
    }
    drawCanvas2();
}

function drawCanvas2(){
    var c = document.getElementById("canvas");
    var ctx = c.getContext("2d");

    ctx.fillStyle = "#FF0000";

    ctx.drawImage(carPic, tiles[0].loc[0], tiles[0].loc[1]);
}

function getTile(){
    $.getJSON("/update", function(result){
        //console.log(result);
        //tile = result;
        tiles.push(result);
        //Logmap After the request gives no errors
        logMap();
    });
}

getTile();
getTile();
getTile();
//Dont use logMap here -> Too soon.
//logMap()
