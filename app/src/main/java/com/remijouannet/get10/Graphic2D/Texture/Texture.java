package com.remijouannet.get10.Graphic2D.Texture;

import com.remijouannet.get10.Graphic2D.Tools;



public class Texture {
    private float[] uvs;
    private float[] uvsCircle;
    private int GLTex;
    private int width;
    private int height;

    float uvHeight;
    float uvWidth;
    float x;
    float y;

    public Texture(float[] uvs, int GLTex, int width, int height){
        this.uvs = uvs;
        this.GLTex = GLTex;
        this.width = width;
        this.height = height;
    }

    public Texture(Texture texture){
        this.uvs = texture.uvs;
        this.GLTex = texture.GLTex;
        this.width = texture.width;
        this.height = texture.height;
    }

    public float[] getUvs(){
        return uvs;
    }

    public int getGLTex(){
        return GLTex;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void getUvs(float[] uvs){
        this.uvs = uvs;
    }

    public void setGLTex(int GLTex){
        this.GLTex = GLTex;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public float[] getUvsCircle(){
        if (uvsCircle == null){
            this.uvHeight = Tools.getDistance(uvs[0] * this.getWidth(), uvs[1] * this.getHeight(),
                    uvs[2] * this.getWidth(), uvs[3] * this.getHeight());
            this.uvWidth = Tools.getDistance(uvs[2] * this.getWidth(), uvs[3] * this.getHeight(),
                    uvs[4] * this.getWidth(), uvs[5] * this.getHeight());
            this.x = uvs[2]*this.getWidth() + uvWidth /2;
            this.y = uvs[3]*this.getHeight() + uvHeight /2;

            float r = ((uvHeight > uvWidth)? uvWidth : uvHeight)/2;

            uvsCircle = new float[722];
            uvsCircle[0] = x /this.getWidth();
            uvsCircle[1] = y /this.getHeight();
            for (int i = 2; i < uvsCircle.length; i += 2) {
                uvsCircle[i] = (float) ((Math.cos(-i * Math.PI / (uvsCircle.length/6)) * r) + x)/this.getWidth();
                uvsCircle[i + 1] = (float) ((Math.sin(-i * Math.PI / (uvsCircle.length/6)) * r) + y)/this.getHeight();
            }
        }
        return uvsCircle;
    }
}
