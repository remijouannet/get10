package com.remijouannet.get10.Graphic2D.Texture;



public class TextureRegion {
    private static final String TAG = TextureRegion.class.getSimpleName();
    private float uvs[];
    private int GLTex;
    private int textureWidth;
    private int textureHeight;
    private Texture texture = null;

    public TextureRegion(LoadTexture loadTexture, float[] uvCoord){
        this.GLTex = loadTexture.texture().getGLTex();
        this.textureHeight = loadTexture.texture().getHeight();
        this.textureWidth = loadTexture.texture().getWidth();
        this.uvs = uvCoord;
        this.texture = new Texture(this.uvs, this.GLTex, this.textureWidth, this.textureHeight);
    }

    public TextureRegion(LoadTexture loadTexture, float x, float y, float width, float height)  {
        this.GLTex = loadTexture.texture().getGLTex();
        this.textureHeight = loadTexture.texture().getHeight();
        this.textureWidth = loadTexture.texture().getWidth();
        this.uvs = new float[]{
                x / textureWidth, (y / textureHeight + ( height / textureHeight)),
                x / textureWidth, y / textureHeight,
                (x / textureWidth + ( width / textureWidth)) , y / textureHeight,
                (x / textureWidth + ( width / textureWidth)) ,(y / textureHeight + ( height / textureHeight))

        };
        this.texture = new Texture(this.uvs, this.GLTex, this.textureWidth, this.textureHeight);
    }

    public TextureRegion(LoadTexture loadTexture, float x, float y, float size)  {
        this.GLTex = loadTexture.texture().getGLTex();
        this.textureHeight = loadTexture.texture().getHeight();
        this.textureWidth = loadTexture.texture().getWidth();
        this.uvs = new float[]{
                (x - size/2)/ textureWidth, (y + size/2)/ textureHeight,
                (x - size/2)/ textureWidth, (y - size/2)/ textureHeight,
                (x + size/2)/ textureWidth,(y - size/2)/ textureHeight,
                (x + size/2)/ textureWidth,(y + size/2)/ textureHeight
        };
        this.texture = new Texture(this.uvs, this.GLTex, this.textureWidth, this.textureHeight);
    }

    public Texture texture(){
        return this.texture;
    }
}
