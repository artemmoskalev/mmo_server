package blood.steel.server.model.location.shop.messaging;

public class ItemQuantity {

	private String name;	
	private Integer quantity;
	
	public ItemQuantity() {}
	public ItemQuantity(String name, Integer quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
