package org.opengraph.lst.core.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resourceType;
	private String resourceIdentifier;
	
	public ResourceNotFoundException(String resourceType, String resoruceIdentifier) {
		super(String.format("Resource %s with identifier %s not found", resourceType, resoruceIdentifier));
		this.resourceType = resourceType;
		this.resourceIdentifier = resoruceIdentifier;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

}
