package com.programcreek.helloworld.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Service {
	
	private static Connection conection = JDBCPostGres.getConnection();
	private  PreparedStatement statement = null;

	public RegionDTO getSubRegions(Integer regId)
	{
		RegionDTO rg = new RegionDTO();
		List<subRegions> subRegs = createSubRegions(regId);
		List<subRegions> finalSubRegs = new ArrayList<subRegions>();
		
		for(subRegions sub: subRegs)
		{
			if(sub.getrId() == regId)
			{
				finalSubRegs.add(sub);
				rg.setBrTotal(sub.getBranches());
				rg.setOppsTotal(sub.getOpps());
				System.out.println(sub.getRegName());
			}
		}
		
		rg.setSubRegions(finalSubRegs);
		/*rg.setBrTotal(30);
		rg.setOppsTotal(20);*/
		return rg;
	}

	
	public BranchDTO getBranches(Integer regId)
	{
		
		BranchDTO brDTO = new BranchDTO();
		List<Branches> subBranches = createBranches(regId);
		List<Branches> finalBranches = new ArrayList<Branches>();
		
		for(Branches br: subBranches)
		{
			if(br.getRegId() == regId)
			{
				finalBranches.add(br);
				brDTO.setBrTotal(br.getBrOpps());
			}
		}
		
		/*brDTO.setBrTotal(20);
		brDTO.setOppsTotal(50);*/
		brDTO.setSubRegions(finalBranches);
		return brDTO;
	}
	
	public OppsDTO getOpps(Integer brId)
	{
		OppsDTO ops = new OppsDTO();
		List<Opps> subOpps = createOpps(brId);
		List<Opps> finalsubOpps = new ArrayList<Opps>();
		
		for(Opps Opp: subOpps)
		{
			if(Opp.getBrId() == brId)
			{
				finalsubOpps.add(Opp);
			}
		}
		
		/*ops.setBrTotal(60);
		ops.setOppsTotal(70);*/
		ops.setSubRegions(finalsubOpps);
		System.out.println(finalsubOpps.size());
		return ops;
	}
	
	public List<subRegions> createSubRegions(Integer regId)
	{
		List<subRegions> subList = new ArrayList<subRegions>();
		
		
		String selectTableSQL = "SELECT sb_id , sb_name, r_id, forecast_status, (select count(*) FROM sfdc.branch_iec where b_sb_id = sb_id and delete_flg = 'N' ) as branches, "
				+ " (select count(*) FROM sfdc.opportunity_iec where o_br_id =  (select br_id from sfdc.branch_iec where b_sb_id = sb_id ) and delete_flg = 'N' ) as opps FROM sfdc.subregion_iec where r_id = "+regId;
		
		ResultSet rs = null;
		try {
			
			statement = conection.prepareStatement(selectTableSQL);
			rs= statement.executeQuery();
			
			while (rs.next()) {
				subRegions sub = new subRegions();
				sub.setrId(regId);
				sub.setRegId(rs.getInt(1));
				sub.setRegName(rs.getString(2));
				sub.setRegStatus(rs.getString(4));
				sub.setBranches(Integer.parseInt(rs.getString(5)));
				sub.setOpps(Integer.parseInt(rs.getString(6)));
				
				System.out.println("rId = "+regId+ "sub.getRegId() = "+sub.getRegId()+"sub.getRegName() = "+ sub.getRegName() +"sub.getRegSttus() = "+ sub.getRegStatus()+"sub.getbranch() = "+ sub.getBranches());
				subList.add(sub);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		System.out.println("subList size = "+subList.size());
		return subList; 
		
		
		
		
		
	}
	public List<Branches> createBranches(Integer regId)
	{
		List<Branches> brList = new ArrayList<Branches>();
				
		
		String selectTableSQL = "SELECT br_id , branch_name, forecast_status, "
				+ " (select count(*) FROM sfdc.opportunity_iec where o_br_id = br_id and delete_flg = 'N' ) as opps,"
				+ " (select sum(facamt) FROM sfdc.opportunity_iec where o_br_id = br_id and delete_flg = 'N' ) as oppsSum,"
				+ " b_sb_id, delete_flg FROM sfdc.branch_iec where delete_flg = 'N' and b_sb_id = "+regId ;
		
		ResultSet rs = null;
		
		try {
			
			statement = conection.prepareStatement(selectTableSQL);
			rs= statement.executeQuery();
			
			while (rs.next()) {
				Branches br = new Branches();
				br.setBrId(rs.getInt(1));
				br.setRegId(regId);
				br.setBrName(rs.getString(2));
				br.setBrFcStatus(rs.getString(3));
				br.setBrOpps(Integer.parseInt(rs.getString(4)));
				br.setFcAmt(Integer.parseInt(rs.getString(5)));
				brList.add(br);
				System.out.println(rs.getString(2)+" "+br.getBrOpps());
				//System.out.println("rId = "+regId+ "sub.getRegId() = "+sub.getRegId()+"sub.getRegName() = "+ sub.getRegName() +"sub.getRegSttus() = "+ sub.getRegStatus()+"sub.getbranch() = "+ sub.getBranches());
				
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return brList; 
		
		
		
		
		
	}
	
	public List<Opps> createOpps(Integer brId)
	{
		List<Opps> brList = new ArrayList<Opps>();
		
		String selectTableSQL = "SELECT opp_id,opp_number,accnam_iec,estamt_iec,facamt,leadsrp_iec, delete_flg FROM sfdc.opportunity_iec where delete_flg = 'N' and o_br_id = "+brId ;
		
		ResultSet rs = null;
		
		try {
			
			statement = conection.prepareStatement(selectTableSQL);
			rs= statement.executeQuery();
			
			while (rs.next()) {
				Opps opp  = new Opps();
				opp.setBrId(brId);
				opp.setOppsId(rs.getInt(1));
				opp.setOppsName(rs.getString(2));
				opp.setAccName(rs.getString(3));
				opp.setEstAmt(rs.getString(4));
				opp.setLeadRep(rs.getString(5));
				opp.setDeleteFlg(rs.getString(7));
				
				
				brList.add(opp);
				/*System.out.println(rs.getString(2)+" "+br.getBrOpps());*/
				//System.out.println("rId = "+regId+ "sub.getRegId() = "+sub.getRegId()+"sub.getRegName() = "+ sub.getRegName() +"sub.getRegSttus() = "+ sub.getRegStatus()+"sub.getbranch() = "+ sub.getBranches());
				
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return brList; 
		
		
		
		
		
	}
	
	public void remove(Integer brId)
	{
		
		try {
			String updateTableSQL="UPDATE sfdc.branch_iec SET  forecast_status = 'In Progress' WHERE br_id = "+brId;
			statement = conection.prepareStatement(updateTableSQL);
			statement.executeUpdate();
			
			updateTableSQL="UPDATE sfdc.subregion_iec SET  forecast_status = 'In Progress' WHERE sb_id = (select b_sb_id from sfdc.branch_iec where br_id = "+brId+") ";
			statement = conection.prepareStatement(updateTableSQL);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
}
