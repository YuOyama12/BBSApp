# BBSApp

## 概要
シンプルな掲示板風のアプリです。 Firebaseを試験的に導入しています。<br>（利用する際には、[こちら](https://firebase.google.com/docs/android/setup)の手順に従い、"google-services.json"等をフォルダ内に導入する必要があります。）

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

## 搭載した主な機能
- アカウント作成及びログイン機能
- 匿名ログイン機能
- スレッド作成機能
- お気に入りスレッド登録機能
- メッセージ投稿機能