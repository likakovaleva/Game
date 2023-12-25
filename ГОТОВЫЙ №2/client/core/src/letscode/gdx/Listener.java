package letscode.gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Listener implements ContactListener {
    Array<Fixture> died=new Array<>();
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA=contact.getFixtureA();
        Fixture fixtureB=contact.getFixtureB();
        int a= (int) fixtureA.getBody().getUserData();
        int b= (int) fixtureB.getBody().getUserData();
       //Проверяем какая конкретно текстура ежик или колючка.
        if(a == 1 && b==0){
            died.add(fixtureB);
        }
        else if(b == 1 && a==0){
            died.add(fixtureA);
        }
        else{
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void endContact(Contact contact) {

    }
    public Array<Fixture> getDied(){
        return died;
    }


}
/*
1. создали списки столкнувшихся
2. получаем значения текстуры
3. если они разные то записываем их в список столкнувшихся
4. потом этот список отдаем в стартер, и обнуляем этот
5. стартер удаляет тело и меняет картинку

 */