<%@ page errorPage="../../ErrorPage.jsp" %>
<%@ page import = "fr.paris.lutece.plugins.mydashboard.modules.identity.web.IdentityXPage" %> 

<%
     response.sendRedirect(  IdentityXPage.getModifyIdentity( request )  );
 %>
