precision mediump float;
uniform int FragColor;

uniform vec4 vColor;

uniform sampler2D s_texture;
varying vec2 v_texCoord;

void main() {
    if(FragColor == 0){
        gl_FragColor = vColor;
    }else if(FragColor == 1){
      gl_FragColor = texture2D( s_texture, v_texCoord );
    }
}