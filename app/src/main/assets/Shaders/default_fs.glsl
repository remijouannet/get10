//FRAGMENT SHADER
precision mediump float;
uniform int FragColor;

uniform vec4 vColor;

uniform vec4 colorOld;
uniform vec4 colorNew;

uniform sampler2D s_texture;
varying vec2 v_texCoord;

void main() {
    if(FragColor == 0){//Simple getColor
        gl_FragColor = vColor;
    }else if(FragColor == 1){//texture
      gl_FragColor = texture2D( s_texture, v_texCoord );
    }else if(FragColor == 2){//texture with getColor replacement
        gl_FragColor = texture2D( s_texture, v_texCoord );
        if(gl_FragColor.x >= colorOld.x && gl_FragColor.x <= (colorOld.x + 0.001f) &&
        gl_FragColor.y >= colorOld.y && gl_FragColor.y <= (colorOld.y + 0.001f) &&
        gl_FragColor.z >= colorOld.z && gl_FragColor.z <= (colorOld.z + 0.001f) &&
        gl_FragColor.w >= colorOld.w && gl_FragColor.w <= (colorOld.w + 0.001f)){
            gl_FragColor = colorNew;
        }
    }
}