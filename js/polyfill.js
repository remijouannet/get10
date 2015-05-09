/*
///////window.requestAnimationFrame///////////
var requestAnimationFrame =
    window.requestAnimationFrame ||       // According to the standard
    window.mozRequestAnimationFrame ||    // For mozilla
    window.webkitRequestAnimationFrame || // For webkit
    window.msRequestAnimationFrame ||     // For ie
    function (f) { window.setTimeout(function () { f(Date.now()); }, 1000/60); }; // If everthing else fails


///////window.cancelAnimationFrame///////////
var cancelAnimationFrame =
    window.cancelAnimationFrame ||
    window.mozCancelAnimationFrame ||
    window.webkitCancelAnimationFrame ||
    window.msCancelAnimationFrame;


///////window.performance///////////

(function(){
    if ("performance" in window == false) {
        window.performance = {};
    }
    Date.now = (Date.now || function () {  // thanks IE8
        return new Date().getTime();
    });
    if ("now" in window.performance == false){
        var nowOffset = Date.now();
        if (performance.timing && performance.timing.navigationStart){
            nowOffset = performance.timing.navigationStart
        }
        window.performance.now = function now(){
            return Date.now() - nowOffset;
        }
    }
})();
*/