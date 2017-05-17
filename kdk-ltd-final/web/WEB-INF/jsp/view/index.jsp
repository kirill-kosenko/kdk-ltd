

 <template:main htmlTitle="Index page" bodyTitle="IndexPage">
     <jsp:attribute name="content">
            <table class="table table-striped table-hover ">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Column heading</th>
                        <th>Column heading</th>
                        <th>Column heading</th>
                    </tr>
                </thead>
                <tbody>
                    <tr id="collapse-group">
                        <td>1</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td><a data-toggle="collapse" data-parent="#collapse-group" href="#el1">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                        </td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td><a data-toggle="collapse" data-parent="#collapse-group" href="#el${i.index}">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                        </td>
                    </tr>
                    <tr class="info">
                        <td>3</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td><a data-toggle="collapse" data-parent="#collapse-group" href="#el${i.index}">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                        </td>
                    </tr>
                    <tr class="success">
                        <td>4</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td><a data-toggle="collapse" data-parent="#collapse-group" href="#el${i.index}">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                        </td>
                    </tr>
                    <tr class="danger">
                        <td>5</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td><a data-toggle="collapse" data-parent="#collapse-group" href="#el${i.index}">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                        </td>
                    </tr>
                    <tr class="warning">
                        <td>6</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td><a data-toggle="collapse" data-parent="#collapse-group" href="#el${i.index}">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                        </td>
                    </tr>
                    <tr class="active">
                        <td>7</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td><a data-toggle="collapse" data-parent="#collapse-group" href="#el${i.index}">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                        </td>
                    </tr>
                </tbody>
        </table>
        <a href="<c:url value='/products/list'/>">Просмотр продуктов</a><br/>
        <a href="<c:url value='/partners/list'/>">Просмотр контрагентов</a><br/>
        <a href="<c:url value='/documents/list'/>">Просмотр документов</a><br/>

    </jsp:attribute>
 </template:main>
