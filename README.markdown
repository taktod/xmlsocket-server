# XmlSocket server.

## 概要 / what is this.
これは、FlashがサポートしているXmlSocketのサーバー側実装です。
<br />Javaで書かれています。

This is the server for XMLsocket which supported by Flex / Flash.
<br />This server is written in Java.

## 使い方 / how to use
1. EGitでEclipseにこのリポジトリのファイルを登録する。
2. 必要なjarライブラリを参照させる。
3. Main.javaを実行する。

<br />

1. Clone this repository with Egit and make new Java Project
2. Add external libraries listed in below.
3. execute Main.java

Beans化したり等はご自由にどうぞ。

## required Jar libraries.
javaee-api-5.1.1.jar
<br />mina-core-2.0.0-RC1.jar
<br />log4j-over-slf4j-1.5.10.jar
<br />logback-classic-0.9.18.jar
<br />logback-core-0.9.18.jar
<br />slf4j-api-1.5.10.jar

PHPで動作させるには以下が必要(いまのところ未実装) / for php execution(not yet.)
<br />inject-16.jar
<br />javamail-141.jar
<br />resin.jar
(すべてQuercusのダウンロードページのwarファイルより入手 / please download from quercus project. war archive does have them.)

