
public interface ICollisionHandler {
	
	void checkCollision();

}

class MazeCollisionHandler implements ICollisionHandler {
	
	Environment env;
	
	public MazeCollisionHandler(Environment env_) {
		env = env_;
	}

	@Override
	public void checkCollision() {
		// TODO Auto-generated method stub
		
	}
	
}
