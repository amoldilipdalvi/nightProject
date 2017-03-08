package com.programcreek.helloworld.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

	private Service service = new Service();
	private JDBCPostGres jdbcPostGresservice = new JDBCPostGres();
	
	private static List<salesForcast> salesForcasts = new ArrayList<salesForcast>();

	/*static {
		
		salesForcast forcast=new salesForcast();
		forcast.setRegion("BRZ-REF");
		forcast.setGoToRegionfor("Forecast");
		forcast.setGoTobraches("GoToBranches");
		forcast.setForcastStatus("System Submited");
		forcast.setBranches("1");
		forcast.setOpportunites("");
		forcast.setForecastAmount("USD");
		forcast.setUpdated("2-8-2017");
		forcast.setUpdatedBy("System");
		salesForcasts.add(forcast);
		
		salesForcast forcast1=new salesForcast();
		forcast1.setRegion("BRZ-SRV");
		forcast1.setGoToRegionfor("Forecast");
		forcast1.setGoTobraches("GoToBranches");
		forcast1.setForcastStatus("System Submited");
		forcast1.setBranches("30");
		forcast1.setOpportunites("");
		forcast1.setForecastAmount("USD");
		forcast1.setUpdated("2-8-2017");
		forcast1.setUpdatedBy("System");
		salesForcasts.add(forcast1);
		
		
		salesForcast forcast2=new salesForcast();
		forcast2.setRegion("BRZ-SYS");
		forcast2.setGoToRegionfor("Forecast");
		forcast2.setGoTobraches("GoToBranches");
		forcast2.setForcastStatus("System Submited");
		forcast2.setBranches("12");
		forcast2.setOpportunites("");
		forcast2.setForecastAmount("USD");
		forcast2.setUpdated("2-8-2017");
		forcast2.setUpdatedBy("System");
		salesForcasts.add(forcast2);
		
		
	}*/
	
	String message = "Welcome to Spring MVC!";

	@RequestMapping("/hello")
	public ModelAndView showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in controller");

		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		return mv;
	}

	@RequestMapping(value = "/india", method = RequestMethod.GET)
	public ModelAndView simpleform(@RequestParam("rId") Integer rId) {

		/*ModelAndView mv = new ModelAndView("latinAmr","salesForcasts",salesForcasts);*/
		
		
		ModelAndView mv = new ModelAndView("subRegions","salesForcasts",service.getSubRegions(rId));

		return mv;

	}

	@RequestMapping(value = "/china", method = RequestMethod.GET)
	public ModelAndView MiddleEast(@RequestParam("rId") Integer rId) {

		ModelAndView mv = new ModelAndView("subRegions","salesForcasts",service.getSubRegions(rId));

		return mv;

	}

	@RequestMapping(value = "/northeast", method = RequestMethod.GET)
	public ModelAndView NortAmerica(@RequestParam("rId") Integer rId) {

		/*ModelAndView mv = new ModelAndView("nortAmr");*/
		ModelAndView mv = new ModelAndView("subRegions","salesForcasts",service.getSubRegions(rId));

		return mv;

	}
	
	
	@RequestMapping(value = "/allOportunities", method = RequestMethod.GET)
	public ModelAndView allOportunities(@RequestParam("subRegionId") Integer subRegionId) {

		System.out.println("Enter");
		List<Opportunity> opps = new ArrayList<Opportunity>();
		Opportunity opps1 = new Opportunity();
		Opportunity opps2 = new Opportunity();
		Opportunity opps3 = new Opportunity();
		
		opps1.setOppId(1);
		opps1.setOppName("Opportunity 1");
		opps1.setBranchName("Branch 1");
		
		opps2.setOppId(2);
		opps2.setOppName("Opportunity 2");
		opps2.setBranchName("Branch 2");
		
		opps3.setOppId(3);
		opps3.setOppName("Opportunity 3");
		opps3.setBranchName("Branch 3");
		
		opps.add(opps1);
		opps.add(opps2);
		opps.add(opps3);
		
		ModelAndView mv = new ModelAndView("allOpps","opps",opps);

		return mv;

	}

	@RequestMapping(value = "/getBranches", method = RequestMethod.GET)
	public ModelAndView getBranches(@RequestParam("subRegId") Integer regId) {

		System.out.println("Enter");
		ModelAndView mv = new ModelAndView("branches","bracnhes",service.getBranches(regId));

		return mv;

	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public void remove(@RequestParam("brId") Integer brId) {

		/*System.out.println("remove");*/
		service.remove(brId);
		/*ModelAndView mv = new ModelAndView("oppsDt","oppsData",service.getBranches(brId));

		return mv;
*/
	}
	

	@RequestMapping(value = "/getOpps", method = RequestMethod.GET)
	public ModelAndView getOpps(@RequestParam("brId") Integer brId) {

		System.out.println("Enter 1234");
		ModelAndView mv = new ModelAndView("oppsDt","oppsData",service.getOpps(brId));

		return mv;

	}
//temporary controller to check jdbc conn on heroku
	@RequestMapping(value = "/jdbcData", method = RequestMethod.GET)
	public ModelAndView getJdbcData() {

		//ModelAndView mv = new ModelAndView("latinAmr","salesForcasts",salesForcasts);
		/*ModelAndView mv = new ModelAndView("test","data",jdbcPostGresservice.getjdbcData());*/
		ModelAndView mv = new ModelAndView();

		return mv;

	}
}
