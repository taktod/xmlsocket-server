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

PHPで動作させるには以下が必要 / for php execution
<br />inject-16.jar
<br />javamail-141.jar
<br />resin.jar
(すべてQuercusのダウンロードページのwarファイルより入手 / please download from quercus project. war archive does have them.)


<br />
テスト用のクライアント / client swf player for test.
<object classid="clsid:D27CDB6E-AE6D11cf96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/ flash/swflash.cab#version=5,0,0,0" height="417" width="455"> <param name="movie" value="http://poepoemix.appspot.com/swf/XmlSocket.swf" />
<param name="quality" value=high />
<param name="bgcolor" value=#000000 />
<embed src="http://poepoemix.appspot.com/swf/XmlSocket.swf" quality=high bgcolor=#000000 width="450" height="417"
type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/shockwave/download
/index.cgi?P1_Prod_Version=ShockwaveFlash"> </embed></object>
