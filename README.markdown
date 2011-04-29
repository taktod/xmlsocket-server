# XmlSocket server.

## 概要 / what is this.
これは、FlashがサポートしているXmlSocketのサーバー側実装です。
Javaで書かれています。

This is the server for XMLsocket which supported by Flex / Flash.
This server is written in Java.

## 使い方 / how to use
1. EGitでEclipseにこのリポジトリのファイルを登録する。
2. 必要なjarライブラリを参照させる。
3. Main.javaを実行する。

1. Clone this repository with Egit and make new Java Project
2. Add external libraries listed in below.
3. execute Main.java

Beans化したり等はご自由にどうぞ。

## required Jar libraries.
javaee-api-5.1.1.jar
mina-core-2.0.0-RC1.jar
log4j-over-slf4j-1.5.10.jar
logback-classic-0.9.18.jar
logback-core-0.9.18.jar
slf4j-api-1.5.10.jar

PHPで動作させるには以下が必要(いまのところ未実装) / for php execution(not yet.)
inject-16.jar
javamail-141.jar
resin.jar
(すべてQuercusのダウンロードページのwarファイルより入手 / please download from quercus project. war archive does have them.)

