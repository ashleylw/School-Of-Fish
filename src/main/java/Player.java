
public class Player {

	private String name;
	private String imageUrl;
	
	public Player(String name, String fileName) {
		this.name = name;
		imageUrl = fileName;
	}
	
	/**
	 * Getters, setters, and toString
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}
	
}
