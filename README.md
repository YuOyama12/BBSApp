# BBSApp

## 概要
シンプルな掲示板風のアプリです。Firebaseを導入しています。<br>（利用する際には、[こちら](https://firebase.google.com/docs/android/setup)の手順に従い、"google-services.json"等をフォルダ内に導入する必要があります。）

### 画面概要

1. ログイン画面にて、アカウント作成時に入力したメールアドレスやパスワードを用いてログインします。

   ![appImage1](https://github.com/YuOyama12/BBSApp/assets/94959504/7e5683f1-2bae-4d77-b36e-09579b8b373c)
- 「ゲストとしてログイン」を押すことで、アカウントを作成せずに、アプリを体験することも可能です。
- 「アカウントを作成」を押すことで、新たにアカウントを作成することができます。（メールアドレス、パスワード、ユーザネームを必要とします。）

2. ログイン後（又は、アカウント作成後）、作成されたスレッドが一覧で表示されます。ハートマークを押すことでお気に入り登録をしたり、画面下部のNavigationBarにてお気に入りスレッド一覧画面と切り替えたりすることができます。

   ![appImage2](https://github.com/YuOyama12/BBSApp/assets/94959504/dcc34506-cfee-417d-8c00-b1a5e3f898ef)
- 画面右上部にあるMoreVertマークを押すことで、ログアウトや、アカウントを作成して現在のデータの紐づけを行うことができます。

3. スレッド項目をタップすることで、スレッドに参加することができ、メッセージの内容を見たりメッセージを投稿したりすることができます。

   ![AppImage3](https://github.com/YuOyama12/BBSApp/assets/94959504/33be94a0-7e3e-4e59-862d-1ce546bd29f0)

### 搭載した主な機能
- アカウント作成及びログイン機能（一部エラー処理に対応）
- ログアウト機能
- 匿名ログイン機能
- スレッド作成機能
- お気に入りスレッド登録機能
- メッセージ投稿機能

### 今後実装予定の機能
- 設定画面（パスワードの変更機能や通知関連の機能）
- 投稿メッセージのコピーや引用等の機能
- Firebaseを用いたメールアドレス認証機能

## 開発環境等
### 開発環境
Android Studio Electric Eel | 2022.1.1 Patch 1
Runtime version: 11.0.15+0-b2043.56-9505619 amd64

### 開発言語
Kotlin 1.8.0
Jetpack Compose Version 1.4.3

## 動作対象端末・OS
### 動作対象OS
- Android 9.0
- Android 10.0
- Android 11.0
#### 動作確認済み仮想端末
- Pixel 3 (Android 10.0)
- Pixel 4 (Android 11.0)
#### 動作確認済み実機端末
- Galaxy S8 (Android 9.0)

## 主な使用技術・ライブラリ等
- Kotlin
- Jetpack Compose
    - viewModel
    - Navigation
- Hilt
- Moshi
- Preferences DataStore
- Firebase
    - Firebase Authentication
    - Realtime Database