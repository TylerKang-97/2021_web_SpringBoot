package kr.mjc.tyler.web.mvc;

import kr.mjc.tyler.web.dao.Article;
import kr.mjc.tyler.web.dao.ArticleDao;
import kr.mjc.tyler.web.dao.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Slf4j
public class ArticleController {

    private final ArticleDao articleDao;

    @Autowired
    public ArticleController(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * 게시글 목록 화면
     */
    public void articleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("articleList", articleDao.listArticles(0, 100));

        request.getRequestDispatcher("/WEB-INF/jsp/model2/article/articleList.jsp")
                .forward(request, response);
    }

    /**
     * 게시글 등록 화면
     */
    public void articleForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/model2/article/articleForm.jsp")
                .forward(request, response);
    }

    /**
     * 글 보기 화면 (한 건 조회)
     */
    public void getArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String articleId = request.getParameter("id");
        Article article = articleDao.getArticle(Integer.parseInt(articleId));
        request.setAttribute("ARTICLE", article);
        request.getRequestDispatcher("/WEB-INF/jsp/model2/article/getArticle.jsp")
                .forward(request, response);
    }

    /**
     * 게시글 등록 액션
     */
    public void addArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        Article article = new Article();
        User user = (User) session.getAttribute("USER");
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("content"));
        article.setName(user.getName());
        article.setUserId(user.getUserId());


        try {
            articleDao.addArticle(article);
            response.sendRedirect(request.getContextPath() + "/mvc/article/articleList");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() +
                    "/mvc/article/articleList?msg=Something Wrong. Try this again.");
        }
    }

    /**
     * 게시글 수정
     */
    public void stateArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        request.getRequestDispatcher("/WEB-INF/jsp/model2/article/articleList.jsp")
                .forward(request, response);
    }
    /**
     * 게시글 수정
     * public int updateArticle(Article article) {
     *         return namedParameterJdbcTemplate
     *                 .update(UPDATE_ARTICLE, new BeanPropertySqlParameterSource(article));
     *     }
     *
     * 게시글 삭제
     *public int deleteArticle(int articleId, int userId) {
     *Map<String, Object> params = new HashMap<>();
     *params.put("articleId", articleId);
     *params.put("userId", userId);
     *return namedParameterJdbcTemplate.update(DELETE_ARTICLE, params);
     *}
     */
}
