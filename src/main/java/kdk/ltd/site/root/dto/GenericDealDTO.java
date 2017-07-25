package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.GenericDeal;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class GenericDealDTO implements Serializable {

	private long id;
	private String partner;
	private LocalDateTime dateOfDocument;
	private String user;
	private GenericDeal.Type type;

	public GenericDealDTO() {
	}

	public GenericDealDTO(long id, String partner, LocalDateTime dateOfDocument, String user, GenericDeal.Type type) {
		this.id = id;
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

	public static GenericDealDTO buildDocument(GenericDeal d) {
		GenericDealDTO dto = new GenericDealDTO(
				d.getId(),
				d.getPartner().getName(),
				d.getDateTimeOfDeal(),
				d.getUser().getUsername(),
				d.getType()
		);
		return dto;
	}
}
