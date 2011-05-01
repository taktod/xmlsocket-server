<?php
import org.slf4j.LoggerFactory;
$log = LoggerFactory::getLogger("php.getData");

require_once dirname(__FILE__) . "/library/argumentUtil.class.php";
require_once dirname(__FILE__) . "/library/connUtil.class.php";

// get input data
$argUtil = ArgumentUtil::getInstance();
$input = $argUtil->getStringData();

// echo to all users with input data.
$connUtil = new ConnUtil();
$connUtil->sendAllUsers($input);
