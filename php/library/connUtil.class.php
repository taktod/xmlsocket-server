<?php
require_once dirname(__FILE__) . "/argumentUtil.class.php";
import com.ttProject.server.net.xmlSocket.XmlSocketConnManager;

class ConnUtil {
	// 呼び出し元のユーザーにメッセージを応答する。
	public function sendCaller($msg) {
		// 自分のコネクションを取得する。
		/**
		 * @var ArgumentUtil
		 */
		$argUtil = ArgumentUtil::getInstance();
		$conn = $argUtil->getConnection();
		$this->sendUser($conn, $msg);
	}
	// 一人に命令を返す。
	public function sendUser($conn, $msg) {
		$conn->sendHexString(bin2hex($msg));
	}
	// 全員に命令を返す。
	public function sendAllUsers($msg) {
		global $log;
		$connManager = XmlSocketConnManager::getInstance();
		$conns = $connManager->getConnectionSet();
		foreach($conns as $conn) {
			$this->sendUser($conn, $msg);
		}
		$log->info("hello");
	}
}