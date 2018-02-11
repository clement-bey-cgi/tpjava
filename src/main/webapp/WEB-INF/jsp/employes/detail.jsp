<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../tags/header.jsp" %>

<div class="container">
    <c:if test="${ requestScope.consultationMode == 'enabled' }">
    	<h2>Détail du 
    		<c:if test="${ requestScope.employeeJob == 'com' }">commercial</c:if>
    		<c:if test="${ requestScope.employeeJob == 'tec' }">technicien</c:if>
    		<c:if test="${ requestScope.employeeJob == 'man' }">manager</c:if> 
    		${ requestScope.consultedEmployee.matricule }</h2>
    </c:if>

	<%-- change l'url d'envoi du formulaire selon le mode création ou edition --%>
    <div class="row">
        <form id="saveForm" 
        	<c:choose>
        	 	<c:when test="${ requestScope.commercialCreationMode =='enabled' }">
        	 		action="http://localhost:8080/commercials/save"
        	 	</c:when>
        	 	<c:when test="${ requestScope.managerCreationMode =='enabled' }">
        	 		action="http://localhost:8080/managers/save"
        	 	</c:when>
        	 	<c:when test="${ requestScope.technicianCreationMode =='enabled' }">
        	 		action="http://localhost:8080/techniciens/save" 
        	 	</c:when>
        	 	<c:when test="${ requestScope.technicianCreationMode =='enabled' }">
        	 		action="http://localhost:8080/techniciens/save" 
        	 	</c:when>        	 
        	 	<c:when test="${ requestScope.employeeJob == 'com' && requestScope.commercialCreationMode != 'enabled' }">
        	 		action="http://localhost:8080/commercials/${ requestScope.consultedEmployee.getId()}" 
        	 	</c:when>   
        	 	<c:when test="${ requestScope.employeeJob == 'tec' && requestScope.technicianCreationMode != 'enabled' }">
        	 		action="http://localhost:8080/techniciens/${ requestScope.consultedEmployee.getId()}" 
        	 	</c:when>
        	 	<c:when test="${requestScope. employeeJob == 'man' && requestScope.managerCreationMode != 'enabled' }">
        	 		action="http://localhost:8080/managers/${ requestScope.consultedEmployee.getId()}" 
        	 	</c:when>        	 	         	 	         	 		
        	 </c:choose>
        method="post">
        
        <%-- Remplissage des champs du formulaire avec les infos de l'employe si consultation --%>
        <%-- Si création, les champs restent vides --%>
        <div class="col-lg-6">
            <div class="form-group">
                <label class="form-control-label" for="nom">Nom</label>
                <input type="text" value="${ requestScope.consultedEmployee.nom }" class="form-control" name="nom" id="nom">

                <label class="form-control-label" for="prenom">Prénom</label>
                <input type="text" value="${ requestScope.consultedEmployee.prenom }" class="form-control" name="prenom" id="prenom">

                <label class="form-control-label" for="matricule">Matricule</label>
                <input type="text" value="${ requestScope.consultedEmployee.matricule }" class="form-control" name="matricule" id="matricule">
            </div>
        </div>
        <div class="col-lg-6">
            <div class="form-group">
                <label class="form-control-label" for="nom">Salaire</label>
                <div class="input-group">
                    <input type="number" value="${ requestScope.consultedEmployee.salaire }" class="form-control" name="salaire" id="salaire">
                    <span class="input-group-addon">€</span>
                </div>
                
                <%-- Si consultation et pas création --%>
				<c:if test="${ requestScope.consultationMode == 'enabled' }" >
	                <label class="form-control-label" for="nom">Prime Annuelle</label>
	                <div class="input-group">
	                    <input type="text" value="${ requestScope.consultedEmployeeYearBonus }" class="form-control" name="primeAnnuelle" id="primeAnnuelle">
	                    <span class="input-group-addon">€</span>
	                </div>
				</c:if>

                <label class="form-control-label" for="nom">Date d'embauche</label>
                <input type="text" value="${ requestScope.consultedEmployee.dateEmbauche }" class="form-control" name="dateEmbauche" id="dateEmbauche">
                
                <%-- Si employe est un commercial,  --%>
				<c:if test="${ requestScope.employeeJob == 'com'}">
	                <label class="form-control-label" for="performance">Performance</label>
	                <input type="number" value="${ requestScope.consultedEmployee.performance }" class="form-control" name="performance" id="performance">
	
	                <label class="form-control-label" for="caAnnuel">CA Annuel</label>
	                <div class="input-group">
	                    <input type="number" value="${ requestScope.consultedEmployee.caAnnuel }" class="form-control" name="caAnnuel" id="caAnnuel">
	                    <span class="input-group-addon">€</span>
	                </div>
                </c:if>
				
				<%-- Affichage du grade pour les techniciens en consult / creation de technicien  --%>
				<c:if test="${ requestScope.employeeJob == 'tec' || requestScope.technicianCreationMode == 'enabled'}">
	                <label class="form-control-label" for="grade">Grade</label>
	                <input type="number" value="${ requestScope.consultedEmployee.grade }" class="form-control" name="grade" id="grade">
				</c:if>
				
				<%-- Affichage de l'equipe d'un manager item par item --%>
				<c:if test="${ requestScope.employeeJob == 'man' && requestScope.consultationMode == 'enabled' }">
                <label class="form-control-label" for="performance">Equipe</label>
                
                <c:forEach items="${ requestScope.consultedEmployee.equipe }" var="managedEmployee">
                <div class="row">
                    <div class="col-lg-10">
                        <ul class="list-group">
                                <li class="list-group-item">
                                
                                	<%-- Lien vers la page de detail de l'employe en question --%>
                                	<a href="http://localhost:8080/employes/${managedEmployee.id}">${managedEmployee.nom} ${managedEmployee.prenom} 
                                		<span class="badge pull-right">${managedEmployee.matricule}</span>
                                	</a>
                                </li>
                        </ul>
                    </div>
                    <div class="col-lg-2 text-center">
                        <div class="list-group text-center">
                                <li class="list-group-item">
                                
                                	<%-- Composition du lien qui sert a retirer un manager a un technicien --%>
                                	<a href="http://localhost:8080/techniciens/${ managedEmployee.id }/removemanager/${ requestScope.consultedEmployee.getId() }">
                                		<span class="glyphicon glyphicon-remove"></span>
                                	</a>
                                </li>
                        </div>
                    </div>
                </div>
                </c:forEach>
                
                </c:if>
            </div>
        </div>
        </form>
        <div class="col-lg-6">
        
        	<%-- Si l employe en consultation est manager --%>
        	<c:if test="${ requestScope.employeeJob == 'man' && requestScope.consultationMode == 'enabled' }">
	            <form action="http://localhost:8080/managers/${ requestScope.consultedEmployee.getId() }/addtechnicien" method="get">
	                <div class="col-lg-10">
	                    <input type="text" name="matricule" value="" placeholder="Ajouter un technicien avec le matricule..." class="form-control">
	                </div>
	                <div class="col-lg-2 text-center">
	                    <button type="submit" class="btn-success list-group-item list-group-item-action"><span class="glyphicon glyphicon-plus"></span></button>
	                </div>
	            </form>
            </c:if>
			
			<%-- Si l'employe est un technicien en consultation ... --%>
			<c:if test="${ requestScope.employeeJob == 'tec' && requestScope.consultationMode == 'enabled' }">
	                <div class="row">
	                
	                <c:choose>
	                	<%-- Et qu'il a un manager, on l'affiche --%>
	                	<c:when test="${ requestScope.consultedEmployee.manager != '' && requestScope.consultedEmployee.manager != null }">
		                    <div class="col-lg-12">
		                        <label class="form-control-label">Manager</label>
		                    </div>
		                    <div class="col-lg-10">
		                        <ul class="list-group">
		                            <li class="list-group-item">
		                                <a href="http://localhost:8080/employes/${requestScope.consultedEmployee.getManager().getId()}">
		                                	${ requestScope.consultedEmployee.getManager().getNom() } ${ requestScope.consultedEmployee.getManager().getPrenom() }
		                                    <span class="badge pull-right">${ requestScope.consultedEmployee.getManager().getMatricule() }</span>
		                                </a>
		                            </li>
		                        </ul>
		                    </div>
		                    <div class="col-lg-2">
		                        <li class="list-group-item">
		                        	<a href="http://localhost:8080/techniciens/${ requestScope.consultedEmployee.getId() }/removemanager/${ requestScope.consultedEmployee.getId() }">
		                        		<span class="glyphicon glyphicon-remove"></span>
		                        	</a>
		                        </li>
		                    </div>
		                 </c:when>
		                 
		                 <%-- Sinon, mise en place du formulaire d'ajout manager à un technicien --%>
		                 <c:otherwise>
		                    <form action="http://localhost:8080/techniciens/${ requestScope.consultedEmployee.getId() }/addmanager" 
		                    method="get">
		                    <div class="col-lg-10">
		                        <input type="text" name="matricule" value="" placeholder="Ajouter un manager avec le matricule..." class="form-control">
		                    </div>
		                    <div class="col-lg-2 text-center">
		                        <button type="submit" class="btn-success list-group-item list-group-item-action"><span class="glyphicon glyphicon-plus"></span></button>
		                    </div>

		                    </form>
		                  </c:otherwise>  
	                </c:choose>
	                </div>
            </c:if>
        </div>
        <div class="col-lg-6">
            <input form="saveForm" class="btn btn-primary" type="submit" value="Enregistrer"/>
            
            <%-- Boutton qui pointe vers la page de suppression de l'employe consulté --%>
            <a href="http://localhost:8080/employes/${ requestScope.consultedEmployee.getId() }/delete"><button class="btn btn-danger">Supprimer</button></a>
        </div>
    </div>
</div>

<%@ include file="../tags/footer.jsp" %>