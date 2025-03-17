<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% pageContext.setAttribute("newLine", "\n"); %>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<%-- 페이지 내용 --%>
<div class="row">
    <div class="col-12">
        <%-- 검색, 등록 버튼 --%>
        <div class="mb-3 d-flex justify-content-between">
            <%-- 검색 --%>
            <form action="/posts" method="get">
                <div class="input-group">
                    <select name="searchType" class="form-select" style="width: 120px;">
                        <option value="title" ${searchType == 'title' ? 'selected' : ''}>제목</option>
                        <option value="content" ${searchType == 'content' ? 'selected' : ''}>내용</option>
                        <option value="username" ${searchType == 'username' ? 'selected' : ''}>작성자</option>
                        <option value="all" <c:if test="${searchType == null}">selected</c:if>>전체</option>
                    </select>
                    <input type="text" name="searchKeyword" class="form-control" value="${searchKeyword}" placeholder="검색어를 입력하세요" style="width: 300px;">
                    <button type="submit" class="btn btn-primary">검색</button>
                    <c:if test="${searchKeyword != null}">
                        <a href="/posts" class="btn btn-danger">취소</a>
                    </c:if>
                </div>
            </form>
            <%--// 검색 --%>

            <%-- 등록 버튼 --%>
            <a href="/posts/create/" class="btn btn-primary">등록</a>
            <%--// 등록 버튼 --%>
        </div>
        <%--// 검색, 등록 버튼 --%>

        <%-- 게시글 목록 --%>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>등록일시</th>
                    <th>수정일시</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${posts}" var="post">
                <tr>
                    <td>${post.id}</td>
                    <td><a href="/posts/${post.id}">${post.title}</a></td>
                    <td>${post.username}</td>
                    <td>${post.createdAt.substring(0, 16)}</td>
                    <td>${post.updatedAt.substring(0, 16)}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <%--// 게시글 목록 --%>
    </div>
</div>

<div class="row">
    <div class="col-12">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <%-- 이전 페이지 --%>
                <c:if test="${pagination.currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="/posts?page=1">처음</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="/posts?page=${pagination.currentPage - 1}">이전</a>
                    </li>
                </c:if>                
                <%--// 이전 페이지 --%>

                <%-- 페이지 번호 --%>
                <c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="pageNumber">
                    <li class="page-item">
                        <a class="page-link <c:if test='${pageNumber == pagination.currentPage}'>active</c:if>" href="/posts?page=${pageNumber}">${pageNumber}</a>
                    </li>
                </c:forEach>
                <%--// 페이지 번호 --%>

                <%-- 다음 페이지 --%>
                <c:if test="${pagination.currentPage < pagination.totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="/posts?page=${pagination.currentPage + 1}">다음</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="/posts?page=${pagination.totalPages}">마지막</a>
                    </li>
                </c:if>                
                <%--// 다음 페이지 --%>
            </ul>
        </nav>
    </div>
</div>
<%--// 페이지 내용 --%>

<%@ include file="../base/script.jsp" %>

<%-- script --%>
<script>
    /* 자바스크립트 */
</script>
<%--// script --%>

<%@ include file="../base/bottom.jsp" %>
