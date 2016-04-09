package app.box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import app.box.Obj.Player;

/**
 * Created by user on 08.04.16.
 */
public class WalkingControl extends Actor {

    //размер джоcтика
    public static float SIZE = 40f;
    //размер движущейся части (khob)
    public static float CSIZE = 30f;

    public static float CIRCLERADIUS = 10.5f;
    public static float CONTRLRADIUS = 30F;
    //public static float Coefficient = 1F;

    //угол для определения направления
    float angle;
    //public static int Opacity = 1;
    //World world;

    //координаты отклонения khob
    protected Vector2 offsetPosition = new Vector2();

    protected Vector2 position = new Vector2();
    protected Rectangle bounds = new Rectangle();
    private Controller controller;
    Texture round_texture, center_texture;
    private final int AMENDMENT = 15;

    public WalkingControl(Vector2 pos, Controller controller) {
        this.controller = controller;
        this.position = pos;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
        round_texture = new Texture(Gdx.files.internal("buttons/round_button.png"));
        center_texture = new Texture(Gdx.files.internal("buttons/center_button.png"));
        getOffsetPosition().x = 0;
        getOffsetPosition().y = 0;
        setHeight(SIZE);
        setWidth(SIZE);
        setX(position.x);
        setY(position.y);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            //при двиджении
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                withControl(x, y);
            }

            //отпускаем джостик
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                getOffsetPosition().x = 0;
                getOffsetPosition().y = 0;
            }

        });
    }


    //отрисовка
    @Override
    public void draw(Batch batch, float parentAlfa) {

        //batch.d
        batch.draw(center_texture, getX(), getY(), getWidth(), getHeight());
        batch.draw(round_texture,
                (position.x + WalkingControl.SIZE / 2 - WalkingControl.CSIZE / 2 + getOffsetPosition().x),
                (position.y + WalkingControl.SIZE / 2 - WalkingControl.CSIZE / 2 + getOffsetPosition().y),
                WalkingControl.CSIZE, WalkingControl.CSIZE);

    }


    public Actor hit(float x, float y, boolean touchable) {
        //Процедура проверки. Если точка в прямоугольнике актёра, возвращаем актёра.
        return (((x > 0) && (x < getWidth()) && (y > 0) && (y < getHeight())) ? this : null);
    }


    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getOffsetPosition() {
        return offsetPosition;
    }

    public Rectangle getBounds() {
        return bounds;
    }


    public void withControl(float x, float y) {
        //точка касания относительно центра джойстика
        float calcX = x / -SIZE / 2;
        float calcY = y / -SIZE / 2;

        //определяем лежит ли точка касания в окружности джойстика
        if (((calcX * calcX + calcY * calcY) <= WalkingControl.CONTRLRADIUS * WalkingControl.CONTRLRADIUS)) {

            //пределяем угол касания
            double angle = Math.atan(calcY / calcX) * 180 / Math.PI;
            //System.out.println(angle);
            //угол будет в диапозоне [-90;90]. Удобнее работать, если он в диапозоне [0;360]
            //поэтому пошаманим немного
            if (angle > 0 && calcY < 0)
                angle += 180;
            if (angle < 0)
                if (calcX < 0)
                    angle = 180 + angle;
                else
                    angle += 360;

            //System.out.println(angle);
            /*//в зависимости от угла указываем направление, куда двигать игрока
            if(angle>40 && angle<140)
                ((Player)world.selectedActor).upPressed();

            if(angle>220 && angle<320)
                ((Player)world.selectedActor).downPressed();


            if(angle>130 && angle<230)
                ((Player)world.selectedActor).leftPressed();

            if(angle<50 || angle>310)
                ((Player)world.selectedActor).rightPressed();


            //двигаем игрока
            ((Player)world.selectedActor).processInput();
            */

            angle = (float) (angle * Math.PI / 180);
            //magic
            getOffsetPosition().x = (float) ((calcX * calcX + calcY * calcY > 1F) ? -Math.cos(angle)*75 : -calcX*75)-AMENDMENT;
            getOffsetPosition().y = (float) ((calcX * calcX + calcY * calcY > 1F) ? -Math.sin(angle)*75 : -calcY*75)-AMENDMENT;

        } else {
            getOffsetPosition().x = 0;
            getOffsetPosition().x = 0;
        }
        double Speed = Math.min(Math.sqrt(Math.pow(calcX, 2) * 10 + Math.pow(calcY, 2) * 10), 4.3f);
    }
}
