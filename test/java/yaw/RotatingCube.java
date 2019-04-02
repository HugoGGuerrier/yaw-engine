package yaw;

import yaw.engine.UpdateCallback;
import yaw.engine.World;
import yaw.engine.items.ItemObject;
import yaw.engine.meshs.*;

/**
 * Basic example of a cube rotating on y axis
 */
public class RotatingCube implements UpdateCallback {
	private int nbUpdates = 0;
	private double totalDeltaTime = 0.0;
	private static long deltaRefreshMillis = 1000;
	private long prevDeltaRefreshMillis = 0;
	private ItemObject cube ;
	private float speed = 10;
	
	public RotatingCube(ItemObject cube) {
		this.cube = cube;
	}
	
	public ItemObject getItem() {
		return cube;
	}
	
	static Mesh createCube() {
		Mesh mesh = MeshBuilder.generateBlock(1, 1, 1);
		return mesh;
	}
	
	@Override
	public void update(double deltaTime) {
		nbUpdates++;
		totalDeltaTime += deltaTime;
		
		long currentMillis = System.currentTimeMillis();
		if (currentMillis - prevDeltaRefreshMillis > deltaRefreshMillis) {
			double avgDeltaTime = totalDeltaTime / (double) nbUpdates;
			System.out.println("Average deltaTime = " + Double.toString(avgDeltaTime) +" s ("+nbUpdates+")");
			nbUpdates = 0;
			totalDeltaTime = 0.0;
			prevDeltaRefreshMillis = currentMillis;
		}

		cube.rotate(0.0f, 3.1415925f * speed * (float) deltaTime, 0.0f);





	}
	
	public static void main(String[] args) {
		Mesh cubem = createCube();
		
		World world = new World(0, 0, 800, 600);
		
		float[] pos = { 0f, 0f, -2f };
		ItemObject cube = world.createItemObject("cube", pos, 1.0f, cubem);
		cube.getMesh().getMaterial().setTexture(new Texture("/ressources/diamond.png"));

		RotatingCube rCube = new RotatingCube(cube);

		world.registerUpdateCallback(rCube);
		
		Thread th = new Thread(world);
		th.start();
		
		
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
