<html> 
<head> 
<title>P�gina que ir� fazer um redirecionamento</title> 
</head> 

<body> 
	<h1> <% out.println("P�gina que ir� fazer um redirecionamento"); %> </h1> 
<%
	response.sendRedirect("pagina-redirecionada.jsp");
%>
</body> 
</html>
