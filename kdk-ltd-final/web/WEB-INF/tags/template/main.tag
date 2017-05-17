<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="utf-8" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
        required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
        required="true" %>
<%@ attribute name="headContent" fragment="true" required="false" %>
<%@ attribute name="content" fragment="true" required="true" %>
<%@ attribute name="footerContent" fragment="true" required="false" %>
<%@ include file="/WEB-INF/jsp/base.jspf"%>

<!DOCTYPE html>
<html>

    <head>
        <title>Wheaten :: <c:out value="${fn:trim(htmlTitle)}" /></title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <script src="<c:url value='/resources/js/jquery-2.1.4.js'/>"></script>
        <script src="<c:url value='/resources/js/jquery.serializeToJSON.js'/>"></script>
        <script src="<c:url value='/resources/js/moment-with-locales.min.js'/>"></script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/> "></script>
        <script src="<c:url value='/resources/js/bootstrap-datetimepicker.min.js'/> "></script>

        <link rel="stylesheet"
                href="<c:url value="/resources/css/bootstrap.min.css" />">
        <link rel="stylesheet"
              href="<c:url value="/resources/css/bootstrap-datetimepicker.css" />">
        <link rel="stylesheet"
                href="<c:url value="/resources/css/styles.css" />">
        <jsp:invoke fragment="headContent" />
    </head>

    <body>
        <template:nav />
        <div class="container" id="container">
            <h1><c:out value="${fn:trim(bodyTitle)}" /></h1>
            <jsp:invoke fragment="content" />
        </div>

        <jsp:invoke fragment="footerContent" />
        <jsp:doBody />
    </body>
</html>
