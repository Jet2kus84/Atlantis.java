package atlantis.samples.soft3d;

import java.awt.Color;
import java.awt.event.KeyEvent;

import atlantis.engine.graphics3d.importer.babylonjs.BabylonImporter;
import atlantis.framework.Game;
import atlantis.framework.GameTime;

public class SceneBabylon extends BaseDemo3D {
	private boolean autoRotate;
	
	public SceneBabylon() {
		super("AtlantisEngine.java - 3D serie : Loading a babylon scene");
		//this.camera.position.set(0, 13.9f, 32.0f);
		//this.camera.rotation.set(-0.38f, 0.0f, 0.0f);
		this.autoRotate = true;
	}
	
	/**
	 * Load the scene from a file.
	 */
	public void loadContent() {
		super.loadContent();
		
		meshes = BabylonImporter.loadBabyonScene("Content/models/hellskitchen.babylon");
	
		for (int i = 0; i < meshes.length; i++) {
			meshes[i].setFacesColor(Color.lightGray);
		}
	}
	
	/**
	 * Apply a rotation on each objects of the scene
	 */
	public void update(GameTime gameTime) {
		super.update(gameTime);
		if (this.autoRotate) {
			for (int i = 0, l = meshes.length; i < l; i++) {
				meshes[i].getRotation().y += 0.005f;
			}
		}
		
		if (this.keyboardManager.getState().isKeyDown(KeyEvent.VK_ENTER)) {
			this.autoRotate = !this.autoRotate;
		}
		
		if (this.keyboardManager.getState().isKeyDown(KeyEvent.VK_F1)) {
			this.renderer.getLight().setEnableFlatShading(!this.renderer.getLight().isEnableFlatShading());
		}
	}

	public static void main(String[] args) {
		Game game = new SceneBabylon();
		game.run();
	}
}
