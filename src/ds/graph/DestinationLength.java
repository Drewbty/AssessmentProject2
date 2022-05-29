package ds.graph;

public class DestinationLength {

	private int steps;
	private Business business;
	public DestinationLength(int steps, Business business) {
		super();
		this.steps = steps;
		this.business = business;
	}
	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}
	/**
	 * @param steps the steps to set
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}
	/**
	 * @return the business
	 */
	public Business getBusiness() {
		return business;
	}
	/**
	 * @param business the business to set
	 */
	public void setBusiness(Business business) {
		this.business = business;
	}
	
	
}
