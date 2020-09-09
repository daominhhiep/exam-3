<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 09-Sep-20
  Time: 10:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/vendors/simple-line-icons/css/simple-line-icons.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/vendors/flag-icon-css/css/flag-icon.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"><!-- End layout styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendors/css/vendor.bundle.base.css">
</head>
<body>

<div class="row" style="clear: both">
    <div class="col-12 grid-margin stretch-card">
        <div class="card">
            <div class="card-body">
                <p class="card-title" style="margin-bottom: 20px; float: right">Thêm mới / cập nhật sản phẩm</p>
                <form class="forms-sample" method="post" style="clear: both">
                    <div class="form-group">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id"
                               value="${product.getProductId()}"
                               name="product-id" hidden>
                    </div>
                    <div class="form-group">
                        <label for="name">Tên sản phẩm</label>
                        <input type="text" class="form-control" id="name"
                        <c:choose>
                        <c:when test="${product.getName() == null}">
                               placeholder="Tên sản phẩm không được để trống"
                        </c:when>
                        <c:otherwise>
                               value="${product.getName()}"
                        </c:otherwise>
                        </c:choose>
                               name="product-name">
                    </div>
                    <div class="form-group">
                        <label for="price">Giá</label>
                        <input type="text" class="form-control" id="price"
                        <c:choose>
                        <c:when test="${product.getPrice() == null}">
                               placeholder="Giá sản phẩm không được để trống"
                        </c:when>
                        <c:otherwise>
                               value="${product.getPrice()}"
                        </c:otherwise>
                        </c:choose>
                               name="product-price">
                    </div>
                    <div class="form-group">
                        <label for="quantity">Số lượng</label>
                        <input type="text" class="form-control" id="quantity"
                        <c:choose>
                        <c:when test="${product.getPrice() == null}">
                               placeholder="Số lượng sản phẩm không được để trống"
                        </c:when>
                        <c:otherwise>
                               value="${product.getQuantity()}"
                        </c:otherwise>
                        </c:choose>
                               name="product-quantity">
                    </div>
                    <div class="form-group">
                        <label for="color">Màu sắc</label>
                        <input type="text" class="form-control" id="color"
                               value="${product.getColor()}"
                               name="product-color">
                    </div>
                    <div class="form-group">
                        <label for="category">Hãng</label>
                        <select class="form-control" id="category" name="category-id">
                            <c:forEach items="${categoryList}" var="category">
                                <option value="${category.getId()}"
                                        <c:if test="${product.getCategory().getId() == category.getId()}">
                                            selected
                                        </c:if>
                                >${category.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="description">Mô tả</label>
                        <textarea class="form-control" id="description" rows="4"
                                  value="${product.getDescription()}"
                                  name="product-description">${product.getDescription()}</textarea>
                    </div>
                    <button type="submit" class="btn btn-primary mr-2">
                        <c:choose>
                            <c:when test="${action == 'create'}">
                                Thêm mới
                            </c:when>
                            <c:when test="${action == 'edit'}">
                                Cập nhật
                            </c:when>
                        </c:choose>
                    </button>
                    <button class="btn btn-light" type="reset" href="/home">Huỷ bỏ</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>