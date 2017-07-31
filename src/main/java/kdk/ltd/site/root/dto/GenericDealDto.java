package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.GenericDeal;

import java.io.Serializable;
import java.time.LocalDateTime;


public class GenericDealDto implements Serializable {

	private long id;
	private int version;
	private String partner;
	private LocalDateTime dateOfDocument;
	private String user;
	private GenericDeal.Type type;

	public GenericDealDto() {
	}

	public GenericDealDto(long id, int version, String partner, LocalDateTime dateOfDocument, String user, GenericDeal.Type type) {
		this.id = id;
		this.version = version;
		this.partner = partner;
		this.dateOfDocument = dateOfDocument;
		this.user = user;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public LocalDateTime getDateOfDocument() {
		return dateOfDocument;
	}

	public void setDateOfDocument(LocalDateTime dateOfDocument) {
		this.dateOfDocument = dateOfDocument;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public GenericDeal.Type getType() {
		return type;
	}

	public void setType(GenericDeal.Type type) {
		this.type = type;
	}

	public static GenericDealDto buildDocument(GenericDeal d) {
		return new GenericDealDto(
				d.getId(),
                d.getVersion(),
				d.getPartner().getName(),
				d.getDateTimeOfDeal(),
				d.getUser().getUsername(),
				d.getType()
		);
	}
}
