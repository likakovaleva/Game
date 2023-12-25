package letscode.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Panzer {
    private final float size = 100;
    private final float halfSize = size / 2;

    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2(1, 1);
    private final Vector2 origin = new Vector2();

    private final Texture texture;
    private final TextureRegion textureRegion;
    int Texture;
    int Texture1;
     double Time = 10;
    Body body;

    public Panzer(float x, float y) {
        this(x, y, "me.png");
    }

    public Panzer(float x, float y, String textureName) {
        Texture=0;
        texture = new Texture(textureName);
        textureRegion = new TextureRegion(texture);
        position.set(x, y);
        origin.set(position).add(halfSize, halfSize);
    }

    public void render(Batch batch) {
        Time = Time + (double) 1/60;
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                halfSize,
                halfSize,
                size,
                size,
                1,
                1,
                angle.angleDeg()
        );
    }

    public void dispose() {
        texture.dispose();
    }


    public Vector2 getOrigin() {
        return origin;
    }

    public void moveTo(float x, float y) {
        position.set(x, y);
        origin.set(position).add(halfSize, halfSize);
        body.setTransform(position.x + halfSize, position.y + halfSize, 0);
    }

    public void Texture(boolean Pic) {
        int Pic2;
        if(Texture!=2){
        if(Pic){Pic2=1;}
        else{Pic2=0;}
        if(Time>10){
            if(Texture != Pic2){
                textureRegion.setTexture(new Texture("fight.png"));
                Texture1 = Pic2;
            } else{
                textureRegion.setTexture(texture);
                Texture1 = 0;
            }
            Time=0;
            Texture = Texture1;
        }
        body.setUserData(Texture);}

    }


    public void diedTexture() {
        textureRegion.setTexture(new Texture("died.png"));
        Texture= 2;
    }
    public void rotateTo(float angle) {
        this.angle.setAngleDeg(angle);
    }

    public void setBody(Body body) {
        this.body = body;
        body.setTransform(position, 0);
        body.setUserData(Texture);
    }
}
