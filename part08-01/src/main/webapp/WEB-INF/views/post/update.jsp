<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="../base/head.jsp" %>
<body>
    <div class="container">
        <!-- 네비게이션 -->
        <%@ include file="../base/navbar.jsp" %>
        <!--// 네비게이션 -->

        <!-- 페이지 제목 -->
        <%@ include file="../base/title.jsp" %>
        <!--// 페이지 제목 -->

        <!-- 메시지 -->
        <%@ include file="../base/message.jsp" %>
        <!--// 메시지 -->

        <%-- 페이지 내용 --%>
        <div class="row">
            <div class="col-12">
                <%-- 게시글 수정 --%>
                <form id="updateForm" action="/posts/${post.id}/update" method="POST">
                    <div class="card mb-3">
                        <div class="card-header">
                            게시글 수정 (<span class="text-danger">*</span> 표시는 필수항목입니다.)
                        </div>
                        <div class="card-body">   
                            <%-- 제목 --%>           
                            <div class="mb-3">
                                <label for="title" class="form-label">제목<span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요" value="${post.title}">
                            </div>
                            <%--// 제목 --%>

                            <%-- 내용 --%>
                            <div class="mb-3">
                                <label for="content" class="form-label">내용<span class="text-danger">*</span></label>
                                <textarea class="form-control" id="content" name="content" rows="5" placeholder="내용을 입력하세요">${post.content}</textarea>
                            </div>
                            <%--// 내용 --%>

                            <%-- 작성자 --%>
                            <div class="mb-3">
                                <label for="username" class="form-label">작성자<span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="작성자를 입력하세요" value="${post.username}">
                            </div>
                            <%--// 작성자 --%>

                            <%-- 비밀번호 --%>
                            <div class="mb-3">
                                <label for="password" class="form-label">비밀번호<span class="text-danger">*</span></label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요">
                            </div>     
                            <%--// 비밀번호 --%>               
                        </div>
                        <div class="card-footer">
                            <div>
                                <button type="submit" class="btn btn-primary">수정</button>
                                <a href="/posts" class="btn btn-secondary">취소</a>
                            </div>
                        </div>
                    </div>
                </form>
                <%--// 게시글 수정 --%>
            </div>
        </div>
        <%--// 페이지 내용 --%>
    </div>

    <%@ include file="../base/script.jsp" %>

    <%-- script --%>
    <script>
        $(document).ready(function() {
            // 게시글 폼 검증
            $('#updateForm').submit(function(event) {
                event.preventDefault();

                if ($('#title').val() == '') {
                    alert('제목을 입력하세요.');
                    $('#title').focus();
                    return false;
                }

                if ($('#content').val() == '') {
                    alert('내용을 입력하세요.');
                    $('#content').focus();
                    return false;
                }

                if ($('#username').val() == '') {
                    alert('작성자를 입력하세요.');
                    $('#username').focus();
                    return false;
                }

                if ($('#password').val() == '') {
                    alert('비밀번호를 입력하세요.');
                    $('#password').focus();
                    return false;
                }

                return true;
            });
        });
    </script>
    <%--// script --%>
</body>
</html>
