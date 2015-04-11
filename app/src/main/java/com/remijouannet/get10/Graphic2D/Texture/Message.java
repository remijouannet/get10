package com.remijouannet.get10.Graphic2D.Texture;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.remijouannet.get10.Graphic2D.Shape.Shape;
import com.remijouannet.get10.Graphic2D.GLRenderer;
import com.remijouannet.get10.Graphic2D.SpriteBatch;

public class Message {
    private static final String TAG = Message.class.getSimpleName();

    //--Constants--//
    public final static int CHAR_START = 32;           // First Character (ASCII Code)
    public final static int CHAR_END = 126;            // Last Character (ASCII Code)
    public final static int CHAR_CNT = ( ( ( CHAR_END - CHAR_START ) + 1 ) + 1 );  // Character Count (Including Character to use for Unknown)

    public final static int CHAR_NONE = 32;            // Character to Use for Unknown (ASCII Code)
    public final static int CHAR_UNKNOWN = ( CHAR_CNT - 1 );  // Index of the Unknown Character

    public final static int FONT_SIZE_MIN = 6;         // Minumum font Size (Pixels)
    public final static int FONT_SIZE_MAX = 180;       // Maximum font Size (Pixels)


    //--Members--//
    AssetManager assets;                               // Asset Manager
    GLRenderer glRenderer;
    int fontPadX, fontPadY;                            // font Padding (Pixels; On Each Side, ie. Doubled on Both x+y Axis)
    float fontHeight;                                  // font getHeight (Actual; Pixels)
    float fontAscent;                                  // font Ascent (Above Baseline; Pixels)
    float fontDescent;                                 // font Descent (Below Baseline; Pixels)
    LoadTexture fontTexture;
    int textureSize;                                   // texture Size for font (square) [NOTE: Public for Testing Purposes Only!]
    float charWidthMax;                                // Character getWidth (Maximum; Pixels)
    float charHeight;                                  // Character getHeight (Maximum; Pixels)
    final float[] charWidths;                          // getWidth of Each Character (Actual; Pixels)
    TextureRegion[] charRgn;                           // Region of Each Character (texture Coordinates)
    public int cellWidth, cellHeight;                         // Character Cell getWidth/getHeight
    int rowCnt, colCnt;                                // Number of Rows/columns
    float scaleX, scaleY;                              // font Scale (x,y Axis)
    float spaceX;                                      // Additional (x,y Axis) Spacing (Unscaled)

    int color;
    int size, scalesize;
    final int limitsize = 156;


    //--Drawing--//
    String text;
    Shape[] Letters;
    float[] mvpMatrix;
    float x;
    float y;

    //--Constructor--//
    // D: save program + asset manager, create arrays, and initialize the members
    public Message(GLRenderer glRenderer, AssetManager assets, String file, int size, int padX, int padY, int color) {
        this.glRenderer = glRenderer;
        this.assets = assets;                           // Save the Asset Manager Instance
        this.fontTexture = new LoadTexture(this.glRenderer);
        charWidths = new float[CHAR_CNT];               // create the Array of Character Widths
        charRgn = new TextureRegion[CHAR_CNT];          // create the Array of Character Regions

        // initialize remaining members
        fontPadX = 0;
        fontPadY = 0;

        fontHeight = 0.0f;
        fontAscent = 0.0f;
        fontDescent = 0.0f;

        textureSize = 0;

        charWidthMax = 0;
        charHeight = 0;

        cellWidth = 0;
        cellHeight = 0;
        rowCnt = 0;
        colCnt = 0;

        scaleX = 1.0f;                                  // Default Scale = 1 (Unscaled)
        scaleY = 1.0f;                                  // Default Scale = 1 (Unscaled)
        spaceX = 0.0f;
        this.color = color;
        this.size = (size >= this.limitsize)?this.limitsize:size ;
        load(file, this.size, padX, padY, color);
    }

