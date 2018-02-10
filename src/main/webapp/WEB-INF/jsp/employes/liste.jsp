<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../tags/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h1>Liste des employés</h1>
            <div class="btn-group">
                <a href="#" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    Nouvel employé
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="/employes/new/technicien">Technicien</a></li>
                    <li><a href="/employes/new/commercial">Commercial</a></li>
                    <li><a href="/employes/new/manager">Manager</a></li>
                </ul>
            </div>
            <table class="table table-hover table-striped">
                <thead>
                
                <tr>
                    <th scope="col">
                        Matricule <a href=""><span class="glyphicon glyphicon-chevron-up"></span></a>
                    </th>
                    <th scope="col">
                        Nom <a href=""><span class="glyphicon glyphicon-chevron-down"></span></a>
                    </th>
                    <th scope="col">
                        <a href="">Prénom</a>
                    </th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${ employeeList }" var="employee">
                    <tr>
                        <th scope="row">${employee.matricule}</th>
                        <td>${employee.nom}</td>
                        <td>${employee.prenom}</td>
                        <td><a href="www.localhost:8080/employes/${employee.id}">Détail</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="row">
                <div class="col-lg-6">
                	<c:choose>
                		<c:when test="${((listPageNumber+1)*10) <= nombreEmployes}">
                    		<p>Affichage des employés ${(listPageNumber*10)+1} à ${(listPageNumber+1)*10} sur un total de ${ nombreEmployes }</p>
                    	</c:when>
                    	<c:otherwise>
                    		<p>Affichage des employés ${(listPageNumber*10)+1} à ${nombreEmployes} sur un total de ${ nombreEmployes }</p>
                    	</c:otherwise>
                    </c:choose>
                </div>
                <div class="col-lg-6">
                    <ul class="pagination">
                    <c:if test="${ listPageNumber != 0 }">
                        <li><a href="localhost:8080/employes?page=${listPageNumber-1 }&size=${pageSize}&sortProperty=${pageSortProperty}&sortDirection=${pageSortDirection}">&laquo;</a></li>
                    </c:if>  
                        <li><a href="#">Page ${ listPageNumber }</a></li>
                    <c:if test="${ ((listPageNumber+1)*10) <= nombreEmployes }">
                        <li><a href="localhost:8080/employes?page=${listPageNumber+1 }&size=${pageSize}&sortProperty=${pageSortProperty}&sortDirection=${pageSortDirection}">&raquo;</a></li>
                    </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../tags/footer.jsp" %>