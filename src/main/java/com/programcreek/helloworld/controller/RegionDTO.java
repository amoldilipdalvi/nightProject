package com.programcreek.helloworld.controller;

import java.util.List;

public class RegionDTO {

	private Integer oppsTotal;
	private Integer brTotal;
	
	private List<subRegions> subRegions;

	public Integer getOppsTotal() {
		return oppsTotal;
	}

	public void setOppsTotal(Integer oppsTotal) {
		this.oppsTotal = oppsTotal;
	}

	public Integer getBrTotal() {
		return brTotal;
	}

	public void setBrTotal(Integer brTotal) {
		this.brTotal = brTotal;
	}

	public List<subRegions> getSubRegions() {
		return subRegions;
	}

	public void setSubRegions(List<subRegions> subRegions) {
		this.subRegions = subRegions;
	}
	
	
	
}
