package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		//plane
		/*float[] vertices = {			
				-0.5f,0.5f,0,	//V0
				-0.5f,-0.5f,0,	//V1
				0.5f,-0.5f,0,	//V2
				0.5f,0.5f,0		//V3
		};
		
		int[] indices = {
				0,1,3,	//Top left triangle (V0,V1,V3)
				3,1,2	//Bottom right triangle (V3,V1,V2)
		};
		
		float[] textureCoords = {
				0,0, //v0
				0,1, //v1
				1,1, //v2
				1,0  //v3
		};
		*/
		//cube
		
		
		RawModel model = OBJLoader.loadObjModel("boss", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("bossTex"));
		TexturedModel staticModel = new TexturedModel(model,texture);
		
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel,new Vector3f(0,0,-10),0,0,0,1f);
		Light light = new Light ( new Vector3f(0,5,0),new Vector3f(1,1,1));
		
		Camera camera = new Camera();
		while(!Display.isCloseRequested()){
			//game logic
			//entity.increasePosition(0, 0,-0.001f);
			entity.increaseRotation(0,0.1f,0.1f);
			camera.Move();
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			renderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();			
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
