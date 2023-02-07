package pojos.postPojos;

import lombok.Builder;

@Builder
public class CreateUserRequestPojo {
	private String name;
	private String job;

	public CreateUserRequestPojo() {
	}


	public String getName(){
		return name;
	}

	public String getJob(){
		return job;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setJob(String job) {
		this.job = job;
	}
}
