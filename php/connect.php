<?php
import org.slf4j.LoggerFactory;
import javax.swing.Timer;
$log = LoggerFactory::getLogger("php.connect");
require_once dirname(__FILE__) . "/library/argumentUtil.class.php";

$arg_util = ArgumentUtil::getInstance();

$log->info("connectEvent");