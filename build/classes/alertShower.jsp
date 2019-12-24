<%@page import="java.util.ArrayList"%>
<%@page import="java.util.logging.Logger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%!
	Logger log = Logger.getLogger( "alertShower.jsp" );
 %>
<%!
 	public String printAlert(String msg){
	 return "<div class=\"alert alert-warning\">"+msg+"</div>"; 
 	}
 	
	public String printInfo(String msg){
	 return "<div class=\"alert alert-info\">"+msg+"</div>"; 
	}
	
	public void flush(){
		infos = alerts = null;
		log("flushing");
	}
	
 	ArrayList<String> alerts, infos;
 %>

                    
<%
log.info("test");

 	if(request.getAttribute("alerts") != null){
 		alerts = (ArrayList<String>)request.getAttribute("alerts");
 		log.info("alerts size"+alerts.size());
 		request.setAttribute("alerts", null);
 	}

	if(request.getAttribute("infos") != null){
		infos = (ArrayList<String>)request.getAttribute("infos");
		log.info("infos size"+infos.size());
 		request.setAttribute("infos", null);
 	}

 if(alerts != null)
    	for(String alert : alerts){
    		out.println(printAlert(alert));
    	}

 if(infos != null)
    	for(String info : infos){
    		out.println(printInfo(info));
    	}

 flush();
 
  %> 