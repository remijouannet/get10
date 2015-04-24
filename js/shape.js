function shape (vertices, colors, mvMatrix) {
    this.vertices = vertices;
    this.colors = colors;
    this.mvMatrix = mvMatrix;

    var i = 0;
    while(!(colors.length/4 === vertices.length/3)){
        if(colors.length/4 < vertices.length/3){
            colors.push(colors[i]);
            i = (i >= 3) ? 0 : i+1;
        }if(colors.length/4 > vertices.length/3){
            colors.splice(this.colors.length-1, 1);
        }
    }
}

function triangle(vertices, colors, mvMatrix) {
    while(!(vertices.length/3 === 3)){
        if(vertices.length/3 < 3){
            vertices.push(vertices[i]);
            i = (i >= 2) ? 0 : i+1;
        }if(vertices.length/3 > 3){
            vertices.splice(vertices.length-1, 1);
        }
    }

    this.shape = new shape(vertices, colors, mvMatrix);
    this.vertices = this.shape.vertices;
    this.colors = this.shape.colors;
    this.mvMatrix = this.shape.mvMatrix
}

function square(colors, mvMatrix) {
    var vertices = [
        1.0, 1.0, 0.0,
        0.0, 1.0, 0.0,
        1.0, 0.0, 0.0,
        0.0, 0.0, 0.0
    ];

    this.shape = new shape(vertices, colors, mvMatrix);
    this.vertices = this.shape.vertices;
    this.colors = this.shape.colors;
    this.mvMatrix = this.shape.mvMatrix
}

function rectangle(vertices, colors, mvMatrix) {
    while(!(vertices.length/3 === 4)){
        if(vertices.length/3 < 4){
            vertices.push(vertices[i]);
            i = (i >= 2) ? 0 : i+1;
        }if(vertices.length/3 > 4){
            vertices.splice(vertices.length-1, 1);
        }
    }

    this.shape = new shape(vertices, colors, mvMatrix);
    this.vertices = this.shape.vertices;
    this.colors = this.shape.colors;
    this.mvMatrix = this.shape.mvMatrix
}