<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Analytics</title>
    </head>
    <body>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <h1>Operations Dashboard for Cocktail Recipes</h1><br>
<%--    Displays the 3 most recently searched for cocktails--%>
    <h3>Here are the top 3 most recently searched for cocktails:</h3><br>

    <table border="2">
        <thead>
        <tr BGCOLOR='#808080'>
            <th> Name </th>
            <th> Time Stamp </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${getMostRecentCocktailsMapList}" var="map">
            <tr>
                <td><c:out value="${map['Name']}"/></td>
                <td><c:out value="${map['CurrentTimeStamp']}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br />
    <br />


<%--    Displays the most searched for cocktail and the number of times it was searched--%>

    <h3>Here is the most searched for cocktail:</h3><br>

    <c:forEach var="entry" items="${mostSearchedDrinkMap}">
        <span>The most searched for cocktail is<b> ${entry.key} </b>and it has been searched for<b> ${entry.value} </b>times.</span>
    </c:forEach>


    <br />
    <br />

<%--    Displays the request count: The number of times the web application received a request from the user--%>

    <h3>Here is the request count for cocktail recipe:</h3><br>

    <span>Cocktail recipes were requested: <b> ${requestCount} times</b></span>


    </body>
</html>

