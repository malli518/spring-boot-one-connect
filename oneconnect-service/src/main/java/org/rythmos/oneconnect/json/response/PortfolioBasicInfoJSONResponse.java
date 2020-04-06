package org.rythmos.oneconnect.json.response;

/**
 * @author Prasanth Kusumaraju
 *
 */
public class PortfolioBasicInfoJSONResponse {
	private Long id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PortfolioBasicInfoJSONResponse [id=" + id + ", name=" + name + "]";
	}

}
