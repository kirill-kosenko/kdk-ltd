package kdk.ltd.site.root.dto;

import kdk.ltd.site.root.entities.Deal;

import java.io.Serializable;
import java.time.LocalDate;


public class DealDTO implements Serializable {

	private long id;
	private String partner;
	private LocalDate dateOfDocument;
	private String user;
	private Deal.Type type;

	public DealDTO() {
	}

	public DealDTO(long id, String partner, LocalDate dateOfDocument, String user, Deal.Type type) {
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

	public LocalDate getDateOfDocument() {
		return dateOfDocument;
	}

	public void setDateOfDocument(LocalDate dateOfDocument) {
		this.dateOfDocument = dateOfDocument;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Deal.Type getType() {
		return type;
	}

	public void setType(Deal.Type type) {
		this.type = type;
	}

	public static DealDTO buildDocument(Deal d) {
		DealDTO dto = new DealDTO(
				d.getId(),
				d.getPartner().getName(),
				d.getDateOfDeal(),
				d.getUser().getUsername(),
				d.getType()
		);
		return dto;
	}
}
