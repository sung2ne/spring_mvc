<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLine", "\n"); %>

<%@ include file="../base/top.jsp" %>
<%@ include file="../base/navbar.jsp" %>
<%@ include file="../base/title.jsp" %>
<%@ include file="../base/message.jsp" %>

<%-- 페이지 내용 --%>
<div class="row">
    <div class="col-12">
        <%-- 게시글 보기 --%>
        <div class="card mb-3">
            <%-- card-header --%>
            <div class="card-header">
                <strong>${post.title}</strong>
            </div>
            <%--// card-header --%>

            <%-- card-body --%>
            <div class="card-body">  
                <div class="mb-3 text-muted">
                    글쓴이: ${post.username} | 등록일시: ${post.createdAt.substring(0, 16)} | 수정일시: ${post.createdAt.substring(0, 16)}
                </div>
                <div class="mb-3">
                    ${fn:replace(post.content, newLine, "<br>")}
                </div>
            </div>
            <%-- card-body --%>

            <%-- card-footer --%>
            <div class="card-footer">
                <a href="/posts" class="btn btn-primary">목록</a>
                <a href="/posts/${post.id}/update" class="btn btn-warning">수정</a>
                <button type="button" class="btn btn-danger">삭제</button>
            </div>
            <%--// card-footer --%>
        </div>        
        <%--// 게시글 보기 --%>
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