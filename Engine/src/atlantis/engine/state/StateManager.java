package atlantis.engine.state;

import java.awt.Graphics;
import java.util.ArrayList;

import atlantis.framework.DrawableGameComponent;
import atlantis.framework.BaseGame;
import atlantis.framework.GameTime;

/**
 * A State manager that is responsible to manage game states.
 * @author Yann
 */
public class StateManager extends DrawableGameComponent {

	protected ArrayList<BaseState> states;
	
	public StateManager(BaseGame game) {
		super(game);
		this.states = new ArrayList<BaseState>();
	}
	
	@Override
	public void initialize() {
		if (!this.initialized) {
			for (BaseState state : this.states) {
				state.initialize();
			}
			this.initialized = true;
		}
	}
	
	@Override
	public void loadContent() {
		if (!this.assetLoaded) {
			for (BaseState state : this.states) {
				state.loadContent(this.game.getContentManager());
			}
			this.assetLoaded = true;
		}
	}

	@Override
	public void update(GameTime gameTime) {
		for (BaseState state : this.states) {
			if (state.isEnabled()) {
				state.update(gameTime);
			}
		}
	}

	@Override
	public void draw(Graphics graphics) {
		for (BaseState state : this.states) {
			if (state.isVisible()) {
				state.draw(graphics);
			}
		}
	}
	
	/**
	 * Sets a state active.
	 * @param name The name of the state to activate.
	 * @param desactiveOtherStates Sets to true to desactive other active states.
	 */
	public void setStateActive(String name, boolean desactiveOtherStates) {
		int i = 0;
        int size = this.states.size();

		while (i < size) {
			if (this.states.get(i).getName() == name) {
                this.states.get(i).setActive(true);
			}
			else if (desactiveOtherStates) {
				this.states.get(i).setActive(false);
			}
			i++;
		}
	}
	
	/**
	 * Sets a state active.
	 * @param position The index of the state to active.
	 * @param desactiveOtherStates Sets to true to desactive other active states.
	 */
	public void setStateActive(int position, boolean desactiveOtherStates) {
		if (desactiveOtherStates) {
			disableStates();
		}
		
		BaseState state = this.states.get(position);
		
		if (state != null) {
			state.setActive(true);
		}
	}
	
	/**
	 * Disable all active states.
	 */
	public void disableStates() {
		for (BaseState state : this.states) {
			state.setActive(false);
		}
	}
	
	/**
	 * Switch to a state and remove other from the state manager.
	 * @param newState
	 */
	public void switchState(BaseState newState) {
		this.states.clear();
		this.states.add(newState);
	}
	
	/**
	 * Add a new state on the collection.
	 * @param state The state to add.
	 * @param isActive Define the activity of the new state.
	 * @param desactiveOtherStates Sets to true to desactive ohter states.
	 */
	public void add(BaseState state, boolean isActive, boolean desactiveOtherStates) {
		if (desactiveOtherStates) {
			disableStates();
		}
		
		state.stateManager = this;
		state.setActive(isActive);
		
		if (this.initialized) {
			state.loadContent(this.game.getContentManager());
			state.initialize();
		}
		
		this.states.add(state);
	}
	
	/**
	 * Remove a state from state manager.
	 * @param state The state to remove.
	 */
	public void remove(BaseState state) {
		this.states.remove(state);
	}
	
	/**
	 * Remove a state from state manager.
	 * @param name The name of the state to remove.
	 */
	public void remove(String name) {
		BaseState state = this.get(name);
		
		if (state != null) {
			this.remove(state);
		}
	}
	
	/**
	 * Gets a state by its name.
	 * @param name The name of the state to find.
	 * @return Return the state with this name if exists, otherwise return null.
	 */
	public BaseState get(String name) {
		BaseState state = null;
		
		int i = 0;
		int index = -1;
        int size = this.states.size();

		while (i < size && index == -1) {
			if (this.states.get(i).getName() == name) {
                state = this.states.get(i);
                index = i;
			}
			i++;
		}
		
		return state;
	}
	
	/**
	 * Gets a state by its index.
	 * @param position The index of the state to find.
	 * @return Return the state at the specified index if exists, otherwise return false.
	 */
	public BaseState get(int position) {
		return this.states.get(position);
	}
}
