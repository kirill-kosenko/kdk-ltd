<template:main htmlTitle="Partners" bodyTitle="Partners">
    <jsp:attribute name="content">
        [<a href="<c:url value='/partners/new' />">new partner</a>]<br />
        <br />
        <c:forEach items="${partners.content}" var="partner">
            <c:out value="${partner.fullname}"/> - <c:out value="${partner.phones[0].fullNumber}"/>
            [<a href="<c:url value='/products/edit/${product.id}'/>">edit</a>]<br/>
        </c:forEach>
    </jsp:attribute>
</template:main>
