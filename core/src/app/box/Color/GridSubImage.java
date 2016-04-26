package app.box.Color;

/**
 * Created by user on 24.04.16.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GridSubImage {
    private ShaderProgram gridShader;
    private Texture whiteTexture;
    private float gridSize;

    public GridSubImage (ShaderProgram gridShader, Texture whiteTexture, float gridSize) {
        this.gridShader = gridShader;
        this.whiteTexture = whiteTexture;
        this.gridSize = gridSize;
    }

    public void draw (Batch batch, Image parent) {
        ShaderProgram originalShader = batch.getShader();
        batch.setShader(gridShader);
        gridShader.setUniformf("u_width", parent.getWidth());
        gridShader.setUniformf("u_height", parent.getHeight());
        gridShader.setUniformf("u_gridSize", gridSize);
        batch.draw(whiteTexture, parent.getX() + parent.getImageX(), parent.getY() + parent.getImageY(),
                parent.getImageWidth() * parent.getScaleX(), parent.getImageHeight() * parent.getScaleY());
        batch.setShader(originalShader);
    }
}
