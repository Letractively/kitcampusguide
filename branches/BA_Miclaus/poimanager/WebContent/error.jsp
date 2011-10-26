<%-- JSP Page header --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KCG POI Management Error Page</title>
</head>
<body>

<%
    //get the error message from the session
    String errorMsg = (String) session.getAttribute("errorMsg");
    //if there was NO error, or NO error variable has been saved in the session then we should not be here
    if (errorMsg.equals("") || errorMsg == null) {
        out.print("Bad redirection!");
    } else {
%>

<%-- display an error message in the HTML part of the JSP page - note that this comment is not included in the output page--%>
An error has occured: <%=errorMsg%>
<br />

<%
    }
    session.invalidate();
%>
<%-- the following comment will be included in the output HTML, this one is not --%>
<!-- Here we invoke some javascript to create a back button-->
<button onClick="window.location='../index.html'">Try Again</button>
</body>
</html>