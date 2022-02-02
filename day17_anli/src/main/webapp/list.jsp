
<%--  Created by IntelliJ IDEA.--%>
<%--  User: YSK--%>
<%--  Date: 2021/10/8--%>
<%--  Time: 21:05--%>
<%--  To change this template use File | Settings | File Templates.--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息列表</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.6.0.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>

    <script>
        function deleteUser(id){
            //用户安全提示
            if(confirm("您确定要删除吗？")){
                location.href="${pageContext.request.contextPath}/delUserServlet?id="+id;
            }
        }

        window.onload=function (){
            document.getElementById("delSelected").onclick=function () {
                //表单提交
                //用户安全提示
                if(confirm("您确定要删除吗？")){

                    //遍历看是否有被选中，不然会空指针异常
                    var flag=false;
                    var cbs=document.getElementsByName("uid");
                    for(var i=0;i<cbs.length;i++){
                        if(cbs[i].checked){
                            flag=true;
                            break;
                        }
                    }

                    if(flag==true){
                        document.getElementById("form").submit();
                    }

                }
            }

            document.getElementById("firstCb").onclick=function () {
                //获取下边列表中所有的cb
                var cbs=document.getElementsByName("uid");

                for(var i=0;i<cbs.length;i++){
                    //this指代id为firstCb的组件
                    cbs[i].checked=this.checked;
                }
            }
        }



    </script>

</head>
<body>

<div class="container">

<div align="center">
<h1>用户信息列表</h1>
</div>

<div style="float: left">
    <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post">
        <div class="form-group">
            <label for="exampleInputName2">姓名</label>
            <input type="text" name="name" value="${condition.name[0]}" class="form-control" id="exampleInputName2">
        </div>

        <div class="form-group">
            <label for="exampleInputName2">籍贯</label>
            <input type="text" name="address" value="${condition.address[0]}" class="form-control" id="exampleInputName3">
        </div>

        <div class="form-group">
            <label for="exampleInputEmail2">邮箱</label>
            <input type="email" name="email" value="${condition.email[0]}" class="form-control" id="exampleInputEmail2">
        </div>
        <button type="submit" class="btn btn-default">查询</button>
    </form>
</div>



<div style="float: right;margin:5px ">
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>
    <a class="btn btn-primary" href="javascript:void(0);" id="delSelected">删除选中</a>
</div>

<form id="form" action="${pageContext.request.contextPath}/delSelectedServlet" method="post">
    <table border="1" class="table table-hover table table-bordered">
    <tr class="success">
        <th><input type="checkbox" id="firstCb"></th>
        <th>编号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>年龄</th>
        <th>籍贯</th>
        <th>QQ</th>
        <th>邮箱</th>
        <th>操作</th>
    </tr>

    <c:forEach items="${pb.list}" var="user" varStatus="s">

            <tr>
                <th><input type="checkbox" name="uid" value="${user.id}"></th>
                <td>${s.count}</td>
                <td>${user.name}</td>
                <td>${user.gender}</td>
                <td>${user.age}</td>
                <td>${user.address}</td>
                <td>${user.qq}</td>
                <td>${user.email}</td>
                <td>
                    <a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>
                    &nbsp&nbsp&nbsp&nbsp&nbsp;
                    <a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id});">删除</a>
                </td>
            </tr>

    </c:forEach>

</table>
</form>
<%--分页查询--%>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">

                <c:if test="${pb.currentPage>1 && pb.totalPage>1}">
                    <li>
                        <a id="prev" href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage-1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>


<%--                生成分页查询--%>
                <c:forEach begin="1" end="${pb.totalPage}" var="i">
                    <c:if test="${pb.currentPage==i}">
                    <li class="active"><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>

                    <c:if test="${pb.currentPage!=i}">
                        <li ><a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>

                </c:forEach>


                <c:if test="${pb.currentPage<pb.totalPage && pb.totalPage>1}">
                    <li>
                        <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage+1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>


                <br>
                <br>
                <span style="font-size: 25px;margin-left: 5px">
                    共${pb.totalCount}条记录，共${pb.totalPage}页
                </span>

            </ul>
        </nav>
    </div>

</div>

</body>
</html>
