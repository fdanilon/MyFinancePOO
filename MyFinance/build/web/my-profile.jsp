<%-- 
    Document   : index
    Created on : 6 de jun. de 2021, 18:51:36
    Author     : Fabio
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="db.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String requestError = null;
    try{
        if(request.getParameter("changePassword")!= null){
            String login = (String) session.getAttribute("user.login");
            String password = request.getParameter("password");
            String newPassword1 = request.getParameter("newPassword1");
            String newPassword2 = request.getParameter("newPassword2");
            if(User.getUser(login, password) == null){
                requestError = "Senha inválida";
            }else if(!newPassword1.equals(newPassword2)){
                requestError = "Confirmação de nova senha inválida";
            }else{ 
                User.changePassword(login, newPassword1);
                response.sendRedirect(request.getRequestURI());
            }
                
    }
    }catch(Exception ex){
        requestError = ex.getMessage();
    }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Meu perfil - MyFinance$</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <h2>Meu perfil</h2>
        
        <%
            String login = (String)session.getAttribute("user.login");
            String name = (String) session.getAttribute("user.name");
            String role = (String)session.getAttribute("user.role");
        %>
        <% if(login == null){%>
        <div style="color:red">Página de acesso restrito a usuários logados</div>
        <%}else{%>       
        
        <h3>Login</h3>
        <%= session.getAttribute("user.login")%> 
        <h3>Nome</h3>
        <%= session.getAttribute("user.name")%> 
        <h3>Papel</h3>
        <%= session.getAttribute("user.role")%>
        
        <fieldset>
            <legend>Alterar Senha</legend>
            <form method="post">
                <% if(requestError!=null){%>
                <div style="color:red"><%= requestError %></div>
                <%}%>
                Senha:
                <br/><input type="password" name="password">
                <br/>Nova senha:
                <br/><input type="password" name="newPassword1">
                <br/>Confirmação da nova senha
                <br/><input type="password" name="newPassword2">
                <hr/>
                <br/><input type="submit" name="changePassword" value="Alterar senha"/>
            </form>
        </fieldset>
        
        
        <hr/>
        <%}%>
    </body>
</html>
