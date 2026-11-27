# タスク管理システム

Java 17、Tomcat 9、JSP/Servlet、MySQL 8 を使用するEclipse Dynamic Web Projectです。画面ID G01〜G18の設計を個別画面として実装しています。

## セットアップ

1. MySQL 8 で `src/main/webapp/WEB-INF/sql/schema.sql` を実行します。
2. `m_user` に、設計書に基づく利用者ID・パスワード・氏名を登録します。設計資料には初期利用者が定義されていないため、利用可能な認証情報は既存環境または管理者から取得してください。
3. 接続情報を設定します。接続先は `jdbc:mysql://localhost:3306/task_db` が既定値です。必要に応じて環境変数 `TASK_DB_URL`、`TASK_DB_USER`、`TASK_DB_PASSWORD`（または同名の `task.db.url` 等のJavaシステムプロパティ）を指定します。
4. Eclipseに既存プロジェクトとして取り込み、Java 17/Tomcat 9のランタイムを設定して実行します。MySQL Connector/J 8.0.26 は `WEB-INF/lib` に含まれています。
5. ブラウザーで `/project_ex/login` を開きます。ログインセッションは30分の無操作で失効します。

## 機能

- ログイン、ログアウト、未認証アクセス制御
- タスク登録、期限順一覧、詳細、担当者本人による編集・複数削除
- コメント投稿、投稿者本人による編集・削除
- タスクと関連コメントの一括削除を同一DBトランザクションで実行

DBテーブルと初期カテゴリ・ステータスは設計書の項目に合わせています。認証処理は設計書の `m_user.password` と入力値を照合する前提です。
