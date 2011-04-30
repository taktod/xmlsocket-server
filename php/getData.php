<?php
require_once dirname(__FILE__) . "/library/systemUtil.class.php";
require_once dirname(__FILE__) . "/library/argumentUtil.class.php";

SystemUtil::println("getData!!");
$argUtil = new ArgumentUtil();
$conn = $argUtil->getConnection();
$input = $argUtil->getStringData();
SystemUtil::println($input);
$conn->sendHexString(bin2hex("こんにちわん"));
