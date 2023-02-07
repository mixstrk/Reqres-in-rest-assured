package pojos.postPojos;

public class RegUserRequestPojo{
	private String password;
	private String email;

	public RegUserRequestPojo() {
	}

	public String getPassword(){
		return password;
	}

	public String getEmail(){
		return email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
