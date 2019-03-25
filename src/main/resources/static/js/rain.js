var canvas = document.querySelector(".rain");
    ctx = canvas.getContext("2d");

var aRain = [];

var wh = canvas.height = window.innerHeight;
var ww = canvas.width = window.innerWidth;


window.onresize=function () {
    wh = canvas.height = window.innerHeight;
    ww = canvas.width = window.innerWidth;
}


function random(min,max) {
    return Math.random()*(max-min)+min;
}

function Rain() {

}

Rain.prototype={
    init:function () {
        this.x = random(0,ww);
        this.y = 0;
        this.h = random(0.8*wh,0.9*wh);
        this.r = 1;
        this.vr = 1;
        this.vY = random(4,5);
    },
    
    draw:function () {

        if(this.y<this.h){
            ctx.beginPath();
            ctx.fillStyle="white";
            // ctx.fillStyle="#31f7f7";
            ctx.fillRect(this.x,this.y,4,10);
        }

        else {
            ctx.beginPath();
            ctx.strokeStyle = "white";
            // ctx.strokeStyle="#31f7f7";
            ctx.arc(this.x,this.y,this.r,0,2*Math.PI);
            ctx.stroke();
        }

    },

    move: function () {
        if(this.y<this.h){
            this.y+=this.vY;
        }

        else {

            if(this.r<40){
                this.r+=this.vr;
            }

            else {
                this.init();
            }

        }

        this.draw();

    }
}

function createRain(num) {
    for(var i=0;i<num;i++){

        setTimeout(function () {
            var rain = new Rain();
            rain.init();
            rain.draw();
            aRain.push(rain);
        },300*i); //ms
    }
}

createRain(45);

setInterval(function () {
    ctx.beginPath();
    // ctx.clearRect(0,0,ww,wh);
    ctx.fillStyle="rgba(0,0,0,0.05)";
    ctx.fillRect(0,0,ww,wh);

    for(var item of aRain){
        item.move();
    }

},1000/60);

