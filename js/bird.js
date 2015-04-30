
function Bird(){
    this.canvas = null;
    this.context = null;

    this.bird1 = null;
    this.bird2 = null;

    this.birdSpec= null;

    this.delta = 0;

    this.deltachangeBird = 0;

    this.deltamoveBird = 0;
    this.stepMoveBird = 0;

    this.birdSpec = function (){
        this.bird = null;
        this.x = null;
        this.y = null;
        this.height = null;
        this.width = null;
    };

    this.initCanvas = function (){
        this.canvas = document.getElementById('birdCanvas');
        this.context = this.canvas.getContext('2d');
        this.resize();
    };

    this.resize = function() {
        this.canvas.height = window.innerHeight/2;
        this.canvas.width = window.innerWidth;
        this.stepMoveBird = this.canvas.height/200;
    };

    this.ready = function(bird1, bird2){
        this.bird1 = bird1;
        this.bird2 = bird2;

        this.birdSpec = new this.birdSpec();
        this.birdSpec.bird = this.bird1;
        this.birdSpec.height = this.canvas.height/3;
        this.birdSpec.width = this.birdSpec.height * (this.bird1.width / this.bird1.height);
        this.birdSpec.x = this.canvas.width/2 - this.birdSpec.width/2;
        this.birdSpec.y = this.canvas.height/2 - this.birdSpec.height/2;
    };

    this.loop = function(delta) {
        this.delta = delta;
        this.drawing();

        if(this.deltachangeBird > (1000/10)){
            this.changeBird();
            this.deltachangeBird = 0;
        }

        if(this.deltamoveBird > (1000/60)){
            this.moveBird();
            this.deltamoveBird = 0;
        }

        this.deltachangeBird += this.delta;
        this.deltamoveBird += this.delta;
    };

    this.drawing = function() {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.context.drawImage(this.birdSpec.bird,
            this.birdSpec.x,
            this.birdSpec.y,
            this.birdSpec.width,
            this.birdSpec.height);
    };

    this.changeBird = function() {
        if(this.birdSpec.bird === this.bird1){
            this.birdSpec.bird = this.bird2;
        }else if(this.birdSpec.bird === this.bird2){
            this.birdSpec.bird = this.bird1;
        }
    };

    this.moveBird = function() {
        var min = (this.canvas.height/4 - this.birdSpec.height/2);
        var max = (this.canvas.height/1.5 - this.birdSpec.height/2);

        if(this.birdSpec.y >= max && this.stepMoveBird > 0){
            this.stepMoveBird = -1 * this.stepMoveBird;
        }else if(this.birdSpec.y <= min && this.stepMoveBird < 0){
            this.stepMoveBird = -1 * this.stepMoveBird;
        }

        this.birdSpec.y += this.stepMoveBird;
    };
}
