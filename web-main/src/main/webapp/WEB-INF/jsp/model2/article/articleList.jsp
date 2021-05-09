
<%@ page import="java.util.List" %>
<%@ page import="kr.mjc.tyler.web.dao.Article" %>
<!DOCTYPE html>
<html>
<body>
<nav><a href="/model2/user/loginForm">로그인</a> <a href="/model2/user/userForm">회원가입</a></nav>
<h3>게시글 목록</h3>
<button onclick="location='articleForm'">게시글 작성하기</button>
<%
    List<Article> articleList = (List<Article>) request.getAttribute("articleList");
    for (Article article : articleList) {%>

<p><%= article %>
    <% String userNames = article.getName(); %>
    <% int articleID = article.getArticleId(); %>

    <a href="/model2/article/getArticle?num=<%=article.getArticleId()%>">article:<%= articleID%>번의 <%= userNames%>님의 게시글 보러 가기 </a>

</p>
<%
    }
%>
</body>
</html>