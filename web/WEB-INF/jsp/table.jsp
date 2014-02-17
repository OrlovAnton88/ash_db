<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Dancers List</h3>
        <table>
                <thead>
                    <tr>
                        <th>Personal<br/>Code </th>
                        <th>Name</th>
                        <th>Last<br/>Name</th>
                        <th>Family<br/>Name</th>
                        <th>Gender</th>
                        <th>Current<br/>Class</th>
                    </tr>
                </thead>
                <tbody id="dancerListContainer">
                        <tr class="person">
                        <td> <c:out value="${Dancer.personalCode}" /> </td>
                        <td> <c:out value="${Dancer.name}" /> </td>
                        <td> <c:out value="${Dancer.lastName}" /> </td>
                        <td> <c:out value="${Dancer.familyName}" /> </td>
                        <td> <c:out value="${Dancer.gender}" /> </td>
                        <td> <c:out value="${Dancer.currentClass}" /> </td>
                        </tr>
                 </tbody>
        </table>
   </body>
</html>

