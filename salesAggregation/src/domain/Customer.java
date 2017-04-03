package domain;

/**
 * A domain class for representing a customer.
 * 
 * @author adath325
 */
public class Customer {
	private Character gender;
	private String dateOfBirth;

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Customer(Character gender, String dateOfBirth) {
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Customer{" + "gender=" + gender + ", dateOfBirth=" + dateOfBirth +
				  '}';
	}
}