    //--Load font--//
    // description
    //    this will load the specified font file, create a texture for the defined
    //    character range, and setup all required values used to render with it.
    // arguments:
    //    file - Filename of the font (.ttf, .otf) to use. In 'Assets' folder.
    //    size - Requested pixel size of font (height)
    //    padX, padY - Extra padding per character (x+y Axis); to prevent overlapping characters.
    private boolean load(String file, int size, int padX, int padY, int color) {
        // setup requested values
        fontPadX = padX;                                // Set Requested x Axis Padding
        fontPadY = padY;                                // Set Requested y Axis Padding

        // load the font and setup paint instance for drawing
        Typeface tf = Typeface.createFromAsset( assets, file );  // create the Typeface from font File
        Paint paint = new Paint();                      // create Android Paint Instance
        paint.setAntiAlias( true );                     // Enable Anti Alias
        paint.setTextSize( size );                      // Set Text Size
        paint.setColor( color);                   // Set ARGB (White, Opaque)
        paint.setTypeface( tf );                        // Set Typeface

        // get font metrics
        Paint.FontMetrics fm = paint.getFontMetrics();  // Get font Metrics
        fontHeight = (float)Math.ceil( Math.abs( fm.bottom ) + Math.abs( fm.top ) );  // Calculate font getHeight
        fontAscent = (float)Math.ceil( Math.abs( fm.ascent ) );  // Save font Ascent
        fontDescent = (float)Math.ceil( Math.abs( fm.descent ) );  // Save font Descent

        // determine the width of each character (including unknown character)
        // also determine the maximum character width
        char[] s = new char[2];                         // create Character Array
        charWidthMax = charHeight = 0;                  // reset Character getWidth/getHeight Maximums
        float[] w = new float[2];                       // Working getWidth Value
        int cnt = 0;                                    // Array Counter
        for ( char c = CHAR_START; c <= CHAR_END; c++ )  {  // FOR Each Character
            s[0] = c;                                    // Set Character
            paint.getTextWidths( s, 0, 1, w );           // Get Character Bounds
            charWidths[cnt] = w[0];                      // Get getWidth
            if ( charWidths[cnt] > charWidthMax )        // IF getWidth Larger Than Max getWidth
                charWidthMax = charWidths[cnt];           // Save New Max getWidth
            cnt++;                                       // Advance Array Counter
        }
        s[0] = CHAR_NONE;                               // Set Unknown Character
        paint.getTextWidths( s, 0, 1, w );              // Get Character Bounds
        charWidths[cnt] = w[0];                         // Get getWidth
        if ( charWidths[cnt] > charWidthMax )           // IF getWidth Larger Than Max getWidth
            charWidthMax = charWidths[cnt];              // Save New Max getWidth
        cnt++;                                          // Advance Array Counter

        // set character height to font height
        charHeight = fontHeight;                        // Set Character getHeight

        // find the maximum size, validate, and setup cell sizes
        cellWidth = (int)charWidthMax + ( 2 * fontPadX );  // Set Cell getWidth
        cellHeight = (int)charHeight + ( 2 * fontPadY );  // Set Cell getHeight
        int maxSize = cellWidth > cellHeight ? cellWidth : cellHeight;  // Save Max Size (getWidth/getHeight)
        if ( maxSize < FONT_SIZE_MIN || maxSize > FONT_SIZE_MAX )  // IF Maximum Size Outside Valid Bounds
            return false;                                // Return Error

        // set texture size based on max font size (width or height)
        // NOTE: these values are fixed, based on the defined characters. when
        // changing start/end characters (CHAR_START/CHAR_END) this will need adjustment too!
        if ( maxSize <= 24 )                            // IF Max Size is 18 or Less
            textureSize = 256;                           // Set 256 texture Size
        else if ( maxSize <= 40 )                       // ELSE IF Max Size is 40 or Less
            textureSize = 512;                           // Set 512 texture Size
        else if ( maxSize <= 80 )                       // ELSE IF Max Size is 80 or Less
            textureSize = 1024;                          // Set 1024 texture Size
        else                                            // ELSE IF Max Size is Larger Than 80 (and Less than FONT_SIZE_MAX)
            textureSize = 2048;                          // Set 2048 texture Size

        // create an empty bitmap (alpha only)
        Bitmap bitmap = Bitmap.createBitmap( textureSize, textureSize, Bitmap.Config.ARGB_8888 );  // create Bitmap
        bitmap.eraseColor( 0x00000000 );                // Set Transparent background (ARGB)
        Canvas canvas = new Canvas( bitmap );           // create Canvas for Rendering to Bitmap


        // calculate rows/columns
        // NOTE: while not required for anything, these may be useful to have :)
        colCnt = textureSize / cellWidth;               // Calculate Number of columns
        rowCnt = (int)Math.ceil( (float)CHAR_CNT / (float)colCnt );  // Calculate Number of Rows

        // render each of the characters to the canvas (ie. build the font map)
        float x = fontPadX;                             // Set Start Position (x)
        float y = ( cellHeight - 1 ) - fontDescent - fontPadY;  // Set Start Position (y)
        for ( char c = CHAR_START; c <= CHAR_END; c++ )  {  // FOR Each Character
            s[0] = c;                                    // Set Character to draw
            canvas.drawText( s, 0, 1, x, y, paint );     // draw Character
            x += cellWidth;                              // move to Next Character
            if ( ( x + cellWidth - fontPadX ) > textureSize )  {  // IF end of Line Reached
                x = fontPadX;                             // Set x for New Row
                y += cellHeight;                          // move Down a Row
            }
        }
        s[0] = CHAR_NONE;                               // Set Character to Use for NONE
        canvas.drawText( s, 0, 1, x, y, paint );        // draw Character

        // save the bitmap in a texture
        fontTexture.bitmap(bitmap);
        fontTexture.recycle();

        // setup the array of character texture regions
        x = 0;                                          // Initialize x
        y = 0;                                          // Initialize y
        for ( int c = 0; c < CHAR_CNT; c++ )  {         // FOR Each Character (On texture)
            charRgn[c] = new TextureRegion(fontTexture, x, y, cellWidth-1, cellHeight-1 );  // create Region for Character
            x += cellWidth;                              // move to Next Char (Cell)
            if ( x + cellWidth > textureSize )  {
                x = 0;                                    // reset x Position to Start
                y += cellHeight;                          // move to Next Row (Cell)
            }
        }

        return true;                                    // Return Success
    }

