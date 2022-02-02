<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>登录成功</title>
</head>
<body>
<div align="center">
  <h1>${admin.getUsername()}，欢迎您</h1>
</div>
<div align="center">

  <a href="${pageContext.request.contextPath}/findUserByPageServlet">
    <h3>查询所有信息</h3>
  </a>
</div>

</body>
</html>