var animation = null;

var bird1 = null;
var bird2 = null;

var requestId = undefined;
var delta = 0;

function start(){
    delta = window.performance.now();
    requestId = window.requestAnimationFrame(render);
}

function render(time){
    animation.loop((time - delta));
    requestId = window.requestAnimationFrame(render);
    delta = window.performance.now();
}

function stop(){
    if (requestId) {
        window.cancelAnimationFrame(requestId);
        requestId = undefined;
    }
}

function ready(){
    if(!bird1.ready || !bird2.ready) {
        console.log("ready : one of the bird is not ready");
        return
    }

    stop();
    animation.resize();
    animation.ready(bird1, bird2);
    start();
}

function first(){
    animation = new Bird();

    bird1 = new Image();
    bird2 = new Image();

    animation.initCanvas(bird1, bird2);

    bird1.src = 'svg/bird1.svg';
    bird1.onload = function(){
        bird1.ready = true;
        ready();
    };

    bird2.src = 'svg/bird2.svg';
    bird2.onload = function(){
        bird2.ready = true;
        ready();
    };

}
