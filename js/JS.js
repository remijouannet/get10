var animation = null;
var delta = 0;
var looping = true;

var bird1 = null;
var bird2 = null;

function loop(){
    if(animation === null){
        console.log("loop : animation not initialize");
        return
    }
    var currentTime = Date.now();

    setTimeout(function() {
        requestAnimationFrame(loop);
        animation.loop(delta);
        delta = Date.now() - currentTime;
    }, 1000 / 60);
}

function ready(){
    if(!bird1.ready || !bird2.ready) {
        console.log("ready : one of the bird is not ready");
        return
    }
    animation.resize();
    animation.ready(bird1, bird2);
    loop();
}

function start(){
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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// http://paulirish.com/2011/requestanimationframe-for-smart-animating/
// http://my.opera.com/emoller/blog/2011/12/20/requestanimationframe-for-smart-er-animating

// requestAnimationFrame polyfill by Erik Möller
// fixes from Paul Irish and Tino Zijdel

(function() {
    var lastTime = 0;
    var vendors = ['ms', 'moz', 'webkit', 'o'];
    for(var x = 0; x < vendors.length && !window.requestAnimationFrame; ++x) {
        window.requestAnimationFrame = window[vendors[x]+'RequestAnimationFrame'];
        window.cancelAnimationFrame = window[vendors[x]+'CancelAnimationFrame']
        || window[vendors[x]+'CancelRequestAnimationFrame'];
    }

    if (!window.requestAnimationFrame)
        window.requestAnimationFrame = function(callback, element) {
            var currTime = new Date().getTime();
            var timeToCall = Math.max(0, 16 - (currTime - lastTime));
            var id = window.setTimeout(function() { callback(currTime + timeToCall); },
                timeToCall);
            lastTime = currTime + timeToCall;
            return id;
        };

    if (!window.cancelAnimationFrame)
        window.cancelAnimationFrame = function(id) {
            clearTimeout(id);
        };
}());
