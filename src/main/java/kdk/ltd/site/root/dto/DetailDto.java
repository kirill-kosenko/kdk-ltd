package kdk.ltd.site.root.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kdk.ltd.site.root.entities.Detail;

public class DetailDto implements Serializable {
	
	private long id;
	private Integer version;
	private String product;
	private int quantity;
	private BigDecimal sum;	
	
	
	public DetailDto(long id, Integer version, String product, int quantity, BigDecimal sum) {
		this.id = id;
		this.version = version;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public static List<DetailDto> buildList(List<? extends Detail> details) {
		List<DetailDto> dtos = new ArrayList<>(details.size());
		for (Detail d: details) {
			dtos.add( build(d) );
		}
		return dtos;
	}

	public static DetailDto build(Detail d) {
		return new DetailDto(
				d.getId(),
				d.getVersion(),
				d.getProduct().getName(),
				d.getQuantity(),
				d.getSum()
		);
	}
	
}
