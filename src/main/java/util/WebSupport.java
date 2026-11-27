package util;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 文字コード設定やJSPへの転送など、Web共通処理を提供するクラス。
 */
public final class WebSupport {
    private static final Map<String, String> MESSAGES = new HashMap<>();
    static {
        MESSAGES.put("E001", "必須項目を入力してください。");
        MESSAGES.put("E002", "文字数を指定範囲内に収めてください。");
        MESSAGES.put("E003", "入力内容を指定表記に訂正してください。");
        MESSAGES.put("E004", "過去の日付は期限に設定できません。");
        MESSAGES.put("E005", "ユーザIDまたはパスワードが違います。");
        MESSAGES.put("E006", "削除するタスクが選択されていません。");
        MESSAGES.put("E007", "タスクを登録できませんでした。");
        MESSAGES.put("E008", "コメント入力に誤りがあります。");
        MESSAGES.put("E009", "コメントを投稿できませんでした。");
        MESSAGES.put("E010", "この操作を行う権限がありません。");
        MESSAGES.put("E011", "削除できませんでした。");
        MESSAGES.put("E012", "対象のデータが見つかりませんでした。");
        MESSAGES.put("E013", "ページを表示できません。");
        MESSAGES.put("E014", "ログイン状態ではありません。ログインしてください。");
        MESSAGES.put("I001", "タスクを登録しました。");
        MESSAGES.put("I002", "タスクを編集しました。");
        MESSAGES.put("I003", "タスクを削除しました。");
        MESSAGES.put("C001", "選択したタスクを削除しますか？");
        MESSAGES.put("C002", "ログアウトしますか？");
        MESSAGES.put("I004", "担当者本人のみ編集可能です。");
        MESSAGES.put("I005", "コメント投稿者本人のみ編集・削除可能です。");
    }

    private WebSupport() {
    }

    public static String message(String id) {
        return MESSAGES.getOrDefault(id, "ページを表示できません。");
    }

    public static void error(HttpServletRequest req, String id) {
        req.setAttribute("messageId", id);
        req.setAttribute("message", message(id));
    }

    public static String userId(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s == null ? null : (String) s.getAttribute("user_id");
    }

    public static String path(HttpServletRequest req, String p) {
        return req.getContextPath()+p;
    }

    public static int positiveInt(String value) {
        try {
            int n = Integer.parseInt(value);
            return n>0 ? n : -1;
        }
        catch (Exception e) {
            return -1;
        }
    }

    public static void log(ServletContext c, Exception e) {
        c.log("タスク管理システム処理エラー", e);
    }

    public static void forward(HttpServletRequest r, HttpServletResponse s, String jsp) throws ServletException, IOException {
        r.getRequestDispatcher("/WEB-INF/jsp/"+jsp).forward(r, s);
    }

    public static boolean required(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean max(String s, int n) {
        return s == null || s.length() <= n;
    }
}
