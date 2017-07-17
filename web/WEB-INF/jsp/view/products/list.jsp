
<template:main htmlTitle="List of Products" bodyTitle="List of Products">
    <jsp:attribute name="content">
        [<a href="<c:url value='/products/new' />">new product</a>]<br />
        <br />
        <c:forEach items="${products}" var="product">
            <c:out value="${product.name} (${product.description})" />
            [<a href="<c:url value='/products/edit/${product.id}'/>">edit</a>]<br/>
        </c:forEach>
    </jsp:attribute>
</template:main>
