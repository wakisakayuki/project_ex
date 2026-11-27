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

## ログイン時に「ページを表示できません」と出る場合

従来のログイン処理ではSQLExceptionをE013として表示していました。
この更新ではDB処理の失敗をE015として表示し、Eclipseのコンソールにも
例外本文、SQLState、ErrorCode、原因のスタックトレースを出力します。
認証情報が不一致の場合は従来どおりE005です。

### A5M2とJavaのDB接続設定を合わせる

A5M2の接続情報はJavaには自動で引き継がれません。
接続設定を指定しない場合、Java側はlocalhost:3306/task_dbに、
DBユーザーroot・空パスワードで接続します。
アプリ画面で入力するdemo001等はm_userの利用者IDで、DB接続ユーザーとは別です。

1. A5M2で使用中の接続のホスト、ポート、DBユーザー、DBパスワードを確認します。
2. EclipseのServersビューでTomcatをダブルクリックし、起動構成を開きます。
3. 起動構成の「環境」タブに次の環境変数を追加します。
   - TASK_DB_URL: jdbc:mysql://接続ホスト:接続ポート/task_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Tokyo
   - TASK_DB_USER: A5M2のDB接続ユーザー
   - TASK_DB_PASSWORD: A5M2のDB接続パスワード
4. 「引数」タブに-Dtask.db.url、-Dtask.db.user、-Dtask.db.passwordがある場合は、
   それらが環境変数より優先されるため同じ接続情報に合わせます。
5. 保存してTomcatを停止・再起動します。
6. アプリ画面で登録済み利用者のIDとパスワードを入力します。

DBパスワードはローカルの起動構成に設定し、リポジトリには登録しないでください。

### ログに応じた対応

- Access denied for user: DB接続ユーザー・パスワード・アクセス権を確認します。
- Communications link failure / Connection refused: MySQLの起動状態と接続ホスト・ポートを確認します。
- Unknown database: 接続先にtask_dbがあるか確認します。
- Table ... doesn't exist / Unknown column: 接続先DBのテーブル定義をschema.sqlと照合します。
  CREATE TABLE IF NOT EXISTSでは既存テーブルの定義は修正されません。
- MySQL JDBC driver is unavailable / ClassNotFoundException:
  src/main/webapp/WEB-INF/lib/mysql-connector-java-8.0.26.jarが
  Tomcatの配置先WEB-INF/libにも含まれているか確認します。

原因の例外を確認する前にDBやテーブルを削除・再作成する必要はありません。
