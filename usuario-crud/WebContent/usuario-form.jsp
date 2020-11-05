<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Usuário - Cad</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: blue">
			<div>
				<a href="https://github.com/edudevhome" class="navbar-brand"> Cadastro de Usuário </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Lista de usuários</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${usuario != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${usuario == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${usuario != null}">
            			Editar Usuário
            		</c:if>
						<c:if test="${usuario == null}">
            			Novo Usuário
            		</c:if>
					</h2>
				</caption>

				<c:if test="${usuario != null}">
					<input type="hidden" name="id" value="<c:out value='${usuario.id}' />" />
				</c:if>
				<!-- Conjunto de campo -->
				<fieldset class="form-group">
					<label>Nome</label> <input type="text"
						value="<c:out value='${usuario.nome}' />" class="form-control"
						name="nome" required="required">
				</fieldset>
				<!-- Conjunto de campo -->
				<fieldset class="form-group">
					<label>Email</label> <input type="text"
						value="<c:out value='${usuario.email}' />" class="form-control"
						name="email">
				</fieldset>
				<!-- Conjunto de campo -->
				<fieldset class="form-group">
					<label>Senha</label> <input type="password"
						value="<c:out  value='${usuario.senha}' />" class="form-control"
						name="senha">
				</fieldset>

				<button type="submit" class="btn btn-outline-primary">Atualizar</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
