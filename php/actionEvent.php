<?php
import org.slf4j.LoggerFactory;
$log = LoggerFactory::getLogger("php.actionEvent");

require_once dirname(__FILE__) . "/library/argumentUtil.class.php";

$arg_util = ArgumentUtil::getInstance();
$adapter = $arg_util->getAdapter();
