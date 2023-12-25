package letscode.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Starter extends ApplicationAdapter {
	SpriteBatch batch;

	private String meId;

	private ObjectMap<String, Panzer> panzers = new ObjectMap<>();

	private final KeyboardAdapter inputProcessor;
	private MessageSender messageSender;
	Listener Listener;
	World world;
	Box2DDebugRenderer debugRenderer;
	BodyDef bodyDef = new BodyDef();
	CircleShape circle;


	public Starter(InputState inputState) {
		this.inputProcessor = new KeyboardAdapter(inputState);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		world = new World(new Vector2(0,0),true);
		Listener=new Listener();
		world.setContactListener(Listener);
		debugRenderer = new Box2DDebugRenderer();
		bodyDef.type= BodyDef.BodyType.DynamicBody;
		Gdx.input.setInputProcessor(inputProcessor);


		Panzer me = new Panzer(100, 200);
		Body body = world.createBody(bodyDef);
		circle = new CircleShape();
		circle.setRadius(45f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		//вывели фикстуру чтоб могли ее потом использывать
		Fixture fixture=body.createFixture(fixtureDef);
		fixture.setUserData(meId);
		me.setBody(body);
		panzers.put(meId, me);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		for (String key : panzers.keys()) {
			panzers.get(key).render(batch);
		}
		batch.end();
		debugRenderer.render(world,batch.getProjectionMatrix());
		world.step(1/30f, 6, 2);
		//перекладываем массив со смертниками
		Array<Fixture> d=new Array<>();
		Listener.getDied().forEach( x ->{
			d.add(x);

		});
		Listener.getDied().clear();

		d.forEach( x ->{
            world.destroyBody(x.getBody()); //удаляем тело
			panzers.get((String) x.getUserData()).diedTexture(); //меняем картинку
		});







	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for (Panzer value : panzers.values()) {
			value.dispose();
		}
		circle.dispose();
	}

	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void handleTimer() {
		if (inputProcessor != null && !panzers.isEmpty()) {
			Panzer me = panzers.get(meId);
			InputState playerState = inputProcessor.updateAndGetInputState(me.getOrigin());
			messageSender.sendMessage(playerState);
		}
	}

	public void setMeId(String meId) {
		this.meId = meId;
	}

	public void evict(String idToEvict) {
		panzers.remove(idToEvict);
	}

	public void updatePanzer(String id, float x, float y, float angle, boolean EP) {
		if (panzers.isEmpty()) {
			return;
		}

		Panzer panzer = panzers.get(id);
		if (panzer == null) {
			panzer = new Panzer(x, y, "you.png");
			Body body = world.createBody(bodyDef);
			CircleShape circle = new CircleShape();
			circle.setRadius(45f);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = circle;
			Fixture fixture=body.createFixture(fixtureDef);
			fixture.setUserData(id);
			panzer.setBody(body);

			panzers.put(id, panzer);
		} else {
			panzer.moveTo(x, y);

		}
		panzer.rotateTo(angle);
		panzer.Texture(EP);


	}}
