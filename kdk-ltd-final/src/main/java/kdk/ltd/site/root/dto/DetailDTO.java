package kdk.ltd.site.root.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kdk.ltd.site.root.entities.Detail;

public class DetailDTO implements Serializable {
	
	private long id;
	private String product;
	private int quantity;
	private BigDecimal sum;	
	
	
	public DetailDTO(long id, String product, int quantity, BigDecimal sum) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.sum = sum;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public static List<DetailDTO> buildList(List<? extends Detail> details) {
		List<DetailDTO> dtos = new ArrayList<>(details.size());
		for (Detail d: details) {
			dtos.add( build(d) );
		}
		return dtos;
	}

	public static DetailDTO build(Detail d) {
		return new DetailDTO(
				d.getId(),
				d.getProduct().getName(),
				d.getQuantity(),
				d.getSum()
		);
	}
	
}
