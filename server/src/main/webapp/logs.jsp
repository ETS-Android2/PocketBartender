<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Logs</title>
    </head>
    <body>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <h1>Logs for Cocktail Recipes</h1><br>

<%--    Shows the request logs info in a tabular format--%>
        <h3>Here are the Request logs:</h3><br>
        <table border="2">
            <thead>
            <tr BGCOLOR='#808080'>
                <th> Path </th>
                <th> RequestSecure </th>
                <th> BrowserName </th>
                <th> MachineIP </th>
                <th> CurrentTimeStamp </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestLogs}" var="map">
            <tr>
                <td><c:out value="${map['Path']}"/></td>
                <td><c:out value="${map['RequestSecure']}"/></td>
                <td><c:out value="${map['BrowserName']}"/></td>
                <td><c:out value="${map['MachineIP']}"/></td>
                <td><c:out value="${map['CurrentTimeStamp']}"/></td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    <br />

    <%--    Shows the Response logs info in a tabular format--%>
    <h3>Here are the Response logs:</h3><br>
    <table border="2">
        <thead>
        <tr BGCOLOR='#1dace4'>
            <th> Name </th>
            <th> TypeofDrink </th>
            <th> Recipe </th>
            <th> CurrentTimeStamp </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${responseLogs}" var="map2">
            <tr>
                <td><c:out value="${map2['Name']}"/></td>
                <td><c:out value="${map2['TypeofDrink']}"/></td>
                <td><c:out value="${map2['Recipe']}"/></td>
                <td><c:out value="${map2['CurrentTimeStamp']}"/></td>
            </tr>
        </c:forEach>
        </tbody>

    </table>

    </body>
</html>

