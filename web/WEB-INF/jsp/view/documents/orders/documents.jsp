
<html>
<head>
    <!--<link href="<c:url value='/resources/css/styles.css'/>" rel="stylesheet"> -->
    <title></title>
</head>
<body>
    <h1>${title}</h1>
    <div class="CSSTableGenerator">
    <table>
           <tr>
                <th>#</th>
                <th>Тип документа</th>
                <th>Дата создания</th>
                <th>Контрагент</th>
            </tr>

        <c:forEach items="${documents}" var="document">
            <tr>
                <td><c:out value="${document.id}"/></td>
                <td><c:out value="${document.documentType}"/></td>
                <td><c:out value="${document.insertTimestamp}"/></td>
                <td><c:out value="${document.partner.lastname}"/></td>
                <table>
                         <tr>
                            <th># партии</th>
                            <th>Наименование</th>
                            <th>Количество</th>
                            <th>Цена</th>
                            <th>Сумма</th>
                        </tr>

                    <c:forEach items="${document.details}" var="detail">
                        <tr>
                            <td><c:out value="${detail.id}"/></td>
                            <td><c:out value="${detail.product.name}"/></td>
                            <td><c:out value="${detail.quantity}"/></td>
                            <td><c:out value="${detail.price}"/></td>
                            <td><c:out value="${detail.sum}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </tr>
        </c:forEach>
    </table>
    </div>
</body>
</html>