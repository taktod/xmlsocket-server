<?php
import org.slf4j.LoggerFactory;
$log = LoggerFactory::getLogger("php.getData");

require_once dirname(__FILE__) . "/library/argumentUtil.class.php";
require_once dirname(__FILE__) . "/library/connUtil.class.php";

$argUtil = ArgumentUtil::getInstance();
$conn = $argUtil->getConnection();
echo $conn->getLocalAddress();
$input = $argUtil->getStringData();
SystemUtil::println($input);
$connUtil = new ConnUtil();
$connUtil->sendAllUsers($input);
