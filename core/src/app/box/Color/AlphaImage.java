package app.box.Color;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


/**
 * Created by user on 24.04.16.
 */
public class AlphaImage extends Image {
    private GridSubImage gridImage;

    ShaderProgram paletteShader;
    ShaderProgram verticalChannelShader;
    ShaderProgram hsvShader;
    ShaderProgram rgbShader;
    ShaderProgram gridShader;
    Boolean loadExtendedShaders = false;
    Texture whiteTexture;

    public AlphaImage (Texture whiteTexture, float gridSize) {
        super(whiteTexture);
        this.whiteTexture = whiteTexture;
        loadShaders();
        gridImage = new GridSubImage(gridShader, whiteTexture, gridSize);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        //don't draw grid if widget alpha is different than 1 because
        //this creates weird affect when window is fading in/out,
        //both parent image and grid is visible
        if (getColor().a != 1) gridImage.draw(batch, this);
        super.draw(batch, parentAlpha);
    }

    private void loadShaders () {
        paletteShader = loadShader("default.vert", "palette.frag");
        verticalChannelShader = loadShader("default.vert", "verticalBar.frag");
        gridShader = loadShader("default.vert", "checkerboard.frag");

        if (loadExtendedShaders) {
            hsvShader = loadShader("default.vert", "hsv.frag");
            rgbShader = loadShader("default.vert", "rgb.frag");
        }
    }

    private ShaderProgram loadShader (String vertFile, String fragFile) {

        ShaderProgram program = new ShaderProgram(Gdx.files.internal("data/"+vertFile), Gdx.files.internal("data/"+fragFile));
        if (program.isCompiled() == false) {
            throw new IllegalStateException("ColorPicker shader compilation failed. Shader: " + vertFile + ", " + fragFile + ": " + program.getLog());
        }
        program.pedantic = false;
        return program;
    }

}
