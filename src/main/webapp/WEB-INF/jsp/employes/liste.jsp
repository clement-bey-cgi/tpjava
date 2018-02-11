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
                    
                    	<%-- Affichage des glyphicons UP DOWN si on tri par matricule + composition du lien pour changer la direction de tri / propriété du tri --%>
                    	<%-- J'ai été obligé d'indenter mon code avec des commentaires, sinon les espaces blancs étaient transmis dans l'URL... Etrange --%>
                        <a href="employes?page=${ requestScope.listPageNumber }&size=10&sortProperty=matricule&sortDirection=<c:choose>
<%-- ---------------------%><c:when test="${ requestScope.pageSortProperty == 'matricule' && requestScope.pageSortDirection == 'ASC'}">DESC">Matricule <span class="glyphicon glyphicon-chevron-down"></span></c:when>
<%-- ---------------------%><c:when test="${ requestScope.pageSortProperty =='matricule' && requestScope.pageSortDirection == 'DESC'}">ASC">Matricule <span class="glyphicon glyphicon-chevron-up"></span></c:when>								
<%-- ---------------------%><c:otherwise>ASC">Matricule</c:otherwise>
						</c:choose></a>                       
                    </th>
                    
                    <th scope="col">
                    
                    	<%-- Affichage des glyphicons UP DOWN si on tri par nom + composition du lien pour changer la direction de tri / propriété du tri --%>
                    	<%-- J'ai été obligé d'indenter mon code avec des commentaires, sinon les espaces blancs étaient transmis dans l'URL... Etrange --%>
                        <a href="employes?page=${ requestScope.listPageNumber }&size=10&sortProperty=nom&sortDirection=<c:choose>
<%-- ---------------------%><c:when test="${ requestScope.pageSortProperty == 'nom' && requestScope.pageSortDirection == 'ASC'}">DESC">Nom <span class="glyphicon glyphicon-chevron-down"></span></c:when>
<%-- ---------------------%><c:when test="${ requestScope.pageSortProperty =='nom' && requestScope.pageSortDirection == 'DESC'}">ASC">Nom <span class="glyphicon glyphicon-chevron-up"></span></c:when>								
<%-- ---------------------%><c:otherwise>ASC">Nom</c:otherwise>
						</c:choose></a>                        
                    </th>
                    
                    <th scope="col">
                    
                    	<%-- Affichage des glyphicons UP DOWN si on tri par prénom + composition du lien pour changer la direction de tri / propriété du tri --%>
                    	<%-- J'ai été obligé d'indenter mon code avec des commentaires, sinon les espaces blancs étaient transmis dans l'URL... Etrange --%>
                        <a href="employes?page=${ requestScope.listPageNumber }&size=10&sortProperty=prenom&sortDirection=<c:choose>
<%-- ---------------------%><c:when test="${ requestScope.pageSortProperty == 'prenom' && requestScope.pageSortDirection == 'ASC'}">DESC">Prenom <span class="glyphicon glyphicon-chevron-down"></span></c:when>
<%-- ---------------------%><c:when test="${ requestScope.pageSortProperty =='prenom' && requestScope.pageSortDirection == 'DESC'}">ASC">Prenom <span class="glyphicon glyphicon-chevron-up"></span></c:when>								
<%-- ---------------------%><c:otherwise>ASC">Prenom</c:otherwise>
						</c:choose></a>						
                    </th>
                    
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                
                <%-- Affichage d'une ligne par employe de la liste. Avec toutes ses infos --%>
                <c:forEach items="${ requestScope.employeeList }" var="employee">
                    <tr>
                        <th scope="row">${ employee.matricule}</th>
                        <td>${employee.nom}</td>
                        <td>${employee.prenom}</td>
                        <td><a href="www.localhost:8080/employes/${employee.id}">Détail</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="row">
                <div class="col-lg-6">
                
                	<%-- Affichage des infos de pagination --%>
                	<c:choose>
                		<c:when test="${((requestScope.listPageNumber+1)*10) <= applicationScope.nombreEmployes}">
                    		<p>Affichage des employés ${(requestScope.listPageNumber*10)+1} à ${(requestScope.listPageNumber+1)*10} sur un total de ${ applicationScope.nombreEmployes }</p>
                    	</c:when>
                    	<c:otherwise>
                    		<p>Affichage des employés ${(requestScope.listPageNumber*10)+1} à ${applicationScope.nombreEmployes} sur un total de ${ applicationScope.nombreEmployes }</p>
                    	</c:otherwise>
                    </c:choose>
                </div>
                <div class="col-lg-6">
                    <ul class="pagination">
                    
               		<%-- Lien pour afficher la page précédente s'il y en a une --%>
                    <c:if test="${ requestScope.listPageNumber != 0 }">
                        <li><a href="localhost:8080/employes?page=${requestScope.listPageNumber-1 }&size=${requestScope.pageSize}&sortProperty=${requestScope.pageSortProperty}&sortDirection=${requestScope.pageSortDirection}">&laquo;</a></li>
                    </c:if>  
                    
                    <%-- Affichage du numero de la page --%>
                    <li><a href="#">Page ${ requestScope.listPageNumber }</a></li>
                        
                    <%-- Lien pour afficher la page suivante s'il y en a une --%>
                    <c:if test="${ ((requestScope.listPageNumber+1)*10) <= applicationScope.nombreEmployes }">
                        <li><a href="localhost:8080/employes?page=${requestScope.listPageNumber+1 }&size=${requestScope.pageSize}&sortProperty=${requestScope.pageSortProperty}&sortDirection=${requestScope.pageSortDirection}">&raquo;</a></li>
                    </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../tags/footer.jsp" %>