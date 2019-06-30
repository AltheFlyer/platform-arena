package game.arena.platform.terrain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import game.arena.platform.utils.Collidable;

public class Platform implements Collidable {

    private Vector2 startPoint;
    private Vector2 endPoint;

    public Platform(Vector2 start, Vector2 end) {
        this.startPoint = start;
        this.endPoint = end;
    }

    /**
     * [getAngle]
     * gets the angle of the platform
     * @return float, the angle of the platform in radians
     */
    public float getAngle() {
        float unModTheta = MathUtils.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);
        if (unModTheta > MathUtils.PI/2) {
            unModTheta = unModTheta - MathUtils.PI;
        } else if (unModTheta < -MathUtils.PI/2) {
            unModTheta = unModTheta + MathUtils.PI;
        }
        return unModTheta;
    }

    /**
     * [draw]
     * draws the platform
     * @param render
     */
    public void draw(ShapeRenderer render) {
        render.setColor(Color.BLACK);
        render.set(ShapeRenderer.ShapeType.Line);
        render.line(startPoint, endPoint);
    }

    public Vector2 getStartPoint() {
        return startPoint;
    }

    public Vector2 getEndPoint() {
        return endPoint;
    }

    @Override
    public void collide(Collidable object) {
    }
}
