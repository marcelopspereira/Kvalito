<html> 
<head> 
<title>P�gina que gera o cookie teste</title> 
</head> 

<body> 
	<h1> <% out.println("P�gina que gera o cookie teste=teste"); %> </h1> 
<%
	Cookie cookie = new Cookie("teste","teste");
	response.addCookie(cookie);
%>
</body> 
</html>
