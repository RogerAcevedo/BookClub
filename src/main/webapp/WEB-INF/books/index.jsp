<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- New line below to use the JSP Standard Tag Library -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<h1>BOOK DASHBOARD:</h1>
		<table class="table table-dark">
		<thead>
			<tr>
				<th>Title:</th>
				<th>Author:</th>
				<th>Thoughts:</th>
				<th>Admin:</th>
				<th>Is it Shonen?</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="book" items="${ allBooks }" >
				<tr>
					<td> <c:out value="${book.title }" /> </td>
					<td> <c:out value="${book.author }" />  </td>
					<td> <c:out value="${book.thoughts }" /></td>
					<td> <c:out value="${book.creator.userName }" /></td>
					<td> <c:out value="${book.shonen }" /> </td>
					<td> 
						<a href="/books/${book.id}"> View </a>
						<!-- ONLY PERSON THAT IS LOGGED IN CAN CHECK THE TWO: -->
						<c:if test="${user_id == book.creator.id }">
						<a href="/books/${book.id}/edit"> Edit </a>
						<a href="/books/${book.id}/delete"> Delete </a>
						</c:if>
						
						<!-- DELETE W/ @DeleteMapping -->
<%-- 						<form action="/books/${book.id }" method="POST">
							<input type="hidden" name="_method" value="DELETE" />
							<button> Delete</button>
						</form> --%>
						
					</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>
</body>
</html>