    public void draw(float[] mvpMatrix, String text, float x, float y, int scalesize)  {
        SpriteBatch.begin();

        if ((this.mvpMatrix == mvpMatrix
                && this.text.equals(text)
                && this.x == x
                && this.y == y
                && this.scalesize == scalesize)
                && Letters != null){
            SpriteBatch.addShape(mvpMatrix, Letters);
            SpriteBatch.end();
            return;
        }

        this.mvpMatrix = mvpMatrix;
        this.text = text;
        this.x = x;
        this.y = y;
        this.scalesize = scalesize;

        this.scaleX = (float)((cellHeight*scalesize)/size) / cellHeight;
        this.scaleY = (float)((cellWidth*scalesize)/size) / cellWidth;

        //Log.d(TAG, cellHeight + ";" + cellWidth);

        float chrHeight = cellHeight * scaleY;          // Calculate Scaled Character getHeight
        float chrWidth = cellWidth * scaleX;            // Calculate Scaled Character getWidth

        //Log.d(TAG, chrHeight + ";" + chrWidth);

        float letterX = x;
        float letterY = y;
        Letters = new Shape[text.length()];
        for (int i = 0; i < text.length(); i++)  {              // FOR Each Character in String
            int c = (int)text.charAt(i) - CHAR_START;  // Calculate Character Index (Offset by First Char in font)
            if (c < 0 || c >= CHAR_CNT)                // IF Character Not In font
                c = CHAR_UNKNOWN;                         // Set to Unknown Character Index
            Letters[i] = new Shape();
            Letters[i].rect.create(letterX + chrWidth / 4.5f, letterY, chrWidth, chrHeight, charRgn[c].texture());
            SpriteBatch.addShape(mvpMatrix, Letters[i]);
            letterX += (charWidths[c] + spaceX ) * scaleX;    // Advance x Position by Scaled Character getWidth
        }
        SpriteBatch.end();
    }

    public void draw(float[] mvpMatrix, String text, float x, float y)  {
        SpriteBatch.begin();

        if ((this.mvpMatrix == mvpMatrix
                && this.text.equals(text)
                && this.x == x
                && this.y == y)
                && Letters != null){
            SpriteBatch.addShape(mvpMatrix, Letters);
            SpriteBatch.end();
            return;
        }

        this.mvpMatrix = mvpMatrix;
        this.text = text;
        this.x = x;
        this.y = y;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;

        float chrHeight = (cellHeight * scaleY);          // Calculate Scaled Character getHeight
        float chrWidth = (cellWidth * scaleX);            // Calculate Scaled Character getWidth

        float letterX = x;
        float letterY = y;
        Letters = new Shape[text.length()];
        for (int i = 0; i < text.length(); i++)  {              // FOR Each Character in String
            int c = (int)text.charAt(i) - CHAR_START;  // Calculate Character Index (Offset by First Char in font)
            if (c < 0 || c >= CHAR_CNT)                // IF Character Not In font
                c = CHAR_UNKNOWN;                         // Set to Unknown Character Index
            Letters[i] = new Shape();
            Letters[i].rect.create(letterX + chrWidth / 4.5f, letterY, chrWidth, chrHeight, charRgn[c].texture());
            SpriteBatch.addShape(mvpMatrix, Letters[i]);
            letterX += (charWidths[c] + spaceX ) * scaleX;    // Advance x Position by Scaled Character getWidth
        }
        SpriteBatch.end();
    }


}
