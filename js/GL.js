function GL (width, height) {
    this.canvas = null;
    this.gl = null;
    this.shaderProgram = null;

    this.VerticesBuffer = null;
    this.VerticesColorBuffer = null;

    this.vertexPositionAttribute = null;
    this.vertexColorAttribute = null;

    this.mvMatrix = null;
    this.perspectiveMatrix = null;

    this.width = width;
    this.height = height;

    this.init = function () {
        this.canvas = document.getElementById("glcanvas");
        this.canvas.width = this.width;
        this.canvas.height = this.height;

        this.initWebGL(this.canvas);

        if (this.gl) {
            this.gl.clearColor(1.0, 1.0, 1.0, 1.0);
            this.gl.clearDepth(1.0);
            this.gl.enable(this.gl.DEPTH_TEST);
            this.gl.depthFunc(this.gl.LEQUAL);
            this.initShaders();
        }
    }

    this.draw = function (shape) {
        if (this.gl) {
            this.initBuffers(shape);
            this.drawing(shape);
        }
    }


    this.initWebGL = function () {
        try {
            this.gl = this.canvas.getContext("experimental-webgl");
        }
        catch (e) {
        }

        if (!this.gl) {
            alert("Unable to initialize WebGL. Your browser may not support it.");
        }
    }

    this.initBuffers = function (shape) {
        this.VerticesBuffer = this.gl.createBuffer();
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.VerticesBuffer);
        this.gl.bufferData(this.gl.ARRAY_BUFFER, new Float32Array(shape.vertices), this.gl.STATIC_DRAW);

        this.VerticesColorBuffer = this.gl.createBuffer();
        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.VerticesColorBuffer);
        this.gl.bufferData(this.gl.ARRAY_BUFFER, new Float32Array(shape.colors), this.gl.STATIC_DRAW);
    }

    this.drawing = function (shape) {
        this.gl.clear(this.gl.COLOR_BUFFER_BIT | this.gl.DEPTH_BUFFER_BIT);

        var AspectRatio = this.canvas.width / this.canvas.height;


        this.perspectiveMatrix = makePerspective(45, AspectRatio, 0.1, 100.0);

        /*var mvTranslate = Matrix.Translation($V([0.0, 0.0, -6.0])).ensure4x4();
        this.mvMatrix = Matrix.I(4);
        this.mvMatrix = this.mvMatrix.x(mvTranslate);*/

        this.mvMatrix = mvTranslate(shape.mvMatrix);

        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.VerticesBuffer);
        this.gl.vertexAttribPointer(this.vertexPositionAttribute, 3, this.gl.FLOAT, false, 0, 0);

        this.gl.bindBuffer(this.gl.ARRAY_BUFFER, this.VerticesColorBuffer);
        this.gl.vertexAttribPointer(this.vertexColorAttribute, 4, this.gl.FLOAT, false, 0, 0);

        var pUniform = this.gl.getUniformLocation(this.shaderProgram, "uPMatrix");
        var mvUniform = this.gl.getUniformLocation(this.shaderProgram, "uMVMatrix");

        this.gl.uniformMatrix4fv(pUniform, false, new Float32Array(this.perspectiveMatrix.flatten()));
        this.gl.uniformMatrix4fv(mvUniform, false, new Float32Array(this.mvMatrix.flatten()));

        this.gl.drawArrays(this.gl.TRIANGLE_STRIP, 0, shape.vertices.length / 3);
    }

    this.initShaders = function () {
        var fragmentShader = this.getShader(this.gl, "shader-fs");
        var vertexShader = this.getShader(this.gl, "shader-vs");

        this.shaderProgram = this.gl.createProgram();
        this.gl.attachShader(this.shaderProgram, vertexShader);
        this.gl.attachShader(this.shaderProgram, fragmentShader);
        this.gl.linkProgram(this.shaderProgram);

        if (!this.gl.getProgramParameter(this.shaderProgram, this.gl.LINK_STATUS)) {
            alert("Unable to initialize the shader program.");
        }

        this.gl.useProgram(this.shaderProgram);

        this.vertexPositionAttribute = this.gl.getAttribLocation(this.shaderProgram, "aVertexPosition");
        this.gl.enableVertexAttribArray(this.vertexPositionAttribute);

        this.vertexColorAttribute = this.gl.getAttribLocation(this.shaderProgram, "aVertexColor");
        this.gl.enableVertexAttribArray(this.vertexColorAttribute);
    }

    this.getShader = function (gl, id) {
        var shaderScript = document.getElementById(id);

        if (!shaderScript) {
            return null;
        }

        var theSource = "";
        var currentChild = shaderScript.firstChild;

        while (currentChild) {
            if (currentChild.nodeType == 3) {
                theSource += currentChild.textContent;
            }
            currentChild = currentChild.nextSibling;
        }

        var shader;

        if (shaderScript.type == "x-shader/x-fragment") {
            shader = this.gl.createShader(this.gl.FRAGMENT_SHADER);
        } else if (shaderScript.type == "x-shader/x-vertex") {
            shader = this.gl.createShader(this.gl.VERTEX_SHADER);
        } else {
            return null;
        }

        this.gl.shaderSource(shader, theSource);
        this.gl.compileShader(shader);

        if (!this.gl.getShaderParameter(shader, this.gl.COMPILE_STATUS)) {
            alert("An error occurred compiling the shaders: " + this.gl.getShaderInfoLog(shader));
            return null;
        }

        return shader;
    }

    function mvTranslate(v) {
        var mTranslate = Matrix.Translation($V([v[0], v[1], v[2]])).ensure4x4();
        var matrix = Matrix.I(4);
        matrix = matrix.x(mTranslate);
        return matrix;
    }

    this.init();
}
