<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/base.jspf"%>

<nav class="navbar navbar-inverse navbar-fixed-top" id="top-nav">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                    data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                    aria-expanded="false">
                <span class="sr-only">Target navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Brand</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-haspopup="true" aria-expanded="false">Документы<span class="caret"></span> </a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/documents/orders'/>">Заказы</a></li>
                        <li><a href="<c:url value='/documents/purchase'/>">Входящие</a></li>
                        <li><a href="<c:url value='/documents/sale'/>">Исходящие</a></li>
                        <li><a href="<c:url value='/documents/movement'/>">Перемещения</a> </li>
                        <li role="separator" class="divider"></li>
                        <li><a href="<c:url value='/documents/new'/>">Создать</a> </li>
                    </ul>
                </li>
                <li><a href="#"></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span> </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another Action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>

            <form class="navbar-form navbar-right" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Клиенты<span class="caret"></span> </a>
                    <ul class="dropdown-menu" id="partner-menu">
                        <li><a href="<c:url value='/partners/new?p'/>">Добавить</a></li>
                        <li><a href="<c:url value='/partners?p'/> ">Список</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Поиск</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Товары<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/products/new'/> ">Новый</a></li>
                        <li><a href="<c:url value='/products/list'/>">Список</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Поиск</a></li>
                    </ul>
                </li>
            </ul>

        </div>

    </div>
</nav>
<script>
    $(deal).on('ready', function() {
        $("#partner-menu").on('click', 'a', function (event) {
            event.preventDefault();
            $.get($(this).attr("href"), function(data) {
                alert(data);
                $("#container").html(data);
            }).fail(function() {
                alert("error");
            });
        })
    })
</script>
<jsp:doBody />